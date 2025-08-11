package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.auth.exception.errorcode.AuthErrorCode;
import com.seniclass.server.domain.auth.service.AuthContext;
import com.seniclass.server.domain.lecture.domain.Assignment;
import com.seniclass.server.domain.lecture.repository.AssignmentRepository;
import com.seniclass.server.domain.student.domain.AssignmentSubmission;
import com.seniclass.server.domain.student.domain.Student;
import com.seniclass.server.domain.student.dto.AssignmentSubmissionFileRequest;
import com.seniclass.server.domain.student.dto.AssignmentSubmissionResponse;
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
import org.springframework.core.io.Resource;
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
    private final FileStorageService fileStorageService;

    /** 파일로 과제 제출 */
    @Transactional
    @Override
    public AssignmentSubmissionResponse submitAssignmentWithFile(
            AssignmentSubmissionFileRequest request) {
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

        AssignmentSubmission submission =
                AssignmentSubmission.createAssignmentSubmissionWithFile(
                        student, assignment, request.content(), fileName);

        return AssignmentSubmissionResponse.from(assignmentSubmissionRepository.save(submission));
    }

    /** 파일로 과제 수정 */
    @Transactional
    @Override
    public AssignmentSubmissionResponse updateSubmissionWithFile(
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

        String oldFilePath = submission.getFilePath();
        String newFileName = fileStorageService.storeFile(request.file(), "assignments");

        submission.updateContent(request.content());
        submission.updateFilePath(newFileName);

        AssignmentSubmission updatedSubmission = assignmentSubmissionRepository.save(submission);

        // 기존 파일이 있으면 삭제
        if (oldFilePath != null && !oldFilePath.isEmpty()) {
            try {
                fileStorageService.deleteFile(oldFilePath);
            } catch (Exception e) {
                log.warn("기존 파일 삭제 중 오류 발생: {}", oldFilePath, e);
            }
        }

        return AssignmentSubmissionResponse.from(updatedSubmission);
    }

    /** 제출된 파일 다운로드 */
    @Override
    public Resource downloadSubmissionFile(Long submissionId) {
        Long studentId = AuthContext.getCurrentUserId();
        log.info("사용자 {}가 과제 제출 {}의 파일을 다운로드합니다", studentId, submissionId);

        AssignmentSubmission submission =
                assignmentSubmissionRepository
                        .findById(submissionId)
                        .orElseThrow(
                                () ->
                                        new CommonException(
                                                AssignmentSubmissionErrorCode
                                                        .ASSIGNMENT_SUBMISSION_NOT_FOUND));

        // 본인만 접근 가능 (추후 강사도 접근 가능하도록 확장 필요)
        if (!submission.getStudent().getId().equals(studentId)) {
            throw new CommonException(AuthErrorCode.UNAUTHORIZED);
        }

        if (submission.getFilePath() == null || submission.getFilePath().isEmpty()) {
            throw new CommonException(AssignmentSubmissionErrorCode.ASSIGNMENT_FILE_NOT_FOUND);
        }

        return fileStorageService.loadAsResource(submission.getFilePath());
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
        if (submission.getFilePath() != null && !submission.getFilePath().isEmpty()) {
            try {
                fileStorageService.deleteFile(submission.getFilePath());
            } catch (Exception e) {
                log.warn("과제 제출 파일 삭제 중 오류 발생: {}", submission.getFilePath(), e);
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
