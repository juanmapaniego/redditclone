package com.jmpaniego.RedditClone.jwt;

import com.jmpaniego.RedditClone.exceptions.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {

  private KeyStore keyStore;

  @PostConstruct
  public void init(){
    try{
      keyStore = KeyStore.getInstance("JKS");
      InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
      keyStore.load(resourceAsStream,"secret".toCharArray());
    } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
      throw new SpringRedditException("Exception ocurred while loading keystore");
    }
  }

  public String generateToken(Authentication authentication){
    User principal = (User) authentication.getPrincipal();
    return Jwts.builder()
        .setSubject(principal.getUsername())
        .signWith(getPrivateKey())
        .compact();
  }

  private PrivateKey getPrivateKey() {
    try{
      return (PrivateKey) keyStore.getKey("springblog","secret".toCharArray());
    }catch (UnrecoverableKeyException | NoSuchAlgorithmException  | KeyStoreException e){
      throw new SpringRedditException("Exception ocurred while retrieving private key");
    }
  }

  public boolean validateToken(String jwt){
    Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
    return true;
  }

  private PublicKey getPublicKey() {
    try{
      return keyStore.getCertificate("springblog").getPublicKey();
    }catch (KeyStoreException e){
      throw new SpringRedditException("Exception ocurred while retrieving private key");
    }
  }

  public String getUsernameFromJwt(String jwt){
    Claims claims = Jwts.parser()
        .setSigningKey(getPublicKey())
        .parseClaimsJws(jwt)
        .getBody();
    return claims.getSubject();
  }
}
