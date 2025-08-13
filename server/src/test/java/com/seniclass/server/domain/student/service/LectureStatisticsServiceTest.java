package com.seniclass.server.domain.student.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.repository.LectureRepository;
import com.seniclass.server.domain.lecture.repository.VideoRepository;
import com.seniclass.server.domain.student.dto.AgeGroupGenderStatsDto;
import com.seniclass.server.domain.student.dto.AssignmentSubmissionStatsDto;
import com.seniclass.server.domain.student.dto.StudentVideoCountDto;
import com.seniclass.server.domain.student.dto.response.LectureStatisticsResponse;
import com.seniclass.server.domain.student.enums.Gender;
import com.seniclass.server.domain.student.repository.LectureStatisticsRepository;
import com.seniclass.server.global.exception.CommonException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
@DisplayName("강좌 통계 서비스 테스트")
class LectureStatisticsServiceTest {

    @Mock private LectureRepository lectureRepository;
    @Mock private LectureStatisticsRepository lectureStatisticsRepository;
    @Mock private VideoRepository videoRepository;

    @InjectMocks private LectureStatisticsServiceImpl lectureStatisticsService;

    private Lecture mockLecture;
    private Long lectureId;

    @BeforeEach
    void setUp() {
        lectureId = 1L;
        mockLecture = createMockLecture();
    }

    @Test
    @DisplayName("정상적인 강좌 통계 조회")
    void getLectureStatistics_ValidLecture_ReturnsStatistics() {
        // Given
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(mockLecture));
        when(videoRepository.countByChapterLectureId(lectureId)).thenReturn(10L);
        when(lectureStatisticsRepository.findStudentVideoWatchCounts(lectureId))
                .thenReturn(createMockVideoCountData());
        when(lectureStatisticsRepository.findCompletedStudentsCount(lectureId)).thenReturn(1);
        when(lectureStatisticsRepository.findRecentAssignmentSubmissionStatistics(
                        eq(lectureId), any(Pageable.class)))
                .thenReturn(createMockAssignmentData());
        when(lectureStatisticsRepository.findAgeGroupGenderStatistics(lectureId))
                .thenReturn(createMockAgeGroupData());

        // When
        LectureStatisticsResponse response =
                lectureStatisticsService.getLectureStatistics(lectureId);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.videoStatistics().averageWatchedVideos()).isEqualTo(7.5);
        assertThat(response.videoStatistics().completedStudentsCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("존재하지 않는 강의 ID로 조회 시 예외 발생")
    void getLectureStatistics_NotFoundLecture_ThrowsException() {
        // Given
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> lectureStatisticsService.getLectureStatistics(lectureId))
                .isInstanceOf(CommonException.class);
    }

    @Test
    @DisplayName("비디오 통계 - 정상 케이스")
    void getVideoStatistics_ValidData_ReturnsCorrectStatistics() {
        // given
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(mockLecture));
        when(videoRepository.countByChapterLectureId(lectureId)).thenReturn(10L);
        when(lectureStatisticsRepository.findStudentVideoWatchCounts(lectureId))
                .thenReturn(createMockVideoCountData());
        when(lectureStatisticsRepository.findCompletedStudentsCount(lectureId)).thenReturn(1);
        when(lectureStatisticsRepository.findRecentAssignmentSubmissionStatistics(
                        eq(lectureId), any(Pageable.class)))
                .thenReturn(List.of());
        when(lectureStatisticsRepository.findAgeGroupGenderStatistics(lectureId))
                .thenReturn(List.of());

        // when
        LectureStatisticsResponse response =
                lectureStatisticsService.getLectureStatistics(lectureId);

        // then
        assertThat(response.videoStatistics().averageWatchedVideos()).isEqualTo(7.5);
        assertThat(response.videoStatistics().completedStudentsCount()).isEqualTo(1);
    }

    // Helper methods for creating mock data
    private Lecture createMockLecture() {
        return mock(Lecture.class);
    }

    private List<StudentVideoCountDto> createMockVideoCountData() {
        return Arrays.asList(new StudentVideoCountDto(1L, 5L), new StudentVideoCountDto(2L, 10L));
    }

    private List<AssignmentSubmissionStatsDto> createMockAssignmentData() {
        return Arrays.asList(
                new AssignmentSubmissionStatsDto(1L, "과제 1", 10L, 8L),
                new AssignmentSubmissionStatsDto(2L, "과제 2", 10L, 6L));
    }

    private List<AgeGroupGenderStatsDto> createMockAgeGroupData() {
        return Arrays.asList(
                new AgeGroupGenderStatsDto("50-54", Gender.MALE, 5L),
                new AgeGroupGenderStatsDto("50-54", Gender.FEMALE, 3L),
                new AgeGroupGenderStatsDto("55-59", Gender.MALE, 2L),
                new AgeGroupGenderStatsDto("55-59", Gender.FEMALE, 4L));
    }
}
