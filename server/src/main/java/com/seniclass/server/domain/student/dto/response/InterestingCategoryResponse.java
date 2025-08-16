package com.seniclass.server.domain.student.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "관심 카테고리 응답", name = "Student.InterestingCategoryResponse")
public record InterestingCategoryResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "관심 카테고리 ID", example = "1") Long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "서브 카테고리 ID", example = "1") Long subCategoryId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "서브 카테고리명", example = "Java") String subCategoryName,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "메인 카테고리 ID", example = "1") Long mainCategoryId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "메인 카테고리명", example = "프로그래밍") String mainCategoryName) {}
