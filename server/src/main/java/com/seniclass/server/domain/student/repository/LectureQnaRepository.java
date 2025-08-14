package com.seniclass.server.domain.student.repository;

import com.seniclass.server.domain.student.domain.LectureQna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureQnaRepository extends JpaRepository<LectureQna, Long> {

    /** 학생 ID로 QnA 목록 조회 (최신순) */
    Page<LectureQna> findByStudentIdOrderByCreatedDtDesc(Long studentId, Pageable pageable);

    /** 강의 ID로 QnA 목록 조회 (최신순) - Page 반환 */
    Page<LectureQna> findByLectureIdOrderByCreatedDtDesc(Long lectureId, Pageable pageable);

    /** 학생의 총 질문 수 */
    long countByStudentId(Long studentId);

    /* --- 질문 수 관련 메서드 --- */

    /** 강의의 총 질문 수 */
    long countByLectureId(Long lectureId);

    /** 답변되지 않은 질문 수 (강의별) */
    long countByLectureIdAndAnswerIsNull(Long lectureId);

    /** 답변된 질문 수 (강의별) */
    long countByLectureIdAndAnswerIsNotNull(Long lectureId);

    /** 위젯용: 특정 강의에서 특정 학생의 질문 조회 - Page 반환 */
    Page<LectureQna> findByStudentIdAndLectureIdOrderByCreatedDtDesc(
            Long studentId, Long lectureId, Pageable pageable);
}
