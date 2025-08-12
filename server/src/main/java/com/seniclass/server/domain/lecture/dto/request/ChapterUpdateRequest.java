package com.seniclass.server.domain.lecture.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "챕터 수정 요청 DTO")
public record ChapterUpdateRequest(
        @Schema(description = "챕터명", example = "1주차: 스윙 기본 자세") @NotBlank(message = "챕터명은 필수입니다.")
                String name) {}
