package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.student.dto.AssignmentSubmissionRequest;
import com.seniclass.server.domain.student.dto.AssignmentSubmissionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssignmentSubmissionService {

    /** 과제 제출 */
    AssignmentSubmissionResponse submitAssignment(AssignmentSubmissionRequest request);

    /** 과제 제출 수정 */
    AssignmentSubmissionResponse updateSubmission(
            Long submissionId, AssignmentSubmissionRequest request);

    /** 과제 제출 삭제 */
    void deleteSubmission(Long submissionId);

    /** 현재 학생의 과제 제출 목록 조회 */
    Page<AssignmentSubmissionResponse> getCurrentStudentSubmissions(Pageable pageable);

    /** 특정 과제에 대한 현재 학생의 제출 조회 */
    AssignmentSubmissionResponse getStudentSubmissionByAssignment(Long assignmentId);

    /** 특정 과제의 모든 제출 목록 조회 (관리자/강사용) */
    Page<AssignmentSubmissionResponse> getSubmissionsByAssignment(
            Long assignmentId, Pageable pageable);

    /** 과제 제출 여부 확인 */
    boolean isSubmitted(Long assignmentId);

    /** 현재 학생의 총 과제 제출 수 조회 */
    long getCurrentStudentSubmissionCount();
}
