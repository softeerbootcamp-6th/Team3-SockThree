package com.seniclass.server.domain.widget.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "리뷰 위젯 응답")
public record ReviewWidgetResponse(@Schema(required = true, description = "상위 리뷰 목록") List<ReviewItem> topReviews) {
    public static ReviewWidgetResponse of(List<ReviewItem> topReviews) {
        return new ReviewWidgetResponse(topReviews);
    }

    @Schema(description = "리뷰 항목")
    public record ReviewItem(
            @Schema(required = true, description = "리뷰 ID") Long reviewId,
            @Schema(required = true, description = "리뷰 내용") String content,
            @Schema(required = true, description = "평점") Double rating,
            @Schema(required = true, description = "학생 이름") String studentName,
            @Schema(required = true, description = "강의 이름") String lectureName,
            @Schema(required = true, description = "학생 리뷰 비율") Double studentReviewRatio // 해당 학생의 전체 수강후기 작성 비율
            ) {}
}
