package com.seniclass.server.domain.widget.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "강사 위젯 응답", name = "Widget.TeacherWidgetResponse")
public record TeacherWidgetResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "프로필 이미지 URL") String profileImageUrl,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "이름") String name,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Q&A 응답률") Integer qnaResponseRate,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "평균 리뷰 평점") Double averageReviewRating,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "소개") String instruction,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "경력 목록") List<String> careerList,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "총 강의 수") Integer totalLectureCount,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "총 학생 수") Integer totalStudentCount,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "경험 경력 수") Integer experienceCareerCount,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "자격증 경력 수") Integer certificateCareerCount,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "총 리뷰 수") Integer totalReviewCount) {
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
