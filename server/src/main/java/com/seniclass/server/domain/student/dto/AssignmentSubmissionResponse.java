package com.seniclass.server.domain.student.dto;

import com.seniclass.server.domain.student.domain.AssignmentSubmission;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "과제 제출 응답")
public record AssignmentSubmissionResponse(
        @Schema(description = "제출 ID", example = "1") Long id,
        @Schema(description = "학생 ID", example = "1") Long studentId,
        @Schema(description = "학생 이름", example = "김학생") String studentName,
        @Schema(description = "과제 ID", example = "1") Long assignmentId,
        @Schema(description = "과제 이름", example = "Java 기초 과제") String assignmentName,
        @Schema(description = "강의 ID", example = "1") Long lectureId,
        @Schema(description = "강의 이름", example = "Java 프로그래밍 기초") String lectureName,
        @Schema(description = "제출 파일 링크", example = "https://example.com/submission.pdf")
                String submissionLink,
        @Schema(description = "제출일시", example = "2023-12-01T10:00:00") LocalDateTime submittedAt) {
    public static AssignmentSubmissionResponse from(AssignmentSubmission submission) {
        return new AssignmentSubmissionResponse(
                submission.getId(),
                submission.getStudent().getId(),
                submission.getStudent().getName(),
                submission.getAssignment().getId(),
                submission.getAssignment().getName(),
                submission.getAssignment().getLecture().getId(),
                submission.getAssignment().getLecture().getName(),
                submission.getSubmissionLink(),
                submission.getCreatedDt());
    }
}
