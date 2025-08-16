package com.seniclass.server.domain.auth.dto;

import com.seniclass.server.domain.auth.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "로그인 응답", name = "Auth.LoginResponse")
public record LoginResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "액세스 토큰", example = "eyJhbGciOiJIUzUxMiJ9...") String accessToken,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "사용자 역할", example = "STUDENT") UserRole role) {}
