package com.seniclass.server.domain.widget.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record AssignmentWidgetResponse(
        // 가장 빠른 마감 과제 정보
        Long upcomingAssignmentId,
        String upcomingAssignmentName,
        String upcomingAssignmentInstruction,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime upcomingDueDateTime,

        // 최근 제출 과제 정보
        Long recentSubmissionId,
        String recentSubmissionAssignmentName,
        String recentSubmissionContent,
        String recentSubmissionFileUrl,
        String feedback,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime recentSubmissionDateTime) {
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
