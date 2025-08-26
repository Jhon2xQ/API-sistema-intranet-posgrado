package com.posgrado.intranet.common.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.posgrado.intranet.common.config.CustomUserDetails;
import com.posgrado.intranet.common.properties.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; 

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {
  private final JwtProperties jwtProperties;

  private SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
  }

  public String generarToken(Authentication authentication) {
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    return Jwts.builder()
        .subject(userDetails.getUsername())
        .issuedAt(Date.from(Instant.now()))
        .expiration(Date.from(Instant.now().plus(jwtProperties.getExpiration(), ChronoUnit.MILLIS)))
        .signWith(getSecretKey())
        .compact();
  }

  public String generarRefreshToken(String username) {
    return Jwts.builder()
        .subject(username)
        .issuedAt(Date.from(Instant.now()))
        .expiration(Date.from(Instant.now().plus(jwtProperties.getRefreshExpiration(), ChronoUnit.MILLIS)))
        .signWith(getSecretKey())
        .compact();
  }

  public Claims getClaimsFromToken(String token) {
    return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
  }

  public boolean validarToken(String token) {
    try {
      getClaimsFromToken(token);
      return true;
    } catch (MalformedJwtException e) {
      log.error("JWT token malformado: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token expirado: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token no soportado: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims vacío: {}", e.getMessage());
    }
    return false;
  }
  
  public Date getExpirationDateFromToken(String token) {
    return getClaimsFromToken(token).getExpiration();
  }

  public boolean isRefreshToken(String token) {
    try {
      String type = getClaimsFromToken(token).get("type", String.class);
      return "refresh".equals(type);
    } catch (Exception e) {
      return false;
    }
  }

  public String getUsernameFromToken(String token) {
    return getClaimsFromToken(token).getSubject();
  }
}
