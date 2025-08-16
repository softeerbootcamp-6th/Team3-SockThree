package com.seniclass.server.domain.student.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(required = true, description = "강의 북마크 응답", name = "Student.LectureBookmarkResponse")
public record LectureBookmarkResponse(
        @Schema(required = true, description = "북마크 ID", example = "1") Long id,
        @Schema(required = true, description = "학생 ID", example = "1") Long studentId,
        @Schema(required = true, description = "강의 ID", example = "1") Long lectureId,
        @Schema(required = true, description = "강의명", example = "Java 기초 강의") String lectureName,
        @Schema(required = true, description = "카테고리명", example = "프로그래밍") String categoryName,
        @Schema(required = true, description = "강의 레벨", example = "BEGINNER") String level,
        @Schema(required = true, description = "강의 가격", example = "50000") Integer fee,
        @Schema(required = true, description = "북마크 추가일시") LocalDateTime createdDt) {}
