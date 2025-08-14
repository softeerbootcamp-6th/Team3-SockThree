package com.seniclass.server.domain.student.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "강의 수강 등록 응답")
public record LectureEnrollmentResponse(
        @Schema(description = "수강 등록 ID", example = "1") Long id,
        @Schema(description = "학생 ID", example = "1") Long studentId,
        @Schema(description = "학생 이름", example = "김학생") String studentName,
        @Schema(description = "강의 ID", example = "1") Long lectureId,
        @Schema(description = "강의명", example = "Java 기초 강의") String lectureName,
        @Schema(description = "카테고리명", example = "프로그래밍") String categoryName,
        @Schema(description = "강의 레벨", example = "BEGINNER") String level,
        @Schema(description = "강의 시작일") LocalDate startDate,
        @Schema(description = "강의 종료일") LocalDate endDate,
        @Schema(description = "강의 가격", example = "50000") Integer fee,
        @Schema(description = "수강 등록일시") LocalDateTime enrolledAt) {}
