package com.seniclass.server.domain.widget.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "강사 위젯 응답", name = "Widget.TeacherWidgetResponse")
public record TeacherWidgetResponse(
        @Schema(required = true, description = "프로필 이미지 URL") String profileImageUrl,
        @Schema(required = true, description = "이름") String name,
        @Schema(required = true, description = "Q&A 응답률") Integer qnaResponseRate,
        @Schema(required = true, description = "평균 리뷰 평점") Double averageReviewRating,
        @Schema(required = true, description = "소개") String instruction,
        @Schema(required = true, description = "경력 목록") List<String> careerList,
        @Schema(required = true, description = "총 강의 수") Integer totalLectureCount,
        @Schema(required = true, description = "총 학생 수") Integer totalStudentCount,
        @Schema(required = true, description = "경험 경력 수") Integer experienceCareerCount,
        @Schema(required = true, description = "자격증 경력 수") Integer certificateCareerCount,
        @Schema(required = true, description = "총 리뷰 수") Integer totalReviewCount) {
    public static TeacherWidgetResponse of(
            String profileImageUrl,
            String name,
            Integer qnaResponseRate,
            Double averageReviewRating,
            String instruction,
            List<String> careerList,
            Integer totalLectureCount,
            Integer totalStudentCount,
            Integer experienceCareerCount,
            Integer certificateCareerCount,
            Integer totalReviewCount) {
        return new TeacherWidgetResponse(
                profileImageUrl,
                name,
                qnaResponseRate,
                averageReviewRating,
                instruction,
                careerList,
                totalLectureCount,
                totalStudentCount,
                experienceCareerCount,
                certificateCareerCount,
                totalReviewCount);
    }
}
