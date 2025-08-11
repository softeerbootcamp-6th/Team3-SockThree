package com.seniclass.server.domain.student.repository;

import com.seniclass.server.domain.student.domain.VideoProgress;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoProgressRepository extends JpaRepository<VideoProgress, Long> {

    /** 학생 ID로 비디오 진행 상황 목록 조회 (최신순) */
    Page<VideoProgress> findByStudentIdOrderByUpdatedDtDesc(Long studentId, Pageable pageable);

    /** 비디오 ID로 진행 상황 목록 조회 (최신순) */
    Page<VideoProgress> findByVideoIdOrderByUpdatedDtDesc(Long videoId, Pageable pageable);

    /** 학생 ID와 비디오 ID로 진행 상황 조회 */
    Optional<VideoProgress> findByStudentIdAndVideoId(Long studentId, Long videoId);

    /** 특정 강의의 학생별 진행 상황 조회 */
    @Query(
            "SELECT vp FROM VideoProgress vp "
                    + "JOIN vp.video v "
                    + "JOIN v.chapter c "
                    + "WHERE vp.student.id = :studentId AND c.lecture.id = :lectureId "
                    + "ORDER BY vp.updatedDt DESC")
    Page<VideoProgress> findByStudentIdAndLectureIdOrderByUpdatedDtDesc(
            @Param("studentId") Long studentId,
            @Param("lectureId") Long lectureId,
            Pageable pageable);
}
