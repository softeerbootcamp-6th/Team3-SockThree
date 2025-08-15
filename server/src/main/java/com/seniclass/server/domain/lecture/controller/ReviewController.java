package com.seniclass.server.domain.lecture.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.lecture.dto.request.ReviewCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.ReviewUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.ReviewResponse;
import com.seniclass.server.domain.lecture.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review", description = "리뷰 관리 API")
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 작성 (수강생)", description = "수강생이 강의에 대한 리뷰를 작성합니다. STUDENT 권한 필요.")
    @PostMapping
    @RequireAuth(roles = {UserRole.STUDENT})
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse createReview(
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId,
            @Parameter(description = "리뷰 작성 요청") @Valid @RequestBody ReviewCreateRequest request) {
        return reviewService.createReview(userId, request);
    }

    @Operation(summary = "리뷰 수정 (수강생)", description = "수강생이 본인의 리뷰를 수정합니다. STUDENT 권한 필요.")
    @PutMapping("/{reviewId}")
    @RequireAuth(roles = {UserRole.STUDENT})
    public ReviewResponse updateReview(
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId,
            @Parameter(description = "수정할 리뷰 ID") @PathVariable Long reviewId,
            @Parameter(description = "리뷰 수정 요청") @Valid @RequestBody ReviewUpdateRequest request) {
        return reviewService.updateReview(userId, reviewId, request);
    }

    @Operation(summary = "리뷰 삭제 (수강생)", description = "수강생이 본인의 리뷰를 삭제합니다. STUDENT 권한 필요.")
    @DeleteMapping("/{reviewId}")
    @RequireAuth(roles = {UserRole.STUDENT})
    public ResponseEntity<Void> deleteReview(
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId,
            @Parameter(description = "삭제할 리뷰 ID") @PathVariable Long reviewId) {
        reviewService.deleteReview(userId, reviewId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "리뷰 단일 조회 (강사, 수강생)", description = "리뷰 ID로 특정 리뷰의 상세 정보를 조회합니다.")
    @GetMapping("/{reviewId}")
    @RequireAuth(roles = {UserRole.TEACHER, UserRole.STUDENT})
    public ReviewResponse getReview(
            @Parameter(description = "조회할 리뷰 ID") @PathVariable Long reviewId) {
        return reviewService.getReview(reviewId);
    }

    @Operation(summary = "강의별 리뷰 목록 조회 (강사, 수강생)", description = "특정 강의의 모든 리뷰를 페이징하여 조회합니다.")
    @GetMapping("/lectures/{lectureId}")
    @RequireAuth(roles = {UserRole.TEACHER, UserRole.STUDENT})
    public Page<ReviewResponse> getReviewsByLecture(
            @Parameter(description = "강의 ID") @PathVariable Long lectureId,
            @PageableDefault(size = 20, sort = "createdDt", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return reviewService.getReviewsByLecture(lectureId, pageable);
    }

    @Operation(summary = "내 리뷰 조회 (수강생)", description = "현재 학생의 특정 강의에 대한 리뷰를 조회합니다.")
    @GetMapping("/lectures/{lectureId}/my")
    @RequireAuth(roles = {UserRole.STUDENT})
    public ReviewResponse getMyReviewByLecture(
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId,
            @Parameter(description = "강의 ID") @PathVariable Long lectureId) {
        return reviewService.getMyReviewByLecture(userId, lectureId);
    }
}
