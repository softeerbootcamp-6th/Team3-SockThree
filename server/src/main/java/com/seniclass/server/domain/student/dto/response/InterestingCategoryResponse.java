package com.seniclass.server.domain.student.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(required = true, description = "관심 카테고리 응답", name = "Student.InterestingCategoryResponse")
public record InterestingCategoryResponse(
        @Schema(required = true, description = "관심 카테고리 ID", example = "1") Long id,
        @Schema(required = true, description = "서브 카테고리 ID", example = "1") Long subCategoryId,
        @Schema(required = true, description = "서브 카테고리명", example = "Java") String subCategoryName,
        @Schema(required = true, description = "메인 카테고리 ID", example = "1") Long mainCategoryId,
        @Schema(required = true, description = "메인 카테고리명", example = "프로그래밍") String mainCategoryName) {}
