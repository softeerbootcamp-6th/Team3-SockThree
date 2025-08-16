package com.seniclass.server.domain.auth.dto;

import com.seniclass.server.domain.auth.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 응답")
public record LoginResponse(
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzUxMiJ9...") String accessToken,
        @Schema(description = "사용자 역할", example = "STUDENT") UserRole role) {}
