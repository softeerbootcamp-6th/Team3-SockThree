package com.seniclass.server.domain.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "관심 카테고리 응답")
public record InterestingCategoryResponse(
        @Schema(description = "관심 카테고리 ID", example = "1") Long id,
        @Schema(description = "서브 카테고리 ID", example = "1") Long subCategoryId,
        @Schema(description = "서브 카테고리명", example = "Java") String subCategoryName,
        @Schema(description = "메인 카테고리 ID", example = "1") Long mainCategoryId,
        @Schema(description = "메인 카테고리명", example = "프로그래밍") String mainCategoryName) {}
