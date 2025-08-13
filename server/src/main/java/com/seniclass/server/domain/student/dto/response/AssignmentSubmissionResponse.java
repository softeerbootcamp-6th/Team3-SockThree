package com.seniclass.server.domain.student.dto.response;

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
        @Schema(
                        description = "파일 다운로드 URL",
                        example = "https://s3.amazonaws.com/bucket/assignments/file.pdf")
                String fileUrl,
        @Schema(description = "과제 내용", example = "과제 설명입니다.") String content,
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
                null, // fileUrl은 서비스 레이어에서 설정
                submission.getContent(),
                submission.getCreatedDt());
    }

    public static AssignmentSubmissionResponse from(
            AssignmentSubmission submission, String fileUrl) {
        return new AssignmentSubmissionResponse(
                submission.getId(),
                submission.getStudent().getId(),
                submission.getStudent().getName(),
                submission.getAssignment().getId(),
                submission.getAssignment().getName(),
                submission.getAssignment().getLecture().getId(),
                submission.getAssignment().getLecture().getName(),
                fileUrl,
                submission.getContent(),
                submission.getCreatedDt());
    }
}
