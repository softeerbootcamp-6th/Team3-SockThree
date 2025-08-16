package com.seniclass.server.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "토큰 응답")
public record TokenResponse(
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "액세스 토큰",
                        example = "eyJhbGciOiJIUzUxMiJ9...")
                String accessToken,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "토큰 타입",
                        example = "Bearer")
                String tokenType,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "토큰 만료 시간 (초)",
                        example = "3600")
                Long expiresIn) {}
