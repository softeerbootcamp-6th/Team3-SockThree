package com.seniclass.server.domain.widget.dto.response;

import java.util.List;

public record TeacherWidgetResponse(
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
