package com.seniclass.server.domain.lecture.repository;

import com.seniclass.server.domain.lecture.domain.Review;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /** 위젯용: 특정 강의의 평점이 가장 높은 리뷰 조회 */
    List<Review> findByLectureIdOrderByRatingDescCreatedDtDesc(Long lectureId, Pageable pageable);

    /** 위젯용: 특정 학생의 전체 수강 강의 대비 리뷰 작성 비율 계산 */
    @Query(
            "SELECT "
                    + "CAST(COUNT(DISTINCT r.lecture.id) AS DOUBLE) / "
                    + "CAST(COUNT(DISTINCT le.lecture.id) AS DOUBLE) "
                    + "FROM LectureEnrollment le "
                    + "LEFT JOIN Review r ON r.student.id = le.student.id AND r.lecture.id = le.lecture.id "
                    + "WHERE le.student.id = :studentId")
    Double calculateStudentReviewRatio(@Param("studentId") Long studentId);
}
