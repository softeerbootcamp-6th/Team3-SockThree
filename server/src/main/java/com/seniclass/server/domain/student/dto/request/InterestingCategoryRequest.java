package com.seniclass.server.domain.student.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "관심 카테고리 추가 요청")
public record InterestingCategoryRequest(
        @Schema(description = "서브 카테고리 ID", example = "1") @NotNull(message = "서브 카테고리 ID는 필수입니다")
                Long subCategoryId) {}
