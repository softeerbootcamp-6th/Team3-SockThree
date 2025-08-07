package com.seniclass.server.domain.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "강의 북마크 추가 요청")
public record LectureBookmarkRequest(
        @Schema(description = "강의 ID", example = "1") @NotNull(message = "강의 ID는 필수입니다")
                Long lectureId) {}
