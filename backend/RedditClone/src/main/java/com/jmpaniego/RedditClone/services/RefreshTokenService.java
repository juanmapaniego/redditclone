package com.jmpaniego.RedditClone.services;

import com.jmpaniego.RedditClone.exceptions.SpringRedditException;
import com.jmpaniego.RedditClone.models.RefreshToken;
import com.jmpaniego.RedditClone.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  public RefreshToken generateRefreshToken(){
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken.setCreated(Instant.now());

    return refreshTokenRepository.save(refreshToken);
  }

  @Transactional(readOnly = true)
  public void validateRefreshToken(String token){
    refreshTokenRepository.findByToken(token)
        .orElseThrow(
            ()->new SpringRedditException("Invalid refresh token")
        );
  }

  public void deleteRefreshToken(String token){
    refreshTokenRepository.deleteByToken(token);
  }
}
