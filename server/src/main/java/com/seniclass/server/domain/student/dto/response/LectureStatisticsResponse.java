package com.seniclass.server.domain.student.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "강좌 수강생 통계 응답")
public record LectureStatisticsResponse(
        @Schema(description = "비디오 수강 통계") VideoStatistics videoStatistics,
        @Schema(description = "과제 제출률 통계") AssignmentSubmissionStatistics assignmentStatistics,
        @Schema(description = "연령대별 수강생 통계") List<AgeGroupStatistics> ageGroupStatistics) {

    @Schema(description = "비디오 수강 통계")
    public record VideoStatistics(
            @Schema(description = "평균 시청 비디오 수") Double averageWatchedVideos,
            @Schema(description = "모든 비디오를 완주한 학생 수") Integer completedStudentsCount) {}

    @Schema(description = "과제 제출률 통계")
    public record AssignmentSubmissionStatistics(
            @Schema(description = "최근 5개 과제 제출률 목록")
                    List<AssignmentSubmissionRate> recentAssignments) {

        @Schema(description = "과제별 제출률")
        public record AssignmentSubmissionRate(
                @Schema(description = "과제 ID") Long assignmentId,
                @Schema(description = "과제 이름") String assignmentName,
                @Schema(description = "제출률 (0.0 ~ 1.0)") Double submissionRate,
                @Schema(description = "제출한 학생 수") Integer submittedCount,
                @Schema(description = "전체 수강생 수") Integer totalStudentCount) {}
    }

    @Schema(description = "연령대별 수강생 통계")
    public record AgeGroupStatistics(
            @Schema(description = "연령대 (50대 이상 5살 단위)", example = "50-54") String ageGroup,
            @Schema(description = "남성 수강생 수") Integer maleCount,
            @Schema(description = "여성 수강생 수") Integer femaleCount) {}
}
