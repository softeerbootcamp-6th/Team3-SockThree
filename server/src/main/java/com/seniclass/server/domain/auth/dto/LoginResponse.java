package com.seniclass.server.domain.auth.dto;

import com.seniclass.server.domain.auth.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(required = true, description = "로그인 응답", name = "Auth.LoginResponse")
public record LoginResponse(
        @Schema(required = true, description = "액세스 토큰", example = "eyJhbGciOiJIUzUxMiJ9...") String accessToken,
        @Schema(required = true, description = "사용자 역할", example = "STUDENT") UserRole role) {}
