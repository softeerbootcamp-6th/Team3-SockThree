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

@RedisHash(value = "user_tokens")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenHash {

  @Id private String id; // userId

  @Indexed private String tokenHash;

  private long expiresAt;

  @TimeToLive private long ttl; // seconds

  public static RefreshTokenHash create(String userId, String token, long ttlSeconds) {
    String tokenHash = hashToken(token);
    long expiresAt = System.currentTimeMillis() + (ttlSeconds * 1000);

    return new RefreshTokenHash(userId, tokenHash, expiresAt, ttlSeconds);
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
