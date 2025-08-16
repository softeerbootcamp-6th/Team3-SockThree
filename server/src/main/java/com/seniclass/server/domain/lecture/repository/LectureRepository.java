package com.seniclass.server.domain.lecture.repository;

import com.seniclass.server.domain.lecture.domain.Lecture;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Optional<Lecture> findTopByNameOrderByCohortDesc(String name);

    // * 강사 ID로 강의 개수 조회 */
    Integer countByTeacherId(Long teacherId);
}
