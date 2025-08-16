package com.seniclass.server.domain.lecture.dto.response;

import com.seniclass.server.domain.category.domain.MainCategory;
import com.seniclass.server.domain.category.domain.SubCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "강의 배너 응답")
public record LectureBannerResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강좌 id", example = "1")
                Long lectureId,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "강좌 이미지 URL",
                        example = "www.aws.com/s3/image/1")
                String presignedImageURL,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "강좌명",
                        example = "골프 왕초보 강좌")
                String name,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "기수", example = "1")
                Integer cohort,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "찜 개수", example = "53")
                Integer bookMarkCount,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "최대 수강 인원",
                        example = "30")
                Integer maxStudentCount,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "현재 수강 인원",
                        example = "17")
                Integer enrolledStudentCount,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "메인 카테고리",
                        example = "운동")
                String mainCategoryName,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "보조 카테고리",
                        example = "골프")
                String subCategoryName,
        @Schema(
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        description = "강좌 수강 기간 (개월)",
                        example = "2")
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
