package com.seniclass.server.domain.auth.service;

import com.seniclass.server.domain.auth.domain.AuthenticatedUser;
import com.seniclass.server.domain.auth.enums.TokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {

  private final SecretKey secretKey;
  private final long accessTokenValidityInMilliseconds;
  private final long refreshTokenValidityInMilliseconds;

  public JwtTokenProvider(
      @Value("${jwt.secret:defaultSecretKeyThatShouldBeChangedInProduction}") String secretKey,
      @Value("${jwt.access-token-validity:3600000}") long accessTokenValidityInMilliseconds,
      @Value("${jwt.refresh-token-validity:2592000000}") long refreshTokenValidityInMilliseconds) {
    this.secretKey = createSecureKey(secretKey);
    this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
    this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
  }

  /** HS512 알고리즘에 적합한 강력한 키를 생성합니다. 최소 512비트(64바이트) 이상의 키를 보장합니다. */
  private SecretKey createSecureKey(String originalKey) {
    try {
      // 원래 키가 64바이트 이상이면 그대로 사용
      if (originalKey.getBytes(StandardCharsets.UTF_8).length >= 64) {
        return Keys.hmacShaKeyFor(originalKey.getBytes(StandardCharsets.UTF_8));
      }

      // 짧은 키는 SHA-512로 해시하여 64바이트로 확장
      MessageDigest digest = MessageDigest.getInstance("SHA-512");
      byte[] hashedKey = digest.digest(originalKey.getBytes(StandardCharsets.UTF_8));

      log.info("JWT key expanded to {} bits for HS512 security", hashedKey.length * 8);
      return Keys.hmacShaKeyFor(hashedKey);

    } catch (NoSuchAlgorithmException e) {
      log.error("SHA-512 algorithm not available, using default key generation", e);
      // Fallback: JJWT에서 제공하는 안전한 키 생성
      return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
  }

  public String generateAccessToken(AuthenticatedUser user) {
    return generateToken(user, TokenType.ACCESS, accessTokenValidityInMilliseconds);
  }

  public String generateRefreshToken(AuthenticatedUser user) {
    return generateToken(user, TokenType.REFRESH, refreshTokenValidityInMilliseconds);
  }

  private String generateToken(
      AuthenticatedUser user, TokenType tokenType, long validityInMilliseconds) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", user.getId());
    claims.put("email", user.getEmail());
    claims.put("role", user.getRole().getValue());
    claims.put("tokenType", tokenType.getValue());

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(user.getId())
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(secretKey, SignatureAlgorithm.HS512)
        .compact();
  }

  public Claims getClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
  }

  public String getUserIdFromToken(String token) {
    return getClaimsFromToken(token).getSubject();
  }

  public String getEmailFromToken(String token) {
    return getClaimsFromToken(token).get("email", String.class);
  }

  public String getRoleFromToken(String token) {
    return getClaimsFromToken(token).get("role", String.class);
  }

  public TokenType getTokenTypeFromToken(String token) {
    String tokenType = getClaimsFromToken(token).get("tokenType", String.class);
    return TokenType.valueOf(tokenType);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimsFromToken(token).getExpiration();
  }

  public LocalDateTime getExpirationLocalDateTimeFromToken(String token) {
    return getExpirationDateFromToken(token)
        .toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime();
  }

  public boolean isTokenExpired(String token) {
    Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public long getAccessTokenValidityInMilliseconds() {
    return accessTokenValidityInMilliseconds;
  }

  public long getRefreshTokenValidityInMilliseconds() {
    return refreshTokenValidityInMilliseconds;
  }
}
