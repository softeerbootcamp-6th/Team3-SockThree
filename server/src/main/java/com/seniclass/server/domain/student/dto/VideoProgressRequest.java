package com.seniclass.server.domain.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "비디오 진행 상황 업데이트 요청")
public record VideoProgressRequest(
        @Schema(description = "비디오 ID", example = "1") @NotNull(message = "비디오 ID는 필수입니다")
                Long videoId,
        @Schema(description = "재생 시간 (초)", example = "120")
                @NotNull(message = "재생 시간은 필수입니다")
                @PositiveOrZero(message = "재생 시간은 0 이상이어야 합니다")
                Long playedTime) {}
