package com.seniclass.server.domain.student.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 수강 등록 응답")
public record LectureEnrollmentResponse(
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "수강 등록 ID",
                        example = "1")
                Long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "학생 ID", example = "1")
                Long studentId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "학생 이름", example = "김학생")
                String studentName,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 ID", example = "1")
                Long lectureId,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "강의명",
                        example = "Java 기초 강의")
                String lectureName,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "카테고리명",
                        example = "프로그래밍")
                String categoryName,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "강의 레벨",
                        example = "BEGINNER")
                String level,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 시작일")
                LocalDate startDate,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 종료일")
                LocalDate endDate,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "강의 가격",
                        example = "50000")
                Integer fee,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "수강 등록일시")
                LocalDateTime enrolledAt) {}
