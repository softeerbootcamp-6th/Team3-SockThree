package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.lecture.dto.request.ReviewCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.ReviewUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    /** 리뷰 생성 */
    ReviewResponse createReview(Long userId, ReviewCreateRequest request);

    /** 리뷰 수정 */
    ReviewResponse updateReview(Long userId, Long reviewId, ReviewUpdateRequest request);

    /** 리뷰 삭제 */
    void deleteReview(Long userId, Long reviewId);

    /** 특정 강의의 리뷰 목록 조회 */
    Page<ReviewResponse> getReviewsByLecture(Long lectureId, Pageable pageable);

    /** 특정 리뷰 조회 */
    ReviewResponse getReview(Long reviewId);

    /** 현재 학생의 특정 강의에 대한 리뷰 조회 */
    ReviewResponse getMyReviewByLecture(Long userId, Long lectureId);
}
