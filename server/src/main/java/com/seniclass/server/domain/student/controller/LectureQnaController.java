package com.seniclass.server.domain.student.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.student.dto.LectureQnaAnswerRequest;
import com.seniclass.server.domain.student.dto.LectureQnaRequest;
import com.seniclass.server.domain.student.dto.LectureQnaResponse;
import com.seniclass.server.domain.student.service.LectureQnaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Student Lecture QnA", description = "학생 강의 QnA 관리 API")
@RestController
@RequestMapping("/students/qna")
@RequiredArgsConstructor
public class LectureQnaController {

    private final LectureQnaService lectureQnaService;

    @Operation(summary = "강의 질문 등록", description = "학생이 강의에 대한 질문을 등록합니다.")
    @PostMapping
    @RequireAuth(roles = {UserRole.STUDENT})
    @ResponseStatus(HttpStatus.CREATED)
    public LectureQnaResponse createQuestion(@Valid @RequestBody LectureQnaRequest request) {
        return lectureQnaService.createQuestion(request);
    }

    @Operation(summary = "강의 질문 수정", description = "학생이 등록한 질문을 수정합니다.")
    @PutMapping("/{qnaId}")
    @RequireAuth(roles = {UserRole.STUDENT})
    public LectureQnaResponse updateQuestion(
            @Parameter(description = "QnA ID") @PathVariable Long qnaId,
            @Valid @RequestBody LectureQnaRequest request) {
        return lectureQnaService.updateQuestion(qnaId, request);
    }

    @Operation(summary = "강의 질문 삭제", description = "학생이 등록한 질문을 삭제합니다.")
    @DeleteMapping("/{qnaId}")
    @RequireAuth(roles = {UserRole.STUDENT})
    public void deleteQuestion(@Parameter(description = "QnA ID") @PathVariable Long qnaId) {
        lectureQnaService.deleteQuestion(qnaId);
    }

    @Operation(summary = "강의 질문 답변", description = "강사가 학생의 질문에 답변합니다.")
    @PutMapping("/{qnaId}/answer")
    @RequireAuth(roles = {UserRole.TEACHER})
    public LectureQnaResponse answerQuestion(
            @Parameter(description = "QnA ID") @PathVariable Long qnaId,
            @Valid @RequestBody LectureQnaAnswerRequest request) {
        return lectureQnaService.answerQuestion(qnaId, request);
    }

    @Operation(summary = "내 질문 목록 조회", description = "현재 학생의 질문 목록을 조회합니다.")
    @GetMapping("/my")
    @RequireAuth(roles = {UserRole.STUDENT})
    public Page<LectureQnaResponse> getCurrentStudentQuestions(
            @PageableDefault(size = 20, sort = "createdDt", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return lectureQnaService.getCurrentStudentQuestions(pageable);
    }

    @Operation(summary = "강의별 QnA 목록 조회", description = "특정 강의의 QnA 목록을 조회합니다.")
    @GetMapping("/lectures/{lectureId}")
    @RequireAuth(roles = {UserRole.STUDENT, UserRole.TEACHER})
    public Page<LectureQnaResponse> getQuestionsByLecture(
            @Parameter(description = "강의 ID") @PathVariable Long lectureId,
            @PageableDefault(size = 20, sort = "createdDt", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return lectureQnaService.getQuestionsByLecture(lectureId, pageable);
    }

    @Operation(summary = "QnA 상세 조회", description = "특정 QnA의 상세 정보를 조회합니다.")
    @GetMapping("/{qnaId}")
    @RequireAuth(roles = {UserRole.STUDENT, UserRole.TEACHER})
    public LectureQnaResponse getQuestion(
            @Parameter(description = "QnA ID") @PathVariable Long qnaId) {
        return lectureQnaService.getQuestion(qnaId);
    }

    @Operation(summary = "내 질문 수 조회", description = "현재 학생의 총 질문 수를 조회합니다.")
    @GetMapping("/my/count")
    @RequireAuth(roles = {UserRole.STUDENT})
    public Map<String, Long> getCurrentStudentQuestionCount() {
        long count = lectureQnaService.getCurrentStudentQuestionCount();
        return Map.of("count", count);
    }
}
