package com.seniclass.server.domain.widget.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "리뷰 위젯 응답")
public record ReviewWidgetResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "상위 리뷰 목록")
                List<ReviewItem> topReviews) {
    public static ReviewWidgetResponse of(List<ReviewItem> topReviews) {
        return new ReviewWidgetResponse(topReviews);
    }

    @Schema(description = "리뷰 항목")
    public record ReviewItem(
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "리뷰 ID")
                    Long reviewId,
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "리뷰 내용")
                    String content,
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "평점") Double rating,
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "학생 이름")
                    String studentName,
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "강의 이름")
                    String lectureName,
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "학생 리뷰 비율")
                    Double studentReviewRatio // 해당 학생의 전체 수강후기 작성 비율
            ) {}
}
