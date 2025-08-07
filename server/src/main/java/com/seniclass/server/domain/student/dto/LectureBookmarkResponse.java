package com.seniclass.server.domain.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "강의 북마크 응답")
public record LectureBookmarkResponse(
        @Schema(description = "북마크 ID", example = "1") Long id,
        @Schema(description = "학생 ID", example = "1") Long studentId,
        @Schema(description = "강의 ID", example = "1") Long lectureId,
        @Schema(description = "강의명", example = "Java 기초 강의") String lectureName,
        @Schema(description = "카테고리명", example = "프로그래밍") String categoryName,
        @Schema(description = "강의 레벨", example = "BEGINNER") String level,
        @Schema(description = "강의 가격", example = "50000") Integer fee,
        @Schema(description = "북마크 추가일시") LocalDateTime createdDt) {}
