package com.seniclass.server.domain.lecture.dto.response;

import com.seniclass.server.domain.category.domain.MainCategory;
import com.seniclass.server.domain.category.domain.SubCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Lecture.LectureBannerResponse", description = "강의 배너 응답")
public record LectureBannerResponse(
        @Schema(required = true, description = "강좌 id", example = "1") Long lectureId,
        @Schema(required = true, description = "강좌 이미지 URL", example = "www.aws.com/s3/image/1")
                String presignedImageURL,
        @Schema(required = true, description = "강좌명", example = "골프 왕초보 강좌") String name,
        @Schema(required = true, description = "기수", example = "1") Integer cohort,
        @Schema(required = true, description = "찜 개수", example = "53") Integer bookMarkCount,
        @Schema(required = true, description = "최대 수강 인원", example = "30") Integer maxStudentCount,
        @Schema(required = true, description = "현재 수강 인원", example = "17") Integer enrolledStudentCount,
        @Schema(required = true, description = "메인 카테고리", example = "운동") String mainCategoryName,
        @Schema(required = true, description = "보조 카테고리", example = "골프") String subCategoryName,
        @Schema(required = true, description = "강좌 수강 기간 (개월)", example = "2") Integer lectureDuration) {
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
