package com.seniclass.server.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(required = true, description = "회원가입 응답")
public record RegisterResponse(
        @Schema(required = true, description = "사용자 ID", example = "2024001") String userId,
        @Schema(required = true, description = "이름", example = "홍길동") String name,
        @Schema(required = true, description = "이메일", example = "student@example.com") String email,
        @Schema(required = true, description = "사용자 역할", example = "STUDENT") String role,
        @Schema(required = true, description = "회원가입 성공 메시지", example = "회원가입이 완료되었습니다") String message) {}
