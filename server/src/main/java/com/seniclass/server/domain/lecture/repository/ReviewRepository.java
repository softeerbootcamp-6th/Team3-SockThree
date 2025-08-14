package com.seniclass.server.domain.lecture.repository;

import com.seniclass.server.domain.lecture.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // * 강의 ID로 평균 평점 조회 */
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.lecture.id = :lectureId")
    Double findAverageRatingByLectureId(Long lectureId);

    // * 강사 ID로 리뷰 개수 조회 */
    @Query("SELECT COUNT(r) FROM Review r WHERE r.lecture.teacher.id = :teacherId")
    Integer countByTeacherId(Long teacherId);
}
