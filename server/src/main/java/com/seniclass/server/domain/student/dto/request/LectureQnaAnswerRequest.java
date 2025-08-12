package com.seniclass.server.domain.student.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "강의 QnA 답변 요청")
public record LectureQnaAnswerRequest(
        @Schema(description = "답변 내용", example = "다형성은 하나의 객체가 여러 타입으로 표현될 수 있는 능력입니다...")
                @NotBlank(message = "답변 내용은 필수입니다")
                String answer) {}
