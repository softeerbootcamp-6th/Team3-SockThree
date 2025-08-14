package com.seniclass.server.domain.lecture.repository;

import com.seniclass.server.domain.lecture.domain.Assignment;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    /** 특정 강의의 가장 제출 마감 시간이 이번주이면서 가장 빠른 과제 조회 */
    @Query(
            """
                SELECT a
                FROM Assignment a
                WHERE a.lecture.id = :lectureId
                  AND a.dueDateTime >= :now
                  AND a.dueDateTime < :nextMondayStart
                ORDER BY a.dueDateTime ASC
                LIMIT 1
            """)
    Assignment findFirstByDueThisWeek(
            @Param("lectureId") Long lectureId,
            @Param("now") LocalDateTime now,
            @Param("nextMondayStart") LocalDateTime nextMondayStart);
}
