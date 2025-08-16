package com.seniclass.server.domain.lecture.dto.response;

import com.seniclass.server.domain.lecture.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "리뷰 응답", name = "Lecture.ReviewResponse")
public record ReviewResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "리뷰 ID", example = "1") Long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "리뷰 내용", example = "정말 좋은 강의였습니다!") String content,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "평점", example = "4.5") Double rating,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 ID", example = "1") Long lectureId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 제목", example = "Java 기초 강의") String lectureTitle,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "학생 ID", example = "1") Long studentId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "학생 이름", example = "홍길동") String studentName,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "생성일시", example = "2024-01-01T10:00:00") LocalDateTime createdDt,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "수정일시", example = "2024-01-01T10:00:00") LocalDateTime updatedDt) {
    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getContent(),
                review.getRating(),
                review.getLecture().getId(),
                review.getLecture().getName(),
                review.getStudent().getId(),
                review.getStudent().getName(),
                review.getCreatedDt(),
                review.getUpdatedDt());
    }
}
