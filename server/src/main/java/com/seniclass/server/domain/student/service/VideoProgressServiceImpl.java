package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.auth.service.AuthContext;
import com.seniclass.server.domain.lecture.domain.Video;
import com.seniclass.server.domain.lecture.repository.VideoRepository;
import com.seniclass.server.domain.student.domain.Student;
import com.seniclass.server.domain.student.domain.VideoProgress;
import com.seniclass.server.domain.student.dto.VideoProgressRequest;
import com.seniclass.server.domain.student.dto.VideoProgressResponse;
import com.seniclass.server.domain.student.exception.errorcode.StudentErrorCode;
import com.seniclass.server.domain.student.exception.errorcode.VideoProgressErrorCode;
import com.seniclass.server.domain.student.repository.StudentRepository;
import com.seniclass.server.domain.student.repository.VideoProgressRepository;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.exception.errorcode.LectureErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VideoProgressServiceImpl implements VideoProgressService {

    private final VideoProgressRepository videoProgressRepository;
    private final StudentRepository studentRepository;
    private final VideoRepository videoRepository;

    /** 비디오 진행 상황 업데이트 */
    @Transactional
    @Override
    public VideoProgressResponse updateProgress(VideoProgressRequest request) {
        Long studentId = AuthContext.getCurrentUserId();

        Student student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new CommonException(StudentErrorCode.STUDENT_NOT_FOUND));

        Video video =
                videoRepository
                        .findById(request.videoId())
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                LectureErrorCode
                                                        .LECTURE_NOT_FOUND)); // 추후 VideoErrorCode로
        // 변경

        // 기존 진행 상황이 있는지 확인
        VideoProgress progress =
                videoProgressRepository
                        .findByStudentIdAndVideoId(studentId, request.videoId())
                        .orElse(null);

        if (progress == null) {
            // 새로 생성
            progress = VideoProgress.createVideoProgress(student, video, request.playedTime());
        } else {
            // 기존 진행 상황 업데이트
            progress.updatePlayedTime(request.playedTime());
        }

        VideoProgress saved = videoProgressRepository.save(progress);

        log.info(
                "Video progress updated: student={}, video={}, playedTime={}",
                studentId,
                request.videoId(),
                request.playedTime());
        return VideoProgressResponse.from(saved);
    }

    /** 비디오 진행 상황 삭제 */
    @Transactional
    @Override
    public void deleteProgress(Long progressId) {
        Long studentId = AuthContext.getCurrentUserId();

        VideoProgress progress =
                videoProgressRepository
                        .findById(progressId)
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                VideoProgressErrorCode.VIDEO_PROGRESS_NOT_FOUND));

        // 본인의 진행 상황인지 확인
        if (!progress.getStudent().getId().equals(studentId)) {
            throw new CommonException(StudentErrorCode.ACCESS_DENIED);
        }

        videoProgressRepository.delete(progress);

        log.info("Video progress deleted: progressId={}, student={}", progressId, studentId);
    }

    /** 현재 학생의 비디오 진행 상황 목록 조회 */
    @Override
    public Page<VideoProgressResponse> getCurrentStudentProgress(Pageable pageable) {
        Long studentId = AuthContext.getCurrentUserId();

        Page<VideoProgress> progresses =
                videoProgressRepository.findByStudentIdOrderByUpdatedDtDesc(studentId, pageable);

        return progresses.map(VideoProgressResponse::from);
    }

    /** 현재 학생의 특정 강의 비디오 진행 상황 조회 */
    @Override
    public Page<VideoProgressResponse> getCurrentStudentProgressByLecture(
            Long lectureId, Pageable pageable) {
        Long studentId = AuthContext.getCurrentUserId();

        Page<VideoProgress> progresses =
                videoProgressRepository.findByStudentIdAndLectureIdOrderByUpdatedDtDesc(
                        studentId, lectureId, pageable);

        return progresses.map(VideoProgressResponse::from);
    }

    /** 특정 비디오의 진행 상황 조회 */
    @Override
    public VideoProgressResponse getProgressByVideo(Long videoId) {
        Long studentId = AuthContext.getCurrentUserId();

        VideoProgress progress =
                videoProgressRepository
                        .findByStudentIdAndVideoId(studentId, videoId)
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                VideoProgressErrorCode.VIDEO_PROGRESS_NOT_FOUND));

        return VideoProgressResponse.from(progress);
    }

    /** 특정 비디오의 모든 학생 진행 상황 조회 (강사용) */
    @Override
    public Page<VideoProgressResponse> getProgressByVideoForAll(Long videoId, Pageable pageable) {
        Page<VideoProgress> progresses =
                videoProgressRepository.findByVideoIdOrderByUpdatedDtDesc(videoId, pageable);

        return progresses.map(VideoProgressResponse::from);
    }
}
