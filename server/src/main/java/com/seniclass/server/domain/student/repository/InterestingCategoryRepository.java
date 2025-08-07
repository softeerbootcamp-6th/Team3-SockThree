package com.seniclass.server.domain.student.repository;

import com.seniclass.server.domain.student.domain.InterestingCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestingCategoryRepository extends JpaRepository<InterestingCategory, Long> {

    /** 학생 ID로 관심 카테고리 목록 조회 (생성일시 내림차순) */
    List<InterestingCategory> findByStudentIdOrderByCreatedDtDesc(Long studentId);

    /** 학생 ID와 서브 카테고리 ID로 관심 카테고리 조회 */
    Optional<InterestingCategory> findByStudentIdAndSubCategoryId(
            Long studentId, Long subCategoryId);

    /** 학생 ID와 서브 카테고리 ID로 존재 여부 확인 */
    boolean existsByStudentIdAndSubCategoryId(Long studentId, Long subCategoryId);

    /** 학생 ID로 모든 관심 카테고리 삭제 */
    void deleteByStudentId(Long studentId);

    /** 서브 카테고리 ID로 모든 관심 카테고리 삭제 */
    void deleteBySubCategoryId(Long subCategoryId);
}
