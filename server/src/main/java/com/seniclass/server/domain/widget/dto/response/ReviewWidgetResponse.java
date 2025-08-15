package com.seniclass.server.domain.widget.dto.response;

import java.util.List;

public record ReviewWidgetResponse(List<ReviewItem> topReviews) {
    public static ReviewWidgetResponse of(List<ReviewItem> topReviews) {
        return new ReviewWidgetResponse(topReviews);
    }

    public record ReviewItem(
            Long reviewId,
            String content,
            Double rating,
            String studentName,
            String lectureName,
            Double studentReviewRatio // 해당 학생의 전체 수강후기 작성 비율
            ) {}
}
