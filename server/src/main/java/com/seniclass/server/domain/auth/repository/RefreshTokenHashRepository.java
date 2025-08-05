package com.seniclass.server.domain.auth.repository;

import com.seniclass.server.domain.auth.domain.RefreshTokenHash;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenHashRepository extends CrudRepository<RefreshTokenHash, String> {
    // 토큰 해시로 조회 (필요시)
    Optional<RefreshTokenHash> findByTokenHash(String tokenHash);

    // 만료 시간 기준으로 삭제 (수동 정리용)
    void deleteByExpiresAtLessThan(long currentTime);
}
