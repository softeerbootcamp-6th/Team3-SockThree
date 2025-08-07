package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.student.dto.LectureEnrollmentRequest;
import com.seniclass.server.domain.student.dto.LectureEnrollmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LectureEnrollmentService {

    /** 현재 로그인한 학생의 수강 중인 강의 목록 조회 */
    Page<LectureEnrollmentResponse> getCurrentStudentEnrollments(Pageable pageable);

    /** 강의 수강 등록 */
    LectureEnrollmentResponse enrollLecture(LectureEnrollmentRequest request);

    /** 강의 수강 취소 */
    void cancelEnrollment(Long enrollmentId);

    /** 강의 ID로 수강 취소 */
    void cancelEnrollmentByLectureId(Long lectureId);

    /** 강의 수강 여부 확인 */
    boolean isEnrolled(Long lectureId);

    /** 현재 학생의 수강 강의 수 조회 */
    long getCurrentStudentEnrollmentCount();

    /** 특정 학생의 수강 목록 조회 (관리자용) */
    Page<LectureEnrollmentResponse> getStudentEnrollments(Long studentId, Pageable pageable);

    /** 특정 강의의 수강생 목록 조회 (관리자용) */
    Page<LectureEnrollmentResponse> getLectureEnrollments(Long lectureId, Pageable pageable);
}
