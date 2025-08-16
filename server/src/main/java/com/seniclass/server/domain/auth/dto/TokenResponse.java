package com.seniclass.server.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(required = true, description = "토큰 응답")
public record TokenResponse(
        @Schema(required = true, description = "액세스 토큰", example = "eyJhbGciOiJIUzUxMiJ9...") String accessToken,
        @Schema(required = true, description = "토큰 타입", example = "Bearer") String tokenType,
        @Schema(required = true, description = "토큰 만료 시간 (초)", example = "3600") Long expiresIn) {}
