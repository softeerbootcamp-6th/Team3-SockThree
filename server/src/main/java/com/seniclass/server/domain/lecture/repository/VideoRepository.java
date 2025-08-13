package com.seniclass.server.domain.lecture.repository;

import com.seniclass.server.domain.lecture.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    /** 특정 강의의 전체 비디오 개수 조회 */
    Long countByChapterLectureId(Long lectureId);
}
