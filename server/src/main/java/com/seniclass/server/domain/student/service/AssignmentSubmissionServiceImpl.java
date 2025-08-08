package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.auth.service.AuthContext;
import com.seniclass.server.domain.lecture.domain.Assignment;
import com.seniclass.server.domain.lecture.repository.AssignmentRepository;
import com.seniclass.server.domain.student.domain.AssignmentSubmission;
import com.seniclass.server.domain.student.domain.Student;
import com.seniclass.server.domain.student.dto.AssignmentSubmissionRequest;
import com.seniclass.server.domain.student.dto.AssignmentSubmissionResponse;
import com.seniclass.server.domain.student.exception.errorcode.AssignmentSubmissionErrorCode;
import com.seniclass.server.domain.student.exception.errorcode.StudentErrorCode;
import com.seniclass.server.domain.student.repository.AssignmentSubmissionRepository;
import com.seniclass.server.domain.student.repository.StudentRepository;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.exception.errorcode.LectureErrorCode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AssignmentSubmissionServiceImpl implements AssignmentSubmissionService {

    private final AssignmentSubmissionRepository assignmentSubmissionRepository;
    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;

    /** 과제 제출 */
    @Transactional
    @Override
    public AssignmentSubmissionResponse submitAssignment(AssignmentSubmissionRequest request) {
        Long studentId = AuthContext.getCurrentUserId();

        Student student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new CommonException(StudentErrorCode.STUDENT_NOT_FOUND));

        Assignment assignment =
                assignmentRepository
                        .findById(request.assignmentId())
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                LectureErrorCode.LECTURE_NOT_FOUND)); // 추후
        // AssignmentErrorCode로 변경

        // 이미 제출한 과제인지 확인
        if (assignmentSubmissionRepository.existsByStudentIdAndAssignmentId(
                studentId, request.assignmentId())) {
            throw new CommonException(
                    AssignmentSubmissionErrorCode.ASSIGNMENT_SUBMISSION_ALREADY_EXISTS);
        }

        // 제출 기한 확인
        if (assignment.getDueDateTime() != null
                && LocalDateTime.now().isAfter(assignment.getDueDateTime())) {
            throw new CommonException(AssignmentSubmissionErrorCode.ASSIGNMENT_DEADLINE_PASSED);
        }

        AssignmentSubmission submission =
                AssignmentSubmission.createAssignmentSubmission(
                        student, assignment, request.submissionLink());

        AssignmentSubmission saved = assignmentSubmissionRepository.save(submission);

        log.info(
                "Assignment submitted: student={}, assignment={}",
                studentId,
                request.assignmentId());
        return AssignmentSubmissionResponse.from(saved);
    }

    /** 과제 제출 수정 */
    @Transactional
    @Override
    public AssignmentSubmissionResponse updateSubmission(
            Long submissionId, AssignmentSubmissionRequest request) {
        Long studentId = AuthContext.getCurrentUserId();

        AssignmentSubmission submission =
                assignmentSubmissionRepository
                        .findById(submissionId)
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                AssignmentSubmissionErrorCode
                                                        .ASSIGNMENT_SUBMISSION_NOT_FOUND));

        // 본인의 제출인지 확인
        if (!submission.getStudent().getId().equals(studentId)) {
            throw new CommonException(StudentErrorCode.ACCESS_DENIED);
        }

        // 제출 기한 확인
        Assignment assignment = submission.getAssignment();
        if (assignment.getDueDateTime() != null
                && LocalDateTime.now().isAfter(assignment.getDueDateTime())) {
            throw new CommonException(AssignmentSubmissionErrorCode.ASSIGNMENT_DEADLINE_PASSED);
        }

        submission.updateSubmissionLink(request.submissionLink());

        log.info(
                "Assignment submission updated: submissionId={}, student={}",
                submissionId,
                studentId);
        return AssignmentSubmissionResponse.from(submission);
    }

    /** 과제 제출 삭제 */
    @Transactional
    @Override
    public void deleteSubmission(Long submissionId) {
        Long studentId = AuthContext.getCurrentUserId();

        AssignmentSubmission submission =
                assignmentSubmissionRepository
                        .findById(submissionId)
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                AssignmentSubmissionErrorCode
                                                        .ASSIGNMENT_SUBMISSION_NOT_FOUND));

        // 본인의 제출인지 확인
        if (!submission.getStudent().getId().equals(studentId)) {
            throw new CommonException(StudentErrorCode.ACCESS_DENIED);
        }

        assignmentSubmissionRepository.delete(submission);

        log.info(
                "Assignment submission deleted: submissionId={}, student={}",
                submissionId,
                studentId);
    }

    /** 현재 학생의 과제 제출 목록 조회 */
    @Override
    public Page<AssignmentSubmissionResponse> getCurrentStudentSubmissions(Pageable pageable) {
        Long studentId = AuthContext.getCurrentUserId();

        Page<AssignmentSubmission> submissions =
                assignmentSubmissionRepository.findByStudentIdOrderByCreatedDtDesc(
                        studentId, pageable);

        return submissions.map(AssignmentSubmissionResponse::from);
    }

    /** 특정 과제에 대한 현재 학생의 제출 조회 */
    @Override
    public AssignmentSubmissionResponse getStudentSubmissionByAssignment(Long assignmentId) {
        Long studentId = AuthContext.getCurrentUserId();

        AssignmentSubmission submission =
                assignmentSubmissionRepository
                        .findByStudentIdAndAssignmentId(studentId, assignmentId)
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                AssignmentSubmissionErrorCode
                                                        .ASSIGNMENT_SUBMISSION_NOT_FOUND));

        return AssignmentSubmissionResponse.from(submission);
    }

    /** 특정 과제의 모든 제출 목록 조회 (관리자/강사용) */
    @Override
    public Page<AssignmentSubmissionResponse> getSubmissionsByAssignment(
            Long assignmentId, Pageable pageable) {
        Page<AssignmentSubmission> submissions =
                assignmentSubmissionRepository.findByAssignmentIdOrderByCreatedDtDesc(
                        assignmentId, pageable);

        return submissions.map(AssignmentSubmissionResponse::from);
    }

    /** 과제 제출 여부 확인 */
    @Override
    public boolean isSubmitted(Long assignmentId) {
        Long studentId = AuthContext.getCurrentUserId();

        return assignmentSubmissionRepository.existsByStudentIdAndAssignmentId(
                studentId, assignmentId);
    }

    /** 현재 학생의 총 과제 제출 수 조회 */
    @Override
    public long getCurrentStudentSubmissionCount() {
        Long studentId = AuthContext.getCurrentUserId();

        return assignmentSubmissionRepository.countByStudentId(studentId);
    }
}
