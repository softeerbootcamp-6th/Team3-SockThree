package com.seniclass.server.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "에러 응답")
public record ErrorResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "에러 클래스 이름")
                String errorClassName,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "에러 메시지")
                String message) {
    public static ErrorResponse of(String errorClassName, String message) {
        return new ErrorResponse(errorClassName, message);
    }
}
