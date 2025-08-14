package com.seniclass.server.domain.student.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "과제 제출 파일 업로드 요청")
public record AssignmentSubmissionFileRequest(
        @Schema(description = "과제 ID", example = "1") @NotNull(message = "과제 ID는 필수입니다")
                Long assignmentId,
        @Schema(description = "과제 내용/설명", example = "과제 설명입니다") String content,
        @Schema(description = "제출 파일") @NotNull(message = "제출 파일은 필수입니다") MultipartFile file) {}
