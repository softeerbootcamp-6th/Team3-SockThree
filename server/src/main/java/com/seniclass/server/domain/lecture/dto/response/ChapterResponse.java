package com.seniclass.server.domain.lecture.dto.response;

import com.seniclass.server.domain.lecture.domain.Chapter;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "챕터 정보 응답 DTO")
public record ChapterResponse(
        @Schema(description = "챕터 ID", example = "1") Long id,
        @Schema(description = "챕터명", example = "1주차: 스윙 기본") String name,
        @Schema(description = "강의 ID", example = "1") Long lectureId) {
    public static ChapterResponse from(Chapter chapter) {
        return new ChapterResponse(
                chapter.getId(), chapter.getName(), chapter.getLecture().getId());
    }
}
