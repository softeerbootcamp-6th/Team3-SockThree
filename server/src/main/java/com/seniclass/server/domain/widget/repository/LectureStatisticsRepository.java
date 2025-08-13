package com.seniclass.server.domain.widget.repository;

import com.seniclass.server.domain.student.domain.LectureEnrollment;
import com.seniclass.server.domain.widget.dto.AgeGroupGenderStatsDto;
import com.seniclass.server.domain.widget.dto.AssignmentSubmissionStatsDto;
import com.seniclass.server.domain.widget.dto.StudentVideoCountDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureStatisticsRepository extends JpaRepository<LectureEnrollment, Long> {

    /** 현재 수강생들의 비디오 시청 통계 조회 (80% 이상이면 수강) */
    @Query(
            "SELECT new com.seniclass.server.domain.widget.dto.StudentVideoCountDto("
                    + "s.id, "
                    + "COUNT(DISTINCT vp.video.id)) "
                    + "FROM LectureEnrollment le "
                    + "JOIN le.student s "
                    + "LEFT JOIN VideoProgress vp ON vp.student.id = s.id "
                    + "AND vp.video.chapter.lecture.id = :lectureId "
                    + "AND vp.playedTime >= (vp.video.duration * 0.8) "
                    + "WHERE le.lecture.id = :lectureId "
                    + "GROUP BY s.id")
    List<StudentVideoCountDto> findStudentVideoWatchCounts(@Param("lectureId") Long lectureId);

    /** 모든 비디오를 완주한 학생 수 조회 (80% 이상이면 수강) */
    @Query(
            "SELECT COUNT(DISTINCT s.id) "
                    + "FROM LectureEnrollment le "
                    + "JOIN le.student s "
                    + "WHERE le.lecture.id = :lectureId "
                    + "AND NOT EXISTS ("
                    + "    SELECT 1 FROM Video v "
                    + "    JOIN v.chapter c "
                    + "    WHERE c.lecture.id = :lectureId "
                    + "    AND NOT EXISTS ("
                    + "        SELECT 1 FROM VideoProgress vp "
                    + "        WHERE vp.student.id = s.id "
                    + "        AND vp.video.id = v.id "
                    + "        AND vp.playedTime >= (v.duration * 0.8)"
                    + "    )"
                    + ")")
    Integer findCompletedStudentsCount(@Param("lectureId") Long lectureId);

    /** 특정 강의의 과제 제출률 통계 (최근 5개) */
    @Query(
            "SELECT new com.seniclass.server.domain.widget.dto.AssignmentSubmissionStatsDto("
                    + "a.id, a.name, "
                    + "COUNT(DISTINCT le.student.id), "
                    + "COUNT(DISTINCT asub.student.id)) "
                    + "FROM Assignment a "
                    + "LEFT JOIN LectureEnrollment le ON le.lecture.id = a.lecture.id "
                    + "LEFT JOIN AssignmentSubmission asub ON asub.assignment.id = a.id "
                    + "WHERE a.lecture.id = :lectureId "
                    + "GROUP BY a.id, a.name, a.createdDt "
                    + "ORDER BY a.createdDt DESC")
    List<AssignmentSubmissionStatsDto> findRecentAssignmentSubmissionStatistics(
            @Param("lectureId") Long lectureId, Pageable pageable);

    /** 특정 강의의 연령대별 성별 통계 (순서 보장을 위한 정렬 키 추가) */
    @Query(
            "SELECT new com.seniclass.server.domain.widget.dto.AgeGroupGenderStatsDto("
                    + "CASE "
                    + "  WHEN s.age >= 50 AND s.age < 55 THEN '50-54' "
                    + "  WHEN s.age >= 55 AND s.age < 60 THEN '55-59' "
                    + "  WHEN s.age >= 60 AND s.age < 65 THEN '60-64' "
                    + "  WHEN s.age >= 65 AND s.age < 70 THEN '65-69' "
                    + "  WHEN s.age >= 70 AND s.age < 75 THEN '70-74' "
                    + "  WHEN s.age >= 75 THEN '75+' "
                    + "END, "
                    + "s.gender, "
                    + "COUNT(s)) "
                    + "FROM LectureEnrollment le "
                    + "JOIN le.student s "
                    + "WHERE le.lecture.id = :lectureId AND s.age >= 50 "
                    + "GROUP BY "
                    + "CASE "
                    + "  WHEN s.age >= 50 AND s.age < 55 THEN '50-54' "
                    + "  WHEN s.age >= 55 AND s.age < 60 THEN '55-59' "
                    + "  WHEN s.age >= 60 AND s.age < 65 THEN '60-64' "
                    + "  WHEN s.age >= 65 AND s.age < 70 THEN '65-69' "
                    + "  WHEN s.age >= 70 AND s.age < 75 THEN '70-74' "
                    + "  WHEN s.age >= 75 THEN '75+' "
                    + "END, s.gender "
                    + "ORDER BY "
                    + "CASE "
                    + "  WHEN s.age >= 50 AND s.age < 55 THEN 1 "
                    + "  WHEN s.age >= 55 AND s.age < 60 THEN 2 "
                    + "  WHEN s.age >= 60 AND s.age < 65 THEN 3 "
                    + "  WHEN s.age >= 65 AND s.age < 70 THEN 4 "
                    + "  WHEN s.age >= 70 AND s.age < 75 THEN 5 "
                    + "  WHEN s.age >= 75 THEN 6 "
                    + "END, s.gender")
    List<AgeGroupGenderStatsDto> findAgeGroupGenderStatistics(@Param("lectureId") Long lectureId);
}
