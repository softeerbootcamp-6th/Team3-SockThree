package com.seniclass.server.domain.lecture.repository;

import com.seniclass.server.domain.lecture.domain.Video;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    /** 특정 강의의 전체 비디오 개수 조회 */
    Long countByChapterLectureId(Long lectureId);

    /** 강의에 속한 동영상의 총 재생 시간을 조회합니다. */
    @Query(
            """
    SELECT COALESCE(SUM(v.duration), 0)
    FROM Video v
    JOIN v.chapter c
    WHERE c.lecture.id = :lectureId
""")
    Long getTotalDurationByLectureId(@Param("lectureId") Long lectureId);

    // *특정 강의의 학생별 총 재생 시간 조회*/
    @Query(
            """
  SELECT COALESCE(SUM(vp.playedTime), 0)
  FROM Video v
  JOIN v.chapter c
  LEFT JOIN VideoProgress vp
         ON vp.video = v
        AND vp.student.id = :studentId
  WHERE c.lecture.id = :lectureId
""")
    Long sumWatchedSecondsByLectureAndStudent(
            @Param("lectureId") Long lectureId, @Param("studentId") Long studentId);

    /** 특정 강의의 모든 비디오 조회 */
    List<Video> findByChapterLectureIdOrderByChapterIdAscIdAsc(Long lectureId);
}
