package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.auth.service.AuthContext;
import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.exception.errorcode.LectureErrorCode;
import com.seniclass.server.domain.lecture.repository.LectureRepository;
import com.seniclass.server.domain.student.domain.LectureEnrollment;
import com.seniclass.server.domain.student.domain.Student;
import com.seniclass.server.domain.student.dto.request.LectureEnrollmentRequest;
import com.seniclass.server.domain.student.dto.response.LectureEnrollmentResponse;
import com.seniclass.server.domain.student.exception.errorcode.LectureEnrollmentErrorCode;
import com.seniclass.server.domain.student.exception.errorcode.StudentErrorCode;
import com.seniclass.server.domain.student.repository.LectureEnrollmentRepository;
import com.seniclass.server.domain.student.repository.StudentRepository;
import com.seniclass.server.global.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LectureEnrollmentServiceImpl implements LectureEnrollmentService {

    private final LectureEnrollmentRepository lectureEnrollmentRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;

    /** 현재 로그인한 학생의 수강 중인 강의 목록 조회 */
    @Override
    public Page<LectureEnrollmentResponse> getCurrentStudentEnrollments(Pageable pageable) {
        Long studentId = AuthContext.getCurrentUserId();

        Page<LectureEnrollment> enrollments =
                lectureEnrollmentRepository.findByStudentIdOrderByCreatedDtDesc(
                        studentId, pageable);

        return enrollments.map(this::toResponse);
    }

    /** 강의 수강 등록 */
    @Override
    @Transactional
    public LectureEnrollmentResponse enrollLecture(LectureEnrollmentRequest request) {
        Long studentId = AuthContext.getCurrentUserId();

        Student student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new CommonException(StudentErrorCode.STUDENT_NOT_FOUND));

        Lecture lecture =
                lectureRepository
                        .findById(request.lectureId())
                        .orElseThrow(() -> new CommonException(LectureErrorCode.LECTURE_NOT_FOUND));
        // 변경 필요

        // 이미 수강 등록한 강의인지 확인
        if (lectureEnrollmentRepository.existsByStudentIdAndLectureId(
                studentId, request.lectureId())) {
            throw new CommonException(LectureEnrollmentErrorCode.LECTURE_ENROLLMENT_ALREADY_EXISTS);
        }

        // TODO: 수강 정원 확인, 수강 가능 조건 확인 등 비즈니스 로직 추가

        LectureEnrollment enrollment = LectureEnrollment.createLectureEnrollment(student, lecture);
        LectureEnrollment saved = lectureEnrollmentRepository.save(enrollment);

        log.info(
                "Lecture enrollment added: student={}, lecture={}", studentId, request.lectureId());
        return toResponse(saved);
    }

    /** 강의 수강 취소 */
    @Override
    @Transactional
    public void cancelEnrollment(Long enrollmentId) {
        Long studentId = AuthContext.getCurrentUserId();

        LectureEnrollment enrollment =
                lectureEnrollmentRepository
                        .findById(enrollmentId)
                        .orElseThrow(() -> new CommonException(StudentErrorCode.STUDENT_NOT_FOUND));

        // 현재 로그인한 학생의 수강 등록인지 확인
        if (!enrollment.getStudent().getId().equals(studentId)) {
            throw new CommonException(StudentErrorCode.ACCESS_DENIED);
        }

        lectureEnrollmentRepository.delete(enrollment);
        log.info("Lecture enrollment cancelled: id={}, student={}", enrollmentId, studentId);
    }

    /** 강의 ID로 수강 취소 */
    @Override
    @Transactional
    public void cancelEnrollmentByLectureId(Long lectureId) {
        Long studentId = AuthContext.getCurrentUserId();

        LectureEnrollment enrollment =
                lectureEnrollmentRepository
                        .findByStudentIdAndLectureId(studentId, lectureId)
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                LectureEnrollmentErrorCode
                                                        .LECTURE_ENROLLMENT_NOT_FOUND));

        lectureEnrollmentRepository.delete(enrollment);
        log.info(
                "Lecture enrollment cancelled by lecture: lecture={}, student={}",
                lectureId,
                studentId);
    }

    /** 강의 수강 여부 확인 */
    @Override
    public boolean isEnrolled(Long lectureId) {
        Long studentId = AuthContext.getCurrentUserId();

        return lectureEnrollmentRepository.existsByStudentIdAndLectureId(studentId, lectureId);
    }

    /** 현재 학생의 수강 강의 수 조회 */
    @Override
    public long getCurrentStudentEnrollmentCount() {
        Long studentId = AuthContext.getCurrentUserId();

        return lectureEnrollmentRepository.countByStudentId(studentId);
    }

    /** 특정 학생의 수강 목록 조회 (관리자용) */
    @Override
    public Page<LectureEnrollmentResponse> getStudentEnrollments(
            Long studentId, Pageable pageable) {
        Page<LectureEnrollment> enrollments =
                lectureEnrollmentRepository.findByStudentIdOrderByCreatedDtDesc(
                        studentId, pageable);

        return enrollments.map(this::toResponse);
    }

    /** 특정 강의의 수강생 목록 조회 (관리자용) */
    @Override
    public Page<LectureEnrollmentResponse> getLectureEnrollments(
            Long lectureId, Pageable pageable) {
        Page<LectureEnrollment> enrollments =
                lectureEnrollmentRepository.findByLectureIdOrderByCreatedDtDesc(
                        lectureId, pageable);

        return enrollments.map(this::toResponse);
    }

    private LectureEnrollmentResponse toResponse(LectureEnrollment enrollment) {
        return new LectureEnrollmentResponse(
                enrollment.getId(),
                enrollment.getStudent().getId(),
                enrollment.getStudent().getName(),
                enrollment.getLecture().getId(),
                enrollment.getLecture().getName(),
                enrollment.getLecture().getSubCategory().getName(),
                enrollment.getLecture().getLevel().name(),
                enrollment.getLecture().getStartDate(),
                enrollment.getLecture().getEndDate(),
                enrollment.getLecture().getFee(),
                enrollment.getCreatedDt());
    }
}
