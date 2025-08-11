package com.seniclass.server.domain.student.repository;

import com.seniclass.server.domain.student.domain.AssignmentSubmission;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {

    /** 학생 ID로 과제 제출 목록 조회 (최신순) */
    Page<AssignmentSubmission> findByStudentIdOrderByCreatedDtDesc(
            Long studentId, Pageable pageable);

    /** 과제 ID로 제출 목록 조회 (최신순) */
    Page<AssignmentSubmission> findByAssignmentIdOrderByCreatedDtDesc(
            Long assignmentId, Pageable pageable);

    /** 학생 ID와 과제 ID로 제출 조회 */
    Optional<AssignmentSubmission> findByStudentIdAndAssignmentId(
            Long studentId, Long assignmentId);

    /** 학생 ID와 과제 ID로 제출 존재 여부 확인 */
    boolean existsByStudentIdAndAssignmentId(Long studentId, Long assignmentId);

    /** 학생의 총 과제 제출 수 */
    long countByStudentId(Long studentId);

    /** 특정 과제의 총 제출 수 */
    long countByAssignmentId(Long assignmentId);
}
