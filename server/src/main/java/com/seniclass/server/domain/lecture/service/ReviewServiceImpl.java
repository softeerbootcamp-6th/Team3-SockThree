package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.domain.Review;
import com.seniclass.server.domain.lecture.dto.request.ReviewCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.ReviewUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.ReviewResponse;
import com.seniclass.server.domain.lecture.exception.errorcode.ReviewErrorCode;
import com.seniclass.server.domain.lecture.repository.LectureRepository;
import com.seniclass.server.domain.lecture.repository.ReviewRepository;
import com.seniclass.server.domain.student.domain.Student;
import com.seniclass.server.domain.student.exception.errorcode.StudentErrorCode;
import com.seniclass.server.domain.student.repository.LectureEnrollmentRepository;
import com.seniclass.server.domain.student.repository.StudentRepository;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.exception.errorcode.LectureErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final LectureRepository lectureRepository;
    private final StudentRepository studentRepository;
    private final LectureEnrollmentRepository lectureEnrollmentRepository;

    @Transactional
    @Override
    public ReviewResponse createReview(Long userId, ReviewCreateRequest request) {
        log.info("학생 {}가 강의 {}에 리뷰를 작성합니다", userId, request.lectureId());

        Student student =
                studentRepository
                        .findById(userId)
                        .orElseThrow(() -> new CommonException(StudentErrorCode.STUDENT_NOT_FOUND));

        Lecture lecture =
                lectureRepository
                        .findById(request.lectureId())
                        .orElseThrow(() -> new CommonException(LectureErrorCode.LECTURE_NOT_FOUND));

        // 수강 중인지 확인
        if (!lectureEnrollmentRepository.existsByStudentIdAndLectureId(
                userId, request.lectureId())) {
            throw new CommonException(ReviewErrorCode.NOT_ENROLLED_STUDENT);
        }

        // 이미 리뷰를 작성했는지 확인
        if (reviewRepository.existsByStudentIdAndLectureId(userId, request.lectureId())) {
            throw new CommonException(ReviewErrorCode.REVIEW_ALREADY_EXISTS);
        }

        Review review = Review.createReview(request.content(), request.rating(), lecture, student);
        Review savedReview = reviewRepository.save(review);

        log.info("리뷰 작성 완료: reviewId = {}", savedReview.getId());
        return ReviewResponse.from(savedReview);
    }

    @Transactional
    @Override
    public ReviewResponse updateReview(Long userId, Long reviewId, ReviewUpdateRequest request) {
        log.info("학생 {}가 리뷰 {}를 수정합니다", userId, reviewId);

        Review review =
                reviewRepository
                        .findById(reviewId)
                        .orElseThrow(() -> new CommonException(ReviewErrorCode.REVIEW_NOT_FOUND));

        // 본인의 리뷰인지 확인
        if (!review.getStudent().getId().equals(userId)) {
            throw new CommonException(ReviewErrorCode.REVIEW_ACCESS_DENIED);
        }

        review.updateReview(request.content(), request.rating());
        Review updatedReview = reviewRepository.save(review);

        log.info("리뷰 수정 완료: reviewId = {}", updatedReview.getId());
        return ReviewResponse.from(updatedReview);
    }

    @Transactional
    @Override
    public void deleteReview(Long userId, Long reviewId) {
        log.info("학생 {}가 리뷰 {}를 삭제합니다", userId, reviewId);

        Review review =
                reviewRepository
                        .findById(reviewId)
                        .orElseThrow(() -> new CommonException(ReviewErrorCode.REVIEW_NOT_FOUND));

        // 본인의 리뷰인지 확인
        if (!review.getStudent().getId().equals(userId)) {
            throw new CommonException(ReviewErrorCode.REVIEW_ACCESS_DENIED);
        }

        reviewRepository.delete(review);
        log.info("리뷰 삭제 완료: reviewId = {}", reviewId);
    }

    @Override
    public Page<ReviewResponse> getReviewsByLecture(Long lectureId, Pageable pageable) {
        // 강의 존재 여부 확인
        if (!lectureRepository.existsById(lectureId)) {
            throw new CommonException(LectureErrorCode.LECTURE_NOT_FOUND);
        }

        Page<Review> reviews =
                reviewRepository.findByLectureIdOrderByCreatedDtDesc(lectureId, pageable);
        return reviews.map(ReviewResponse::from);
    }

    @Override
    public ReviewResponse getReview(Long reviewId) {
        Review review =
                reviewRepository
                        .findById(reviewId)
                        .orElseThrow(() -> new CommonException(ReviewErrorCode.REVIEW_NOT_FOUND));

        return ReviewResponse.from(review);
    }

    @Override
    public ReviewResponse getMyReviewByLecture(Long userId, Long lectureId) {
        Review review =
                reviewRepository
                        .findByStudentIdAndLectureId(userId, lectureId)
                        .orElseThrow(() -> new CommonException(ReviewErrorCode.REVIEW_NOT_FOUND));

        return ReviewResponse.from(review);
    }
}
