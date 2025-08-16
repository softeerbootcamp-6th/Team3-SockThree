package com.seniclass.server.domain.student.dto.response;

import com.seniclass.server.domain.student.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "학생 정보 응답")
public record StudentInfoResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "학생 ID") Long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "이름") String name,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "이메일") String email,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "나이") Integer age,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "성별") Gender gender) {}
