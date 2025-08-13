package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.auth.exception.errorcode.AuthErrorCode;
import com.seniclass.server.domain.auth.service.AuthContext;
import com.seniclass.server.domain.lecture.domain.Assignment;
import com.seniclass.server.domain.lecture.repository.AssignmentRepository;
import com.seniclass.server.domain.student.domain.AssignmentSubmission;
import com.seniclass.server.domain.student.domain.Student;
import com.seniclass.server.domain.student.dto.request.AssignmentSubmissionFileRequest;
import com.seniclass.server.domain.student.dto.response.AssignmentSubmissionResponse;
import com.seniclass.server.domain.student.exception.errorcode.AssignmentSubmissionErrorCode;
import com.seniclass.server.domain.student.exception.errorcode.StudentErrorCode;
import com.seniclass.server.domain.student.repository.AssignmentSubmissionRepository;
import com.seniclass.server.domain.student.repository.StudentRepository;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.exception.errorcode.LectureErrorCode;
import com.seniclass.server.global.service.FileStorageService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AssignmentSubmissionServiceImpl implements AssignmentSubmissionService {

    private final AssignmentSubmissionRepository assignmentSubmissionRepository;
    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;
    private final FileStorageService fileStorageService;

    /** 파일로 과제 제출 */
    @Transactional
    @Override
    public AssignmentSubmissionResponse createSubmission(AssignmentSubmissionFileRequest request) {
        Long studentId = AuthContext.getCurrentUserId();
        log.info("학생 {}가 과제 {}에 파일로 제출합니다", studentId, request.assignmentId());

        Student student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new CommonException(StudentErrorCode.STUDENT_NOT_FOUND));

        Assignment assignment =
                assignmentRepository
                        .findById(request.assignmentId())
                        .orElseThrow(() -> new CommonException(LectureErrorCode.LECTURE_NOT_FOUND));

        // 이미 제출한 과제인지 확인
        if (assignmentSubmissionRepository.existsByStudentIdAndAssignmentId(
                studentId, request.assignmentId())) {
            throw new CommonException(
                    AssignmentSubmissionErrorCode.ASSIGNMENT_SUBMISSION_ALREADY_EXISTS);
        }

        // 마감일 체크
        if (assignment.getDueDateTime() != null
                && assignment.getDueDateTime().isBefore(LocalDateTime.now())) {
            throw new CommonException(AssignmentSubmissionErrorCode.ASSIGNMENT_DEADLINE_PASSED);
        }

        String fileName = fileStorageService.storeFile(request.file(), "assignments");
        log.info("파일 저장 완료: S3 key = {}", fileName);

        AssignmentSubmission submission =
                AssignmentSubmission.createAssignmentSubmissionWithFile(
                        student, assignment, request.content(), fileName);

        AssignmentSubmission savedSubmission = assignmentSubmissionRepository.save(submission);
        log.info(
                "과제 제출 저장 완료: submissionId = {}, fileKey = {}",
                savedSubmission.getId(),
                savedSubmission.getFileUrl());
        return convertToResponseWithFileUrl(savedSubmission);
    }

    /** 파일로 과제 수정 */
    @Transactional
    @Override
    public AssignmentSubmissionResponse updateSubmission(
            Long submissionId, AssignmentSubmissionFileRequest request) {
        Long studentId = AuthContext.getCurrentUserId();
        log.info("학생 {}가 과제 제출 {}를 파일로 수정합니다", studentId, submissionId);

        AssignmentSubmission submission =
                assignmentSubmissionRepository
                        .findById(submissionId)
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                AssignmentSubmissionErrorCode
                                                        .ASSIGNMENT_SUBMISSION_NOT_FOUND));

        if (!submission.getStudent().getId().equals(studentId)) {
            throw new CommonException(AuthErrorCode.UNAUTHORIZED);
        }

        String oldFilePath = submission.getFileUrl();
        String newFileName = fileStorageService.storeFile(request.file(), "assignments");

        submission.updateContent(request.content());
        submission.updateFileUrl(newFileName);

        AssignmentSubmission updatedSubmission = assignmentSubmissionRepository.save(submission);

        // 기존 파일이 있으면 삭제
        if (oldFilePath != null && !oldFilePath.isEmpty()) {
            try {
                fileStorageService.deleteFile(oldFilePath);
            } catch (Exception e) {
                log.warn("기존 파일 삭제 중 오류 발생: {}", oldFilePath, e);
            }
        }

        return convertToResponseWithFileUrl(updatedSubmission);
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
            throw new CommonException(AuthErrorCode.UNAUTHORIZED);
        }

        assignmentSubmissionRepository.delete(submission);
        // 파일 삭제
        if (submission.getFileUrl() != null && !submission.getFileUrl().isEmpty()) {
            try {
                fileStorageService.deleteFile(submission.getFileUrl());
            } catch (Exception e) {
                log.warn("과제 제출 파일 삭제 중 오류 발생: {}", submission.getFileUrl(), e);
            }
        }
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

        return convertToResponseWithFileUrl(submission);
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

    /** AssignmentSubmission을 Response로 변환 (파일 URL 포함) */
    private AssignmentSubmissionResponse convertToResponseWithFileUrl(
            AssignmentSubmission submission) {
        String fileUrl = null;
        if (submission.getFileUrl() != null && !submission.getFileUrl().isEmpty()) {
            fileUrl = fileStorageService.getFileUrl(submission.getFileUrl());
        }
        return AssignmentSubmissionResponse.from(submission, fileUrl);
    }
}
