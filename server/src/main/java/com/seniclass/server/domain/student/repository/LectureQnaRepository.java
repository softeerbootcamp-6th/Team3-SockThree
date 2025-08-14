package com.seniclass.server.domain.student.repository;

import com.seniclass.server.domain.student.domain.LectureQna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureQnaRepository extends JpaRepository<LectureQna, Long> {

    /** 학생 ID로 QnA 목록 조회 (최신순) */
    Page<LectureQna> findByStudentIdOrderByCreatedDtDesc(Long studentId, Pageable pageable);

    /** 강의 ID로 QnA 목록 조회 (최신순) */
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

    /** 강의별 평균 답변률 (답변된 질문 수 / 총 질문 수) */
    @Query(
            """
    SELECT COALESCE(AVG(
            CASE WHEN lq.answer IS NOT NULL AND lq.answer <> '' THEN 100.0 ELSE 0.0 END
    ), 0.0)
    FROM LectureQna lq
    WHERE lq.lecture.id = :lectureId
""")
    Double getAnswerPercentByLectureId(@Param("lectureId") Long lectureId);
}
