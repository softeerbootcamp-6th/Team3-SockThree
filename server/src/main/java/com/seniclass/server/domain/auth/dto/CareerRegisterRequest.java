package com.seniclass.server.domain.auth.dto;

import com.seniclass.server.domain.teacher.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "강사 이력")
public record CareerRegisterRequest(
        @Schema(description = "이력", example = "생활체육지도자 1급")
                @NotBlank(message = "이력 이름은 필수입니다.")
                @Size(max = 50, message = "이력은 50자를 넘을 수 없습니다.")
                String name,
        @Schema(
                        description = "이력 종류, [ EXPERIENCE : 일반 경력, CERTIFICATE : 자격증 ]",
                        example = "EXPERIENCE",
                        allowableValues = {"EXPERIENCE", "CERTIFICATE"})
                @NotNull(message = "이력 종류는 필수입니다.")
                Type type) {}
