package com.seniclass.server.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 응답")
public record LoginResponse(
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzUxMiJ9...") String accessToken,
        @Schema(description = "토큰 타입", example = "Bearer") String tokenType,
        @Schema(description = "토큰 만료 시간 (초)", example = "3600") Long expiresIn,
        @Schema(description = "사용자 정보") UserInfo userInfo) {

    @Schema(description = "사용자 정보")
    public record UserInfo(
            @Schema(description = "사용자 ID", example = "STU_1") String id,
            @Schema(description = "사용자 이메일", example = "student@example.com") String email,
            @Schema(description = "사용자 역할", example = "STUDENT") String role) {}
}
