package com.jmpaniego.RedditClone.services;

import com.jmpaniego.RedditClone.dto.AuthenticationResponse;
import com.jmpaniego.RedditClone.dto.LoginRequest;
import com.jmpaniego.RedditClone.dto.RegisterRequest;
import com.jmpaniego.RedditClone.exceptions.SpringRedditException;
import com.jmpaniego.RedditClone.jwt.JwtProvider;
import com.jmpaniego.RedditClone.models.NotificationEmail;
import com.jmpaniego.RedditClone.models.User;
import com.jmpaniego.RedditClone.models.VerificationToken;
import com.jmpaniego.RedditClone.repositories.UserRepository;
import com.jmpaniego.RedditClone.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private VerificationTokenRepository verificationTokenRepository;
  @Autowired
  private MailService mailService;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtProvider jwtProvider;

  @Transactional
  public void signup(RegisterRequest registerRequest){
    if(
        !registerRequest.getPassword().equals(
            registerRequest.getRepeatedPassword()
        )
    ){
      throw new SpringRedditException("Passwords mismatchs!");
    }


    User user = new User();
    user.setUsername(registerRequest.getUsername());
    user.setEmail(registerRequest.getEmail());
    user.setPassword(
        passwordEncoder.encode(registerRequest.getPassword())
    );
    user.setCreated(Instant.now());
    user.setEnabled(false);

    userRepository.save(user);

    String token = generateVerificationToken(user);

    mailService.sendMail(
        new NotificationEmail(
            "Please avtivate your account",
            user.getEmail(),
            "Thank you for signing up to Spring Reddit clone, " +
                "plese click on the below url to activate your account: " +
                "http://localhost:8080/api/auth/accountVerification/" +token
        )
    );
  }

  private String generateVerificationToken(User user) {
    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken(token);
    verificationToken.setUser(user);

    verificationTokenRepository.save(verificationToken);

    return token;
  }

  public void verifyAccount(String token) {
    VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(
        () -> new SpringRedditException("Invalid Token")
    );
    fetchUserAndEnable(verificationToken);
  }

  @Transactional
  private void fetchUserAndEnable(VerificationToken verificationToken) {
    String username = verificationToken.getUser().getUsername();
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new SpringRedditException(String.format("Username with name %s not found", username))
    );
    user.setEnabled(true);
    userRepository.save(user);
  }

  public AuthenticationResponse login(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtProvider.generateToken(authentication);
    return AuthenticationResponse.builder()
        .authenticationToken(token)
        .username(loginRequest.getUsername())
        .build();
  }
}
