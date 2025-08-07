package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.auth.service.AuthContext;
import com.seniclass.server.domain.category.domain.SubCategory;
import com.seniclass.server.domain.category.exception.errorcode.CategoryErrorCode;
import com.seniclass.server.domain.category.repository.SubCategoryRepository;
import com.seniclass.server.domain.student.domain.InterestingCategory;
import com.seniclass.server.domain.student.domain.Student;
import com.seniclass.server.domain.student.dto.InterestingCategoryRequest;
import com.seniclass.server.domain.student.dto.InterestingCategoryResponse;
import com.seniclass.server.domain.student.exception.errorcode.InterestingCategoryErrorCode;
import com.seniclass.server.domain.student.exception.errorcode.StudentErrorCode;
import com.seniclass.server.domain.student.repository.InterestingCategoryRepository;
import com.seniclass.server.domain.student.repository.StudentRepository;
import com.seniclass.server.global.exception.CommonException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class InterestingCategoryServiceImpl implements InterestingCategoryService {

    private final InterestingCategoryRepository interestingCategoryRepository;
    private final StudentRepository studentRepository;
    private final SubCategoryRepository subCategoryRepository;

    /** 현재 로그인한 학생의 관심 카테고리 목록 조회 */
    @Override
    public List<InterestingCategoryResponse> getCurrentStudentInterestingCategories() {
        Long studentId = AuthContext.getCurrentUserId();

        List<InterestingCategory> interestingCategories =
                interestingCategoryRepository.findByStudentIdOrderByCreatedDtDesc(studentId);

        return interestingCategories.stream().map(this::toResponse).toList();
    }

    /** 관심 카테고리 추가 */
    @Override
    @Transactional
    public InterestingCategoryResponse addInterestingCategory(InterestingCategoryRequest request) {
        Long studentId = AuthContext.getCurrentUserId();

        Student student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new CommonException(StudentErrorCode.STUDENT_NOT_FOUND));

        SubCategory subCategory =
                subCategoryRepository
                        .findById(request.subCategoryId())
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                CategoryErrorCode.SUB_CATEGORY_NOT_FOUND));

        // 이미 등록된 관심 카테고리인지 확인
        if (interestingCategoryRepository.existsByStudentIdAndSubCategoryId(
                studentId, request.subCategoryId())) {
            throw new CommonException(
                    InterestingCategoryErrorCode.INTERESTING_CATEGORY_ALREADY_EXISTS);
        }

        InterestingCategory interestingCategory =
                InterestingCategory.createInterestingCategory(student, subCategory);
        InterestingCategory saved = interestingCategoryRepository.save(interestingCategory);

        log.info(
                "Interesting category added: student={}, subCategory={}",
                studentId,
                request.subCategoryId());
        return toResponse(saved);
    }

    /** 관심 카테고리 삭제 */
    @Override
    @Transactional
    public void removeInterestingCategory(Long interestingCategoryId) {
        Long studentId = AuthContext.getCurrentUserId();

        InterestingCategory interestingCategory =
                interestingCategoryRepository
                        .findById(interestingCategoryId)
                        .orElseThrow(() -> new CommonException(StudentErrorCode.STUDENT_NOT_FOUND));

        // 현재 로그인한 학생의 관심 카테고리인지 확인
        if (!interestingCategory.getStudent().getId().equals(studentId)) {
            throw new CommonException(StudentErrorCode.ACCESS_DENIED);
        }

        interestingCategoryRepository.delete(interestingCategory);
        log.info(
                "Interesting category removed: id={}, student={}",
                interestingCategoryId,
                studentId);
    }

    /** 서브 카테고리 ID로 관심 카테고리 삭제 */
    @Override
    @Transactional
    public void removeInterestingCategoryBySubCategoryId(Long subCategoryId) {
        Long studentId = AuthContext.getCurrentUserId();

        InterestingCategory interestingCategory =
                interestingCategoryRepository
                        .findByStudentIdAndSubCategoryId(studentId, subCategoryId)
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                InterestingCategoryErrorCode
                                                        .INTERESTING_CATEGORY_NOT_FOUND));

        interestingCategoryRepository.delete(interestingCategory);
        log.info(
                "Interesting category removed by subCategory: subCategory={}, student={}",
                subCategoryId,
                studentId);
    }

    /** 특정 학생의 관심 카테고리 목록 조회 (관리자용) */
    @Override
    public List<InterestingCategoryResponse> getStudentInterestingCategories(Long studentId) {
        List<InterestingCategory> interestingCategories =
                interestingCategoryRepository.findByStudentIdOrderByCreatedDtDesc(studentId);

        return interestingCategories.stream().map(this::toResponse).toList();
    }

    private InterestingCategoryResponse toResponse(InterestingCategory interestingCategory) {
        return new InterestingCategoryResponse(
                interestingCategory.getId(),
                interestingCategory.getStudent().getId(),
                interestingCategory.getSubCategory().getId(),
                interestingCategory.getSubCategory().getName(),
                interestingCategory.getSubCategory().getMainCategory().getId(),
                interestingCategory.getSubCategory().getMainCategory().getName());
    }
}
