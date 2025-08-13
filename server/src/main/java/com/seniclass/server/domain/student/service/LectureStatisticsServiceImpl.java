package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.repository.LectureRepository;
import com.seniclass.server.domain.lecture.repository.VideoRepository;
import com.seniclass.server.domain.student.dto.AgeGroupGenderStatsDto;
import com.seniclass.server.domain.student.dto.AssignmentSubmissionStatsDto;
import com.seniclass.server.domain.student.dto.StudentVideoCountDto;
import com.seniclass.server.domain.student.dto.response.LectureStatisticsResponse;
import com.seniclass.server.domain.student.dto.response.LectureStatisticsResponse.AgeGroupStatistics;
import com.seniclass.server.domain.student.dto.response.LectureStatisticsResponse.AssignmentSubmissionStatistics;
import com.seniclass.server.domain.student.dto.response.LectureStatisticsResponse.AssignmentSubmissionStatistics.AssignmentSubmissionRate;
import com.seniclass.server.domain.student.dto.response.LectureStatisticsResponse.VideoStatistics;
import com.seniclass.server.domain.student.enums.Gender;
import com.seniclass.server.domain.student.repository.LectureStatisticsRepository;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.exception.errorcode.LectureErrorCode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureStatisticsServiceImpl implements LectureStatisticsService {

    private final LectureStatisticsRepository lectureStatisticsRepository;
    private final LectureRepository lectureRepository;
    private final VideoRepository videoRepository;

    private static final List<String> AGE_BUCKET_ORDER =
            List.of("50-54", "55-59", "60-64", "65-69", "70-74", "75+");

    @Override
    public LectureStatisticsResponse getLectureStatistics(Long lectureId) {
        log.debug("Fetching lecture statistics for lectureId: {}", lectureId);

        // 강의 존재 여부 확인
        Lecture lecture =
                lectureRepository
                        .findById(lectureId)
                        .orElseThrow(() -> new CommonException(LectureErrorCode.LECTURE_NOT_FOUND));

        // 1. 비디오 수강 통계 조회
        VideoStatistics videoStatistics = getVideoStatistics(lectureId);

        // 2. 과제 제출률 통계 조회
        AssignmentSubmissionStatistics assignmentStatistics =
                getAssignmentSubmissionStatistics(lectureId);

        // 3. 연령대별 수강생 통계 조회
        List<AgeGroupStatistics> ageGroupStatistics = getAgeGroupStatistics(lectureId);

        return new LectureStatisticsResponse(
                videoStatistics, assignmentStatistics, ageGroupStatistics);
    }

    private VideoStatistics getVideoStatistics(Long lectureId) {
        try {
            // 전체 비디오 개수 조회
            Long totalVideos = videoRepository.countByChapterLectureId(lectureId);
            log.debug("Total videos for lecture {}: {}", lectureId, totalVideos);

            // 학생별 비디오 시청 개수 조회
            List<StudentVideoCountDto> studentVideoCounts =
                    lectureStatisticsRepository.findStudentVideoWatchCounts(lectureId);

            // 평균 시청 비디오 수 계산
            double averageWatchedVideos = 0.0;
            if (!studentVideoCounts.isEmpty()) {
                double totalWatched =
                        studentVideoCounts.stream()
                                .mapToLong(StudentVideoCountDto::videoCount)
                                .sum();
                averageWatchedVideos = totalWatched / studentVideoCounts.size();
            }

            // 모든 비디오를 완주한 학생 수 조회
            Integer completedStudentsCount = 0;
            if (totalVideos != null && totalVideos > 0) {
                completedStudentsCount =
                        lectureStatisticsRepository.findCompletedStudentsCount(lectureId);
            }

            log.debug(
                    "Video statistics - Average: {}, Completed: {}",
                    averageWatchedVideos,
                    completedStudentsCount);

            return new VideoStatistics(averageWatchedVideos, completedStudentsCount);
        } catch (Exception e) {
            log.error("Error calculating video statistics for lecture {}", lectureId, e);
            return new VideoStatistics(0.0, 0);
        }
    }

    private AssignmentSubmissionStatistics getAssignmentSubmissionStatistics(Long lectureId) {
        try {
            // 최근 5개 과제만 조회
            Pageable pageable = PageRequest.of(0, 5);
            List<AssignmentSubmissionStatsDto> assignmentStats =
                    lectureStatisticsRepository.findRecentAssignmentSubmissionStatistics(
                            lectureId, pageable);

            List<AssignmentSubmissionRate> submissionRates = new ArrayList<>();
            for (AssignmentSubmissionStatsDto stat : assignmentStats) {
                double submissionRate = 0.0;
                if (stat.totalStudentCount() > 0) {
                    submissionRate =
                            (double) stat.submittedStudentCount() / stat.totalStudentCount();
                }

                submissionRates.add(
                        new AssignmentSubmissionRate(
                                stat.assignmentId(),
                                stat.assignmentName(),
                                submissionRate,
                                stat.submittedStudentCount().intValue(),
                                stat.totalStudentCount().intValue()));
            }

            log.debug("Assignment submission statistics count: {}", submissionRates.size());
            return new AssignmentSubmissionStatistics(submissionRates);
        } catch (Exception e) {
            log.error("Error calculating assignment statistics for lecture {}", lectureId, e);
            return new AssignmentSubmissionStatistics(List.of());
        }
    }

    private List<AgeGroupStatistics> getAgeGroupStatistics(Long lectureId) {
        try {
            List<AgeGroupGenderStatsDto> ageGroupStats =
                    lectureStatisticsRepository.findAgeGroupGenderStatistics(lectureId);

            Map<String, Map<Gender, Integer>> ageGroupMap = new LinkedHashMap<>();

            // 미리 정의된 순서로 빈 버킷 생성
            for (String ageBucket : AGE_BUCKET_ORDER) {
                ageGroupMap.put(ageBucket, new LinkedHashMap<>());
                ageGroupMap.get(ageBucket).put(Gender.MALE, 0);
                ageGroupMap.get(ageBucket).put(Gender.FEMALE, 0);
            }

            // 실제 데이터로 채우기
            for (AgeGroupGenderStatsDto stat : ageGroupStats) {
                String ageGroup = stat.ageGroup();
                if (ageGroupMap.containsKey(ageGroup)) {
                    ageGroupMap.get(ageGroup).put(stat.gender(), stat.count().intValue());
                }
            }

            List<AgeGroupStatistics> result = new ArrayList<>();
            for (String ageGroup : AGE_BUCKET_ORDER) {
                Map<Gender, Integer> genderCounts = ageGroupMap.get(ageGroup);
                int maleCount = genderCounts.get(Gender.MALE);
                int femaleCount = genderCounts.get(Gender.FEMALE);

                // 해당 연령대에 학생이 있는 경우만 포함
                if (maleCount > 0 || femaleCount > 0) {
                    result.add(new AgeGroupStatistics(ageGroup, maleCount, femaleCount));
                }
            }

            log.debug("Age group statistics count: {}", result.size());
            return result;
        } catch (Exception e) {
            log.error("Error calculating age group statistics for lecture {}", lectureId, e);
            return List.of();
        }
    }
}
