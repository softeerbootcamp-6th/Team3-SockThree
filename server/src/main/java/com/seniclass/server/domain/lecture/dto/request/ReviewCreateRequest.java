package com.seniclass.server.domain.lecture.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "리뷰 생성 요청")
public record ReviewCreateRequest(
        @Schema(description = "강의 ID", example = "1") @NotNull(message = "강의 ID는 필수입니다.")
                Long lectureId,
        @Schema(description = "리뷰 내용", example = "정말 좋은 강의였습니다!")
                @NotBlank(message = "리뷰 내용은 필수입니다.")
                String content,
        @Schema(description = "평점 (1.0 ~ 5.0)", example = "4.5")
                @NotNull(message = "평점은 필수입니다.")
                @DecimalMin(value = "1.0", message = "평점은 1.0 이상이어야 합니다.")
                @DecimalMax(value = "5.0", message = "평점은 5.0 이하여야 합니다.")
                Double rating) {}
