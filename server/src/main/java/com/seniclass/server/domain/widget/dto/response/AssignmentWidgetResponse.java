package com.seniclass.server.domain.widget.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "과제 위젯 응답")
public record AssignmentWidgetResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "다가오는 과제 ID")
                Long upcomingAssignmentId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "다가오는 과제 이름")
                String upcomingAssignmentName,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "다가오는 과제 설명")
                String upcomingAssignmentInstruction,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "다가오는 과제 마감일")
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                LocalDateTime upcomingDueDateTime,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "최근 제출 ID")
                Long recentSubmissionId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "최근 제출 과제 이름")
                String recentSubmissionAssignmentName,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "최근 제출 내용")
                String recentSubmissionContent,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "최근 제출 파일 URL")
                String recentSubmissionFileUrl,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "피드백") String feedback,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "최근 제출일")
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                LocalDateTime recentSubmissionDateTime) {
    public static AssignmentWidgetResponse of(
            Long upcomingAssignmentId,
            String upcomingAssignmentName,
            String upcomingAssignmentInstruction,
            LocalDateTime upcomingDueDateTime,
            Long recentSubmissionId,
            String recentSubmissionAssignmentName,
            String recentSubmissionContent,
            String recentSubmissionFileUrl,
            String feedback,
            LocalDateTime recentSubmissionDateTime) {
        return new AssignmentWidgetResponse(
                upcomingAssignmentId,
                upcomingAssignmentName,
                upcomingAssignmentInstruction,
                upcomingDueDateTime,
                recentSubmissionId,
                recentSubmissionAssignmentName,
                recentSubmissionContent,
                recentSubmissionFileUrl,
                feedback,
                recentSubmissionDateTime);
    }
}
