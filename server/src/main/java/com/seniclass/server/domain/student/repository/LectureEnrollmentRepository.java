package com.seniclass.server.domain.student.repository;

import com.seniclass.server.domain.student.domain.LectureEnrollment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureEnrollmentRepository extends JpaRepository<LectureEnrollment, Long> {

    /** 학생 ID로 수강 목록 조회 (생성일시 내림차순, 페이징) */
    Page<LectureEnrollment> findByStudentIdOrderByCreatedDtDesc(Long studentId, Pageable pageable);

    /** 강의 ID로 수강생 목록 조회 (생성일시 내림차순, 페이징) */
    Page<LectureEnrollment> findByLectureIdOrderByCreatedDtDesc(Long lectureId, Pageable pageable);

    /** 학생 ID와 강의 ID로 수강 등록 조회 */
    Optional<LectureEnrollment> findByStudentIdAndLectureId(Long studentId, Long lectureId);

    /** 학생 ID와 강의 ID로 수강 등록 존재 여부 확인 */
    boolean existsByStudentIdAndLectureId(Long studentId, Long lectureId);

    /** 학생 ID로 모든 수강 등록 삭제 */
    void deleteByStudentId(Long studentId);

    /** 강의 ID로 모든 수강 등록 삭제 */
    void deleteByLectureId(Long lectureId);

    /** 학생 ID로 수강 강의 개수 조회 */
    long countByStudentId(Long studentId);

    /** 강의 ID로 수강생 개수 조회 */
    long countByLectureId(Long lectureId);
}
