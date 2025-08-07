package com.seniclass.server.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "리프레시 토큰 요청")
public record RefreshTokenRequest(
        @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzUxMiJ9...")
                @NotBlank(message = "리프레시 토큰은 필수입니다")
                @Pattern(
                        regexp = "^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_.+/=]*$",
                        message = "올바른 JWT 토큰 형식이 아닙니다")
                String refreshToken) {}
