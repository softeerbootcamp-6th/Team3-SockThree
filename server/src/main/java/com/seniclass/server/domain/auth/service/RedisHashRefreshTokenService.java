package com.seniclass.server.domain.auth.service;

import com.seniclass.server.domain.auth.domain.RefreshTokenHash;
import com.seniclass.server.domain.auth.repository.RefreshTokenHashRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisHashRefreshTokenService {

  private final RefreshTokenHashRepository refreshTokenRepository;
  private final JwtTokenProvider jwtTokenProvider; // JWT 파싱 로직 중복 제거

  /**
   * @RedisHash를 사용한 리프레시 토큰 저장
   */
  public void saveRefreshToken(String token, String userId, long ttlSeconds) {
    try {
      // 기존 사용자 토큰이 있다면 삭제 (하나의 사용자는 하나의 refresh token만)
      if (refreshTokenRepository.existsById(userId)) {
        refreshTokenRepository.deleteById(userId);
        log.debug("Deleted existing refresh token for user: {}", userId);
      }

      RefreshTokenHash refreshToken = RefreshTokenHash.create(userId, token, ttlSeconds);
      RefreshTokenHash saved = refreshTokenRepository.save(refreshToken);

      log.debug("Saved refresh token for user: {} with TTL: {} seconds", userId, ttlSeconds);
      log.debug("Token hash: {}", saved.getTokenHash());
    } catch (Exception e) {
      log.error("Failed to save refresh token for user: {}", userId, e);
      throw new RuntimeException("Failed to save refresh token", e);
    }
  }

  /** 리프레시 토큰 유효성 검사 (JwtTokenProvider 사용) */
  public boolean isValidRefreshToken(String token) {
    try {
      // JWT 토큰 자체 검증 먼저
      if (!jwtTokenProvider.validateToken(token)) {
        log.debug("Invalid JWT token format");
        return false;
      }

      String userId = jwtTokenProvider.getUserIdFromToken(token);
      String tokenHash = hashToken(token);

      Optional<RefreshTokenHash> savedToken = refreshTokenRepository.findById(userId);

      if (savedToken.isEmpty()) {
        log.debug("No refresh token found for user: {}", userId);
        return false;
      }

      RefreshTokenHash refreshToken = savedToken.get();

      // 토큰 해시 비교
      if (!refreshToken.getTokenHash().equals(tokenHash)) {
        log.debug("Token hash mismatch for user: {}", userId);
        return false;
      }

      // 만료 시간 확인
      if (refreshToken.isExpired()) {
        log.debug("Token expired for user: {}, removing from Redis", userId);
        refreshTokenRepository.deleteById(userId);
        return false;
      }

      log.debug("Valid refresh token for user: {}", userId);
      return true;

    } catch (Exception e) {
      log.warn("Error validating refresh token", e);
      return false;
    }
  }

  /** 특정 리프레시 토큰 무효화 (JwtTokenProvider 사용) */
  public void revokeRefreshToken(String token) {
    try {
      String userId = jwtTokenProvider.getUserIdFromToken(token);
      boolean existed = refreshTokenRepository.existsById(userId);

      if (existed) {
        refreshTokenRepository.deleteById(userId);
        log.debug("Revoked refresh token for user: {}", userId);
      } else {
        log.debug("No refresh token to revoke for user: {}", userId);
      }
    } catch (Exception e) {
      log.warn("Error revoking refresh token", e);
    }
  }

  /** 사용자의 모든 리프레시 토큰 무효화 */
  public void revokeAllUserRefreshTokens(String userId) {
    try {
      boolean existed = refreshTokenRepository.existsById(userId);

      if (existed) {
        refreshTokenRepository.deleteById(userId);
        log.info("Revoked all refresh tokens for user: {}", userId);
      } else {
        log.debug("No refresh tokens to revoke for user: {}", userId);
      }
    } catch (Exception e) {
      log.warn("Error revoking all refresh tokens for user: {}", userId, e);
    }
  }

  /** 만료된 토큰들 정리 (@RedisHash TTL이 자동 처리하므로 선택적) */
  public void cleanupExpiredTokens() {
    // @RedisHash의 @TimeToLive가 자동으로 처리하므로
    // 추가적인 수동 정리는 선택적으로 구현
    try {
      long now = System.currentTimeMillis();
      refreshTokenRepository.deleteByExpiresAtLessThan(now);
      log.debug("Manually cleaned up expired refresh tokens");
    } catch (Exception e) {
      log.warn("Error during manual cleanup of expired tokens", e);
    }
  }

  /** 사용자의 토큰 조회 (디버깅용) */
  public Optional<RefreshTokenHash> getUserToken(String userId) {
    return refreshTokenRepository.findById(userId);
  }

  /** 전체 토큰 개수 조회 (모니터링용) */
  public long getTokenCount() {
    return refreshTokenRepository.count();
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
