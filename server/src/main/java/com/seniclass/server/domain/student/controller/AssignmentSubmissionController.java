package com.seniclass.server.domain.student.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.student.dto.AssignmentSubmissionFileRequest;
import com.seniclass.server.domain.student.dto.AssignmentSubmissionResponse;
import com.seniclass.server.domain.student.service.AssignmentSubmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Student Assignment Submission", description = "학생 과제 제출 관리 API")
@RestController
@RequestMapping("/students/assignments")
@RequiredArgsConstructor
public class AssignmentSubmissionController {

    private final AssignmentSubmissionService assignmentSubmissionService;

    @Operation(summary = "파일로 과제 제출", description = "학생이 파일을 업로드하여 과제를 제출합니다.")
    @PostMapping("/submissions")
    @RequireAuth(roles = {UserRole.STUDENT})
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentSubmissionResponse createSubmission(
            @Parameter(description = "과제 ID") @RequestParam Long assignmentId,
            @Parameter(description = "과제 설명") @RequestParam(required = false) String content,
            @Parameter(description = "업로드할 파일") @RequestParam("file") MultipartFile file) {
        AssignmentSubmissionFileRequest request =
                new AssignmentSubmissionFileRequest(assignmentId, content, file);
        return assignmentSubmissionService.createSubmission(request);
    }

    @Operation(summary = "파일로 과제 제출 수정", description = "학생이 제출한 과제를 파일로 수정합니다.")
    @PutMapping("/submissions/{submissionId}")
    @RequireAuth(roles = {UserRole.STUDENT})
    public AssignmentSubmissionResponse updateSubmission(
            @Parameter(description = "제출 ID") @PathVariable Long submissionId,
            @Parameter(description = "과제 설명") @RequestParam(required = false) String content,
            @Parameter(description = "업로드할 파일") @RequestParam("file") MultipartFile file) {
        AssignmentSubmissionFileRequest request =
                new AssignmentSubmissionFileRequest(null, content, file);
        return assignmentSubmissionService.updateSubmission(submissionId, request);
    }

    @Operation(summary = "과제 제출 삭제", description = "학생이 제출한 과제를 삭제합니다.")
    @DeleteMapping("/submissions/{submissionId}")
    @RequireAuth(roles = {UserRole.STUDENT})
    public void deleteSubmission(
            @Parameter(description = "제출 ID") @PathVariable Long submissionId) {
        assignmentSubmissionService.deleteSubmission(submissionId);
    }

    @Operation(summary = "내 과제 제출 목록 조회", description = "현재 학생의 과제 제출 목록을 조회합니다.")
    @GetMapping("/submissions")
    @RequireAuth(roles = {UserRole.STUDENT})
    public Page<AssignmentSubmissionResponse> getCurrentStudentSubmissions(
            @PageableDefault(size = 20, sort = "createdDt", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return assignmentSubmissionService.getCurrentStudentSubmissions(pageable);
    }

    @Operation(summary = "특정 과제 제출 조회", description = "특정 과제에 대한 현재 학생의 제출을 조회합니다.")
    @GetMapping("/{assignmentId}/submissions/my")
    @RequireAuth(roles = {UserRole.STUDENT})
    public AssignmentSubmissionResponse getMySubmissionByAssignment(
            @Parameter(description = "과제 ID") @PathVariable Long assignmentId) {
        return assignmentSubmissionService.getStudentSubmissionByAssignment(assignmentId);
    }

    @Operation(summary = "특정 과제의 모든 제출 조회", description = "특정 과제의 모든 제출을 조회합니다. (강사용)")
    @GetMapping("/{assignmentId}/submissions")
    @RequireAuth(roles = {UserRole.TEACHER})
    public Page<AssignmentSubmissionResponse> getSubmissionsByAssignment(
            @Parameter(description = "과제 ID") @PathVariable Long assignmentId,
            @PageableDefault(size = 20, sort = "createdDt", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return assignmentSubmissionService.getSubmissionsByAssignment(assignmentId, pageable);
    }

    @Operation(summary = "과제 제출 여부 확인", description = "특정 과제에 대한 제출 여부를 확인합니다.")
    @GetMapping("/{assignmentId}/submissions/check")
    @RequireAuth(roles = {UserRole.STUDENT})
    public Map<String, Boolean> checkSubmissionStatus(
            @Parameter(description = "과제 ID") @PathVariable Long assignmentId) {
        boolean isSubmitted = assignmentSubmissionService.isSubmitted(assignmentId);
        return Map.of("isSubmitted", isSubmitted);
    }

    @Operation(summary = "내 과제 제출 수 조회", description = "현재 학생의 총 과제 제출 수를 조회합니다.")
    @GetMapping("/submissions/count")
    @RequireAuth(roles = {UserRole.STUDENT})
    public Map<String, Long> getCurrentStudentSubmissionCount() {
        long count = assignmentSubmissionService.getCurrentStudentSubmissionCount();
        return Map.of("count", count);
    }

    @Operation(summary = "제출 파일 다운로드", description = "제출된 과제 파일을 다운로드합니다.")
    @GetMapping("/submissions/{submissionId}/download")
    @RequireAuth(roles = {UserRole.STUDENT, UserRole.TEACHER})
    public ResponseEntity<Resource> downloadSubmissionFile(
            @Parameter(description = "제출 ID") @PathVariable Long submissionId) {
        Resource file = assignmentSubmissionService.downloadSubmissionFile(submissionId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
