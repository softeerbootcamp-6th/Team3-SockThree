package com.seniclass.server.domain.widget.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(required = true, description = "강좌 수강생 통계 응답", name = "Widget.LectureStatisticsResponse")
public record LectureStatisticsResponse(
        @Schema(required = true, description = "비디오 수강 통계") VideoStatistics videoStatistics,
        @Schema(required = true, description = "과제 제출률 통계") AssignmentSubmissionStatistics assignmentStatistics,
        @Schema(required = true, description = "연령대별 수강생 통계") List<AgeGroupStatistics> ageGroupStatistics) {

    @Schema(required = true, description = "비디오 수강 통계", name = "Widget.LectureStatisticsResponse.VideoStatistics")
    public record VideoStatistics(
            @Schema(required = true, description = "평균 시청 비디오 수") Double averageWatchedVideos,
            @Schema(required = true, description = "모든 비디오를 완주한 학생 수") Integer completedStudentsCount) {}

    @Schema(required = true, description = "과제 제출률 통계", name = "Widget.LectureStatisticsResponse.AssignmentSubmissionStatistics")
    public record AssignmentSubmissionStatistics(
            @Schema(required = true, description = "최근 5개 과제 제출률 목록")
                    List<AssignmentSubmissionRate> recentAssignments) {

        @Schema(required = true, description = "과제별 제출률", name = "Widget.LectureStatisticsResponse.AssignmentSubmissionRate")
        public record AssignmentSubmissionRate(
                @Schema(required = true, description = "과제 ID") Long assignmentId,
                @Schema(required = true, description = "과제 이름") String assignmentName,
                @Schema(required = true, description = "제출률 (0.0 ~ 1.0)") Double submissionRate,
                @Schema(required = true, description = "제출한 학생 수") Integer submittedCount,
                @Schema(required = true, description = "전체 수강생 수") Integer totalStudentCount) {}
    }

    @Schema(required = true, description = "연령대별 수강생 통계", name = "Widget.LectureStatisticsResponse.AgeGroupStatistics")
    public record AgeGroupStatistics(
            @Schema(required = true, description = "연령대 (50대 이상 5살 단위)", example = "50-54") String ageGroup,
            @Schema(required = true, description = "남성 수강생 수") Integer maleCount,
            @Schema(required = true, description = "여성 수강생 수") Integer femaleCount) {}
}
