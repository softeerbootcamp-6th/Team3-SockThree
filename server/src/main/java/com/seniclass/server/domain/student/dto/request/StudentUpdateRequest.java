package com.seniclass.server.domain.student.dto.request;

import com.seniclass.server.domain.student.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Schema(description = "학생 정보 수정 요청")
public record StudentUpdateRequest(
        @Schema(description = "이름", example = "김학생")
                @Size(min = 2, max = 10, message = "이름은 2~10자 사이여야 합니다")
                String name,
        @Schema(description = "나이", example = "22")
                @Min(value = 1, message = "나이는 1세 이상이어야 합니다")
                @Max(value = 150, message = "나이는 150세 이하여야 합니다")
                Integer age,
        @Schema(description = "성별", example = "MALE") Gender gender) {}
