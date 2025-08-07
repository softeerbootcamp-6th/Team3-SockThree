package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.student.dto.InterestingCategoryRequest;
import com.seniclass.server.domain.student.dto.InterestingCategoryResponse;
import java.util.List;

public interface InterestingCategoryService {

    /** 현재 로그인한 학생의 관심 카테고리 목록 조회 */
    List<InterestingCategoryResponse> getCurrentStudentInterestingCategories();

    /** 관심 카테고리 추가 */
    InterestingCategoryResponse addInterestingCategory(InterestingCategoryRequest request);

    /** 관심 카테고리 삭제 */
    void removeInterestingCategory(Long interestingCategoryId);

    /** 서브 카테고리 ID로 관심 카테고리 삭제 */
    void removeInterestingCategoryBySubCategoryId(Long subCategoryId);

    /** 특정 학생의 관심 카테고리 목록 조회 (관리자용) */
    List<InterestingCategoryResponse> getStudentInterestingCategories(Long studentId);
}
