package com.seniclass.server.domain.auth.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "token_blacklist")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenBlacklistHash {

  @Id private String tokenHash; // SHA-256 해시된 토큰을 ID로 사용

  @Indexed private String userId;

  private long expiresAt;

  @TimeToLive private long ttl; // seconds

  public static TokenBlacklistHash create(String token, String userId, long ttlSeconds) {
    String tokenHash = hashToken(token);
    long expiresAt = System.currentTimeMillis() + (ttlSeconds * 1000);

    return new TokenBlacklistHash(tokenHash, userId, expiresAt, ttlSeconds);
  }

  public boolean isExpired() {
    return System.currentTimeMillis() > expiresAt;
  }

  private static String hashToken(String token) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(token.getBytes());
      StringBuilder hexString = new StringBuilder();

      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) {
          hexString.append('0');
        }
        hexString.append(hex);
      }

      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("SHA-256 algorithm not available", e);
    }
  }
}
