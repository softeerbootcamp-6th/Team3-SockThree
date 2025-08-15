package com.seniclass.server.domain.student.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "과제 제출 피드백 업데이트 요청")
public record FeedbackUpdateRequest(
        @Schema(description = "피드백 내용", example = "잘 작성된 과제입니다. 다음에는 더 자세한 설명을 추가해보세요.")
                @NotBlank(message = "피드백 내용은 필수입니다.")
                String feedback) {}
