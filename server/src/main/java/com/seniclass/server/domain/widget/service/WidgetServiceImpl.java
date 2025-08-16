package com.seniclass.server.domain.widget.service;

import com.seniclass.server.domain.auth.service.AuthContext;
import com.seniclass.server.domain.aws.service.S3Service;
import com.seniclass.server.domain.lecture.domain.Assignment;
import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.domain.Review;
import com.seniclass.server.domain.lecture.exception.errorcode.LectureErrorCode;
import com.seniclass.server.domain.lecture.repository.AssignmentRepository;
import com.seniclass.server.domain.lecture.repository.LectureRepository;
import com.seniclass.server.domain.lecture.repository.ReviewRepository;
import com.seniclass.server.domain.student.domain.AssignmentSubmission;
import com.seniclass.server.domain.student.domain.LectureQna;
import com.seniclass.server.domain.student.domain.Student;
import com.seniclass.server.domain.student.repository.AssignmentSubmissionRepository;
import com.seniclass.server.domain.student.repository.LectureEnrollmentRepository;
import com.seniclass.server.domain.student.repository.LectureQnaRepository;
import com.seniclass.server.domain.student.repository.StudentRepository;
import com.seniclass.server.domain.teacher.domain.Teacher;
import com.seniclass.server.domain.teacher.enums.Type;
import com.seniclass.server.domain.teacher.repository.CareerRepository;
import com.seniclass.server.domain.widget.dto.response.*;
import com.seniclass.server.global.exception.CommonException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WidgetServiceImpl implements WidgetService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentSubmissionRepository assignmentSubmissionRepository;
    private final ReviewRepository reviewRepository;
    private final LectureQnaRepository lectureQnaRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;
    private final CareerRepository careerRepository;
    private final LectureEnrollmentRepository lectureEnrollmentRepository;
    private final S3Service S3Service;

    @Override
    public AssignmentWidgetResponse getAssignmentWidget(Long lectureId) {
        Long currentUserId = AuthContext.getCurrentUserId();

        // 현재 사용자가 학생인지 확인
        Student student =
                studentRepository
                        .findById(currentUserId)
                        .orElseThrow(() -> new IllegalArgumentException("학생 정보를 찾을 수 없습니다."));

        // 해당 강의의 마감 기한이 가장 빠른 과제 조회
        Optional<Assignment> upcomingAssignment =
                assignmentRepository.findTopByLectureIdAndDueDateTimeAfterOrderByDueDateTimeAsc(
                        lectureId, java.time.LocalDateTime.now());

        // 해당 강의에서 가장 최근 제출한 과제 조회
        Pageable recentPageable = PageRequest.of(0, 1);
        Page<AssignmentSubmission> recentSubmissions =
                assignmentSubmissionRepository
                        .findByStudentIdAndAssignmentLectureIdOrderByCreatedDtDesc(
                                student.getId(), lectureId, recentPageable);
        Optional<AssignmentSubmission> recentSubmission =
                recentSubmissions.hasContent()
                        ? Optional.of(recentSubmissions.getContent().get(0))
                        : Optional.empty();

        return AssignmentWidgetResponse.of(
                upcomingAssignment.map(Assignment::getId).orElse(null),
                upcomingAssignment.map(Assignment::getName).orElse(null),
                upcomingAssignment.map(Assignment::getInstruction).orElse(null),
                upcomingAssignment.map(Assignment::getDueDateTime).orElse(null),
                recentSubmission.map(AssignmentSubmission::getId).orElse(null),
                recentSubmission.map(sub -> sub.getAssignment().getName()).orElse(null),
                recentSubmission.map(AssignmentSubmission::getContent).orElse(null),
                recentSubmission.map(AssignmentSubmission::getFileUrl).orElse(null),
                recentSubmission.map(AssignmentSubmission::getFeedback).orElse(null),
                recentSubmission.map(AssignmentSubmission::getCreatedDt).orElse(null));
    }

    @Override
    public ReviewWidgetResponse getReviewWidget(Long lectureId) {
        // 해당 강의의 평점이 높은 리뷰 3개 조회
        Pageable pageable = PageRequest.of(0, 3);
        List<Review> topReviews =
                reviewRepository.findByLectureIdOrderByRatingDescCreatedDtDesc(lectureId, pageable);

        List<ReviewWidgetResponse.ReviewItem> reviewItems =
                topReviews.stream()
                        .map(
                                review -> {
                                    // 각 리뷰 작성자의 전체 수강후기 작성 비율 계산
                                    Double studentReviewRatio =
                                            reviewRepository.calculateStudentReviewRatio(
                                                    review.getStudent().getId());
                                    return new ReviewWidgetResponse.ReviewItem(
                                            review.getId(),
                                            review.getContent(),
                                            review.getRating(),
                                            review.getStudent().getName(),
                                            review.getLecture().getName(),
                                            studentReviewRatio != null ? studentReviewRatio : 0.0);
                                })
                        .collect(Collectors.toList());

        return ReviewWidgetResponse.of(reviewItems);
    }

    @Override
    public QnaWidgetResponse getQnaWidget(Long lectureId) {
        Long currentUserId = AuthContext.getCurrentUserId();

        // 현재 사용자가 학생인지 확인
        Student student =
                studentRepository
                        .findById(currentUserId)
                        .orElseThrow(() -> new IllegalArgumentException("학생 정보를 찾을 수 없습니다."));

        // 해당 강의의 최근 질문 7개 조회
        Pageable recentPageable = PageRequest.of(0, 7);
        Page<LectureQna> recentQuestions =
                lectureQnaRepository.findByLectureIdOrderByCreatedDtDesc(lectureId, recentPageable);

        List<QnaWidgetResponse.QnaItem> recentQnaItems =
                recentQuestions.getContent().stream()
                        .map(
                                qna ->
                                        new QnaWidgetResponse.QnaItem(
                                                qna.getId(),
                                                qna.getQuestion(),
                                                qna.getAnswer(),
                                                qna.getStudent().getName(),
                                                qna.getLecture().getName(),
                                                qna.getCreatedDt(),
                                                qna.getStudent()
                                                        .getId()
                                                        .equals(student.getId()) // 본인 질문인지 확인
                                                ))
                        .collect(Collectors.toList());

        // 해당 강의에서 내 질문 최대 7개 조회
        Pageable myPageable = PageRequest.of(0, 7);
        Page<LectureQna> myQuestions =
                lectureQnaRepository.findByStudentIdAndLectureIdOrderByCreatedDtDesc(
                        student.getId(), lectureId, myPageable);

        List<QnaWidgetResponse.QnaItem> myQnaItems =
                myQuestions.getContent().stream()
                        .map(
                                qna ->
                                        new QnaWidgetResponse.QnaItem(
                                                qna.getId(),
                                                qna.getQuestion(),
                                                qna.getAnswer(),
                                                qna.getStudent().getName(),
                                                qna.getLecture().getName(),
                                                qna.getCreatedDt(),
                                                true // 내 질문임
                                                ))
                        .collect(Collectors.toList());

        return QnaWidgetResponse.of(recentQnaItems, myQnaItems);
    }

    @Override
    public TeacherWidgetResponse getTeacherWidget(Long lectureId) {

        Lecture lecture =
                lectureRepository
                        .findById(lectureId)
                        .orElseThrow(() -> new CommonException(LectureErrorCode.LECTURE_NOT_FOUND));

        Teacher teacher = lecture.getTeacher();

        // 1. 강사의 이미지 URL 생성
        String imageKey = teacher.getImageKey();
        String presignedImageURL = S3Service.generatePresignedUrl(imageKey);

        // 2. 강사의 답변율 계산
        Double answerRate = lectureQnaRepository.getAnswerPercentByLectureId(lectureId);

        // 3. 강의 평균 리뷰 점수
        Double averageReviewRating = reviewRepository.findAverageRatingByLectureId(lectureId);

        // 4. 강사의 커리어를 리스트로 변환
        List<String> careerList = careerRepository.findCareerNameByTeacherId(teacher.getId());

        // 5. 강사의 강의 수
        Integer totalLectureCount = lectureRepository.countByTeacherId(teacher.getId());

        // 6. 강사의 총 수강생 수
        Integer totalStudentCount = lectureEnrollmentRepository.countByLectureId(lectureId);

        // 7. 강사의 경력 수
        Integer experienceCareerCount =
                careerRepository.countByTeacherIdAndType(teacher.getId(), Type.EXPERIENCE);

        // 8. 강사의 자격증 수
        Integer certificateCareerCount =
                careerRepository.countByTeacherIdAndType(teacher.getId(), Type.CERTIFICATE);

        // 9. 강사의 총 리뷰 수
        Integer totalReviewCount = reviewRepository.countByLectureTeacherId(teacher.getId());

        return TeacherWidgetResponse.of(
                presignedImageURL,
                teacher.getName(),
                answerRate != null ? answerRate.intValue() : 0,
                averageReviewRating != null ? averageReviewRating : 0.0,
                teacher.getInstruction(),
                careerList,
                totalLectureCount,
                totalStudentCount,
                experienceCareerCount,
                certificateCareerCount,
                totalReviewCount);
    }
}
