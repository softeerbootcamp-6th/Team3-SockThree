package com.seniclass.server.domain.lecture.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "동영상 업로드 요청")
public record VideoUploadRequest(
        @Schema(description = "동영상 제목", example = "React 기초 강의 1편")
                @NotBlank(message = "동영상 제목은 필수입니다.")
                String title,
        @Schema(description = "동영상 길이 (초 단위)", example = "1800")
                @NotNull(message = "동영상 길이는 필수입니다.")
                @Positive(message = "동영상 길이는 0보다 커야 합니다.")
                Long duration) {}
