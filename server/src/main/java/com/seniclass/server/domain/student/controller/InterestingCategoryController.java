package com.seniclass.server.domain.student.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.student.dto.InterestingCategoryRequest;
import com.seniclass.server.domain.student.dto.InterestingCategoryResponse;
import com.seniclass.server.domain.student.service.InterestingCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Student Interest Category", description = "학생 관심 카테고리 관리 API")
@RestController
@RequestMapping("/students/interests")
@RequiredArgsConstructor
public class InterestingCategoryController {

    private final InterestingCategoryService interestingCategoryService;

    @Operation(summary = "내 관심 카테고리 목록 조회", description = "현재 로그인한 학생의 관심 카테고리 목록을 조회합니다.")
    @GetMapping
    @RequireAuth(roles = {UserRole.STUDENT})
    public List<InterestingCategoryResponse> getMyInterestingCategories() {
        return interestingCategoryService.getCurrentStudentInterestingCategories();
    }

    @Operation(summary = "관심 카테고리 추가", description = "새로운 관심 카테고리를 추가합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RequireAuth(roles = {UserRole.STUDENT})
    public InterestingCategoryResponse addInterestingCategory(
            @Valid @RequestBody InterestingCategoryRequest request) {
        return interestingCategoryService.addInterestingCategory(request);
    }

    @Operation(summary = "관심 카테고리 삭제", description = "관심 카테고리를 삭제합니다.")
    @DeleteMapping("/{interestingCategoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequireAuth(roles = {UserRole.STUDENT})
    public void removeInterestingCategory(
            @Parameter(description = "관심 카테고리 ID") @PathVariable Long interestingCategoryId) {
        interestingCategoryService.removeInterestingCategory(interestingCategoryId);
    }

    @Operation(summary = "서브 카테고리로 관심 카테고리 삭제", description = "서브 카테고리 ID로 관심 카테고리를 삭제합니다.")
    @DeleteMapping("/subcategory/{subCategoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequireAuth(roles = {UserRole.STUDENT})
    public void removeInterestingCategoryBySubCategory(
            @Parameter(description = "서브 카테고리 ID") @PathVariable Long subCategoryId) {
        interestingCategoryService.removeInterestingCategoryBySubCategoryId(subCategoryId);
    }
}
