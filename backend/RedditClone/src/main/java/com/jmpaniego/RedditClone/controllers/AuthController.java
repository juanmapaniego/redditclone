package com.jmpaniego.RedditClone.controllers;

import com.jmpaniego.RedditClone.dto.AuthenticationResponse;
import com.jmpaniego.RedditClone.dto.LoginRequest;
import com.jmpaniego.RedditClone.dto.RegisterRequest;
import com.jmpaniego.RedditClone.exceptions.SpringRedditException;
import com.jmpaniego.RedditClone.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest){
    authService.signup(registerRequest);
    return ResponseEntity.ok("User registration successfull");
  }

  @GetMapping("accountVerification/{token}")
  public ResponseEntity<String> accountVerification(@PathVariable("token") String token){
    authService.verifyAccount(token);
    return ResponseEntity.ok("Account activated successfully");
  }

  @PostMapping("/login")
  public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
    return authService.login(loginRequest);
  }

  @ExceptionHandler(SpringRedditException.class)
  public ResponseEntity<String> handlingSpingException(SpringRedditException sre){
    return ResponseEntity.badRequest().body(sre.getMessage());
  }
}
