package com.seniclass.server.domain.lecture.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "챕터 생성 요청 DTO")
public record ChapterCreateRequest(
        @Schema(description = "강의 ID", example = "1") @NotNull(message = "강의 ID는 필수입니다.")
                Long lectureId,
        @Schema(description = "챕터명", example = "1주차: 스윙 기본") @NotBlank(message = "챕터명은 필수입니다.")
                String name) {}
