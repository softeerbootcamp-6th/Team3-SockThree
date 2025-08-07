package com.seniclass.server.domain.auth.repository;

import com.seniclass.server.domain.auth.domain.TokenBlacklistHash;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlacklistHashRepository extends CrudRepository<TokenBlacklistHash, String> {

    // 사용자 ID로 블랙리스트된 토큰들 조회
    List<TokenBlacklistHash> findByUserId(String userId);

    // 토큰 해시로 블랙리스트 확인
    boolean existsByTokenHash(String tokenHash);

    // 사용자의 모든 블랙리스트 토큰 삭제
    void deleteByUserId(String userId);

    // 만료된 블랙리스트 토큰들 삭제
    void deleteByExpiresAtLessThan(long currentTime);
}
