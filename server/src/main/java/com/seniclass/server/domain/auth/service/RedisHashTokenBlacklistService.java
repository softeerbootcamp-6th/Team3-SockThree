package com.seniclass.server.domain.auth.service;

import com.seniclass.server.domain.auth.domain.TokenBlacklistHash;
import com.seniclass.server.domain.auth.repository.TokenBlacklistHashRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisHashTokenBlacklistService {

  private final TokenBlacklistHashRepository blacklistRepository;

  /**
   * @RedisHash를 사용한 토큰 블랙리스트 추가
   */
  public void addToBlacklist(String token, String userId, long ttlSeconds) {
    TokenBlacklistHash blacklistToken = TokenBlacklistHash.create(token, userId, ttlSeconds);
    blacklistRepository.save(blacklistToken);

    log.debug("Added token to blacklist for user: {} with TTL: {} seconds", userId, ttlSeconds);
  }

  /** 토큰이 블랙리스트에 있는지 확인 */
  public boolean isBlacklisted(String token) {
    try {
      String tokenHash = hashToken(token);
      return blacklistRepository.existsByTokenHash(tokenHash);
    } catch (Exception e) {
      log.warn("Error checking blacklist", e);
      return false;
    }
  }

  /** 사용자의 모든 블랙리스트 토큰 조회 */
  public List<TokenBlacklistHash> getUserBlacklistedTokens(String userId) {
    return blacklistRepository.findByUserId(userId);
  }

  /** 만료된 블랙리스트 토큰들 정리 */
  public void cleanupExpiredBlacklistTokens() {
    long now = System.currentTimeMillis();
    blacklistRepository.deleteByExpiresAtLessThan(now);
    log.info("Cleaned up expired blacklist tokens");
  }

  /** 사용자의 모든 블랙리스트 토큰 삭제 */
  public void clearUserBlacklist(String userId) {
    blacklistRepository.deleteByUserId(userId);
    log.info("Cleared all blacklist tokens for user: {}", userId);
  }

  private String hashToken(String token) {
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
      throw new RuntimeException("Token hashing failed", e);
    }
  }
}
