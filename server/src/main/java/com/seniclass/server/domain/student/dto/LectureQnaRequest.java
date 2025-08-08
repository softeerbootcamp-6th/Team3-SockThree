package com.seniclass.server.domain.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "강의 QnA 등록 요청")
public record LectureQnaRequest(
        @Schema(description = "강의 ID", example = "1") @NotNull(message = "강의 ID는 필수입니다")
                Long lectureId,
        @Schema(description = "질문 내용", example = "자바의 다형성에 대해 자세히 설명해주세요.")
                @NotBlank(message = "질문 내용은 필수입니다")
                String question) {}
