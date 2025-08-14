package com.seniclass.server.domain.widget.dto.response;

import java.util.List;

public record TeacherInfoLargeWidgetResponse(
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
    public static TeacherInfoLargeWidgetResponse of(
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
        return new TeacherInfoLargeWidgetResponse(
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
