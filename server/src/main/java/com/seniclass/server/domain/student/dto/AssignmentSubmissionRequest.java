package com.seniclass.server.domain.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "과제 제출 요청")
public record AssignmentSubmissionRequest(
        @Schema(description = "과제 ID", example = "1") @NotNull(message = "과제 ID는 필수입니다")
                Long assignmentId,
        @Schema(description = "제출 파일 링크", example = "https://example.com/submission.pdf")
                @NotBlank(message = "제출 파일 링크는 필수입니다")
                String submissionLink) {}
