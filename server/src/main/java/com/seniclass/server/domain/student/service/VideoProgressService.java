package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.student.dto.VideoProgressRequest;
import com.seniclass.server.domain.student.dto.VideoProgressResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoProgressService {

    /** 비디오 진행 상황 업데이트 */
    VideoProgressResponse updateProgress(VideoProgressRequest request);

    /** 비디오 진행 상황 삭제 */
    void deleteProgress(Long progressId);

    /** 현재 학생의 비디오 진행 상황 목록 조회 */
    Page<VideoProgressResponse> getCurrentStudentProgress(Pageable pageable);

    /** 현재 학생의 특정 강의 비디오 진행 상황 조회 */
    Page<VideoProgressResponse> getCurrentStudentProgressByLecture(
            Long lectureId, Pageable pageable);

    /** 특정 비디오의 진행 상황 조회 */
    VideoProgressResponse getProgressByVideo(Long videoId);

    /** 특정 비디오의 모든 학생 진행 상황 조회 (관리자/강사용) */
    Page<VideoProgressResponse> getProgressByVideoForAll(Long videoId, Pageable pageable);
}
