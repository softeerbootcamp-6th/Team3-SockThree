package com.seniclass.server.domain.auth.service;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenManagementService {

  // @RedisHash 기반 서비스들로 교체
  private final RedisHashRefreshTokenService redisHashRefreshTokenService;
  private final RedisHashTokenBlacklistService redisHashTokenBlacklistService;
  private final JwtTokenProvider jwtTokenProvider;

  @Transactional
  public void saveRefreshToken(String token, String userId) {
    // JWT에서 만료 시간 추출하여 TTL 계산
    LocalDateTime expiresAt = jwtTokenProvider.getExpirationLocalDateTimeFromToken(token);
    long ttlSeconds = Duration.between(LocalDateTime.now(), expiresAt).getSeconds();

    if (ttlSeconds > 0) {
      redisHashRefreshTokenService.saveRefreshToken(token, userId, ttlSeconds);
    }
  }

  @Transactional
  public void revokeRefreshToken(String token) {
    redisHashRefreshTokenService.revokeRefreshToken(token);
  }

  @Transactional
  public void revokeAllUserRefreshTokens(String userId) {
    redisHashRefreshTokenService.revokeAllUserRefreshTokens(userId);
  }

  public boolean isValidRefreshToken(String token) {
    return redisHashRefreshTokenService.isValidRefreshToken(token);
  }

  @Transactional
  public void addToBlacklist(String token, String userId) {
    // JWT에서 만료 시간 추출
    LocalDateTime expiresAt = jwtTokenProvider.getExpirationLocalDateTimeFromToken(token);
    long ttlSeconds = Duration.between(LocalDateTime.now(), expiresAt).getSeconds();

    if (ttlSeconds > 0) {
      // @RedisHash로 저장
      redisHashTokenBlacklistService.addToBlacklist(token, userId, ttlSeconds);
      log.info("Token added to blacklist for user: {}", userId);
    }
  }

  public boolean isBlacklisted(String token) {
    // @RedisHash에서 확인
    return redisHashTokenBlacklistService.isBlacklisted(token);
  }

  /** 만료된 토큰들 정리 (@RedisHash 자동 TTL + 수동 정리) */
  @Transactional
  public void cleanupExpiredTokens() {
    redisHashRefreshTokenService.cleanupExpiredTokens();
    redisHashTokenBlacklistService.cleanupExpiredBlacklistTokens();
    log.info("Cleaned up expired tokens using @RedisHash");
  }
}
