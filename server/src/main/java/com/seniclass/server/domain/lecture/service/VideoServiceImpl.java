package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.aws.service.S3Service;
import com.seniclass.server.domain.lecture.domain.Chapter;
import com.seniclass.server.domain.lecture.domain.UploadTime;
import com.seniclass.server.domain.lecture.domain.Video;
import com.seniclass.server.domain.lecture.dto.request.VideoUploadRequest;
import com.seniclass.server.domain.lecture.dto.response.VideoUploadResponse;
import com.seniclass.server.domain.lecture.exception.errorcode.ChapterErrorCode;
import com.seniclass.server.domain.lecture.exception.errorcode.VideoErrorCode;
import com.seniclass.server.domain.lecture.repository.ChapterRepository;
import com.seniclass.server.domain.lecture.repository.VideoRepository;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.exception.errorcode.LectureErrorCode;
import com.seniclass.server.global.exception.errorcode.UserErrorCode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final ChapterRepository chapterRepository;
    private final S3Service s3Service;
    private final UploadTimeService uploadTimeService;

    @Override
    public VideoUploadResponse createVideoForUpload(
            Long userId, Long lectureId, Long chapterId, VideoUploadRequest request) {
        // 1. Chapter 존재 확인
        Chapter chapter =
                chapterRepository
                        .findById(chapterId)
                        .orElseThrow(() -> new CommonException(ChapterErrorCode.CHAPTER_NOT_FOUND));

        // 2. 강의 ID 일치 확인
        if (!Objects.equals(chapter.getLecture().getId(), lectureId)) {
            throw new CommonException(LectureErrorCode.LECTURE_NOT_FOUND);
        }

        // 3. 강사 권한 확인 (Chapter의 Lecture 소유자가 현재 사용자인지)
        if (!Objects.equals(chapter.getLecture().getTeacher().getId(), userId)) {
            throw new CommonException(UserErrorCode.USER_NOT_AUTHORIZED);
        }

        // 4. UploadTime을 기반으로 다음 공개 일정 계산
        LocalDateTime publicationDateTime = calculateNextPublicationDateTime(chapter.getLecture());

        // 5. 임시 S3 키로 Video 엔티티 생성 (나중에 실제 videoId로 업데이트)
        String tempS3Key = generateTempS3Key(lectureId, chapterId);
        Video video =
                Video.createVideo(
                        request.title(),
                        request.duration(),
                        tempS3Key,
                        publicationDateTime,
                        chapter);

        Video savedVideo = videoRepository.save(video);

        // 6. 실제 videoId로 S3 키 생성 및 업데이트
        String actualS3Key = generateActualS3Key(lectureId, chapterId, savedVideo.getId());
        savedVideo.updateUploadPath(actualS3Key);
        videoRepository.save(savedVideo);

        // 7. Upload Presigned URL 생성 (동영상 파일용)
        String uploadPresignedUrl = s3Service.generateUploadPresignedUrl(actualS3Key, "video/mp4");

        log.info(
                "Video created for upload: videoId={}, s3Key={}, lectureId={}, chapterId={}",
                savedVideo.getId(),
                actualS3Key,
                lectureId,
                chapterId);

        return new VideoUploadResponse(savedVideo.getId(), uploadPresignedUrl, actualS3Key);
    }

    @Override
    public void finalizeVideoUpload(Long videoId, String streamingPath) {
        Video video =
                videoRepository
                        .findById(videoId)
                        .orElseThrow(() -> new CommonException(VideoErrorCode.VIDEO_NOT_FOUND));

        video.finalizeVideoUpload(streamingPath);
        videoRepository.save(video);

        log.info("Video upload finalized: videoId={}, streamingPath={}", videoId, streamingPath);
    }

    /** 임시 S3 키 생성 (Video 엔티티 생성용) */
    private String generateTempS3Key(Long lectureId, Long chapterId) {
        long timestamp = System.currentTimeMillis();
        return String.format(
                "lectures/%d/chapters/%d/video/temp_%d/full", lectureId, chapterId, timestamp);
    }

    /** 실제 S3 키 생성: lectures/{lectureId}/chapters/{chapterId}/video/{videoId}/full */
    private String generateActualS3Key(Long lectureId, Long chapterId, Long videoId) {
        return String.format(
                "lectures/%d/chapters/%d/video/%d/full", lectureId, chapterId, videoId);
    }

    /** UploadTime을 기반으로 다음 공개 일정을 계산 현재 시간 이후 가장 가까운 업로드 시간을 반환 */
    private LocalDateTime calculateNextPublicationDateTime(
            com.seniclass.server.domain.lecture.domain.Lecture lecture) {
        List<UploadTime> uploadTimes = uploadTimeService.getAllUploadTimesEntity(lecture);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextPublicationTime = null;

        for (UploadTime uploadTime : uploadTimes) {
            DayOfWeek targetDayOfWeek = uploadTime.getDayOfWeek();
            LocalTime targetTime = uploadTime.getScheduledAt();

            // 이번 주 해당 요일의 날짜 계산
            LocalDateTime thisWeekTarget =
                    now.with(TemporalAdjusters.nextOrSame(targetDayOfWeek))
                            .with(targetTime)
                            .withSecond(0)
                            .withNano(0);

            // 현재 시간보다 이전이면 다음 주로 이동
            if (thisWeekTarget.isBefore(now) || thisWeekTarget.isEqual(now)) {
                thisWeekTarget = thisWeekTarget.plusWeeks(1);
            }

            // 가장 가까운 시간 선택
            if (nextPublicationTime == null || thisWeekTarget.isBefore(nextPublicationTime)) {
                nextPublicationTime = thisWeekTarget;
            }
        }

        return nextPublicationTime;
    }
}
