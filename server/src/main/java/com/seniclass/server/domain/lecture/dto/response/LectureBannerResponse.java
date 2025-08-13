package com.seniclass.server.domain.lecture.dto.response;

import com.seniclass.server.domain.category.domain.MainCategory;
import com.seniclass.server.domain.category.domain.SubCategory;

public record LectureBannerResponse(
        Long lectureId,
        String presignedImageURL,
        String name,
        Integer cohort,
        Integer bookMarkCount,
        Integer maxStudentCount,
        Integer enrolledStudentCount,
        String mainCategoryName,
        String subCategoryName,
        Integer lectureDuration) {
    public static LectureBannerResponse of(
            Long lectureId,
            String presignedImageURL,
            String name,
            Integer cohort,
            Integer bookMarkCount,
            Integer maxStudentCount,
            Integer enrolledStudentCount,
            MainCategory mainCategory,
            SubCategory subCategory,
            Integer lectureDuration) {
        return new LectureBannerResponse(
                lectureId,
                presignedImageURL,
                name,
                cohort,
                bookMarkCount,
                maxStudentCount,
                enrolledStudentCount,
                mainCategory.getName(),
                subCategory.getName(),
                lectureDuration);
    }
}
