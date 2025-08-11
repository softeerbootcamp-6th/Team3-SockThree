package com.seniclass.server.domain.student.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.seniclass.server.domain.auth.service.AuthContext;
import com.seniclass.server.domain.lecture.domain.Assignment;
import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.repository.AssignmentRepository;
import com.seniclass.server.domain.student.domain.AssignmentSubmission;
import com.seniclass.server.domain.student.domain.Student;
import com.seniclass.server.domain.student.dto.AssignmentSubmissionFileRequest;
import com.seniclass.server.domain.student.dto.AssignmentSubmissionResponse;
import com.seniclass.server.domain.student.repository.AssignmentSubmissionRepository;
import com.seniclass.server.domain.student.repository.StudentRepository;
import com.seniclass.server.global.exception.CommonException;
import com.seniclass.server.global.service.FileStorageService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class AssignmentSubmissionServiceTest {

    // (이전과 동일)
    @InjectMocks private AssignmentSubmissionServiceImpl assignmentSubmissionService;
    @Mock private AssignmentSubmissionRepository assignmentSubmissionRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private AssignmentRepository assignmentRepository;
    @Mock private FileStorageService fileStorageService;
    @Mock private MultipartFile mockFile;
    @Mock private Student student;
    @Mock private Assignment assignment;
    @Mock private Lecture lecture;
    private MockedStatic<AuthContext> authContext;

    @BeforeEach
    void setUp() {
        authContext = mockStatic(AuthContext.class);
    }

    @AfterEach
    void tearDown() {
        authContext.close();
    }

    @Test
    @DisplayName("과제 제출 성공 - 파일 업로드")
    void createSubmission_Success() {
        // given
        // 테스트용 가상 데이터
        Long studentId = 1L;
        String studentName = "김학생";
        Long assignmentId = 2L;
        String assignmentName = "Java 기초 과제";
        Long lectureId = 3L;
        String lectureName = "Java 프로그래밍 기초";
        Long submissionId = 50L;
        String content = "제출합니다.";
        String fileName = "assignment.pdf";
        LocalDateTime now = LocalDateTime.now();

        authContext.when(AuthContext::getCurrentUserId).thenReturn(studentId);
        AssignmentSubmissionFileRequest request =
                new AssignmentSubmissionFileRequest(assignmentId, content, mockFile);
        AssignmentSubmission newSubmission = mock(AssignmentSubmission.class);

        // 서비스 로직에 필요한 Mock 설정
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        when(assignment.getDueDateTime()).thenReturn(now.plusDays(1));
        when(assignmentSubmissionRepository.existsByStudentIdAndAssignmentId(
                        studentId, assignmentId))
                .thenReturn(false);
        when(fileStorageService.storeFile(any(MultipartFile.class), anyString()))
                .thenReturn(fileName);
        when(assignmentSubmissionRepository.save(any(AssignmentSubmission.class)))
                .thenReturn(newSubmission);

        // --- DTO 변환에 필요한 Mock 설정 (Checklist 기반) ---
        when(newSubmission.getId()).thenReturn(submissionId);
        when(newSubmission.getStudent()).thenReturn(student);
        when(student.getId()).thenReturn(studentId);
        when(student.getName()).thenReturn(studentName); // 추가
        when(newSubmission.getAssignment()).thenReturn(assignment);
        when(assignment.getId()).thenReturn(assignmentId); // 추가
        when(assignment.getName()).thenReturn(assignmentName); // 추가
        when(assignment.getLecture()).thenReturn(lecture);
        when(lecture.getId()).thenReturn(lectureId);
        when(lecture.getName()).thenReturn(lectureName); // 추가
        when(newSubmission.getFilePath()).thenReturn(fileName);
        when(newSubmission.getContent()).thenReturn(content);
        when(newSubmission.getCreatedDt()).thenReturn(now); // 추가

        // when
        AssignmentSubmissionResponse response =
                assignmentSubmissionService.createSubmission(request);

        // then
        assertNotNull(response);
        assertEquals(submissionId, response.id());
        assertEquals(studentName, response.studentName());
        assertEquals(lectureName, response.lectureName());
        assertEquals(content, response.content());
    }

    // (DeadlinePassed 테스트는 이전과 동일)
    @Test
    @DisplayName("과제 제출 실패 - 마감일 지남")
    void createSubmission_DeadlinePassed() {
        // given
        Long studentId = 1L;
        Long assignmentId = 1L;
        String content = "This is my assignment.";

        authContext.when(AuthContext::getCurrentUserId).thenReturn(studentId);
        AssignmentSubmissionFileRequest request =
                new AssignmentSubmissionFileRequest(assignmentId, content, mockFile);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        // 사용자가 수정한 메서드명 반영
        when(assignment.getDueDateTime()).thenReturn(LocalDateTime.now().minusDays(1));
        when(assignmentSubmissionRepository.existsByStudentIdAndAssignmentId(
                        studentId, assignmentId))
                .thenReturn(false);

        // when & then
        assertThrows(
                CommonException.class, () -> assignmentSubmissionService.createSubmission(request));
    }

    @Test
    @DisplayName("과제 수정 성공 - 파일 업로드")
    void updateSubmission_Success() {
        // given: 테스트에 필요한 모든 가상 데이터를 명확하게 정의합니다.
        Long studentId = 1L;
        String studentName = "김학생";
        Long assignmentId = 2L;
        String assignmentName = "Java 기초 과제";
        Long lectureId = 3L;
        String lectureName = "Java 프로그래밍 기초";
        Long submissionId = 1L;
        String oldFileName = "old.pdf";
        String newContent = "수정된 과제 내용입니다.";
        String newFileName = "new.pdf";
        LocalDateTime now = LocalDateTime.now();

        // 현재 로그인한 사용자(학생) ID를 설정합니다.
        authContext.when(AuthContext::getCurrentUserId).thenReturn(studentId);

        // 서비스 메서드에 전달될 요청 DTO를 생성합니다.
        AssignmentSubmissionFileRequest request =
                new AssignmentSubmissionFileRequest(assignmentId, newContent, mockFile);

        // 테스트의 핵심인 `AssignmentSubmission` Mock 객체를 생성합니다.
        AssignmentSubmission submission = mock(AssignmentSubmission.class);

        // --- 서비스 로직 실행 및 DTO 변환에 필요한 모든 Mock 동작을 설정합니다. ---

        // 1. 서비스가 DB에서 submissionId로 기존 제출물을 찾을 때 `submission` Mock 객체를 반환하도록 설정합니다.
        when(assignmentSubmissionRepository.findById(submissionId))
                .thenReturn(Optional.of(submission));

        // 2. 권한 확인 및 DTO 변환에 필요한 `student` 관련 정보를 설정합니다.
        when(submission.getStudent()).thenReturn(student);
        when(student.getId()).thenReturn(studentId);
        when(student.getName()).thenReturn(studentName);

        // 3. 마감일 확인 및 DTO 변환에 필요한 `assignment`와 `lecture` 관련 정보를 설정합니다.
        when(submission.getAssignment()).thenReturn(assignment);
        when(assignment.getId()).thenReturn(assignmentId);
        when(assignment.getName()).thenReturn(assignmentName);
        when(assignment.getLecture()).thenReturn(lecture);
        when(lecture.getId()).thenReturn(lectureId);
        when(lecture.getName()).thenReturn(lectureName);

        // 4. 새 파일 저장 및 기존 파일 삭제 로직에 필요한 정보를 설정합니다.
        when(fileStorageService.storeFile(any(MultipartFile.class), anyString()))
                .thenReturn(newFileName);

        // 5. getFilePath()의 순차적 반환 값을 설정합니다.
        when(submission.getFilePath())
                .thenReturn(oldFileName) // 첫 번째 호출: 서비스 로직에서 기존 파일 삭제를 위해 사용
                .thenReturn(newFileName); // 두 번째 호출: 최종 DTO 변환 시 사용

        // 6. DTO 변환에 필요한 나머지 정보들을 설정합니다.
        when(submission.getId()).thenReturn(submissionId);
        when(submission.getContent()).thenReturn(newContent);
        when(submission.getCreatedDt()).thenReturn(now);

        // 7. repository.save()가 호출되면 `submission` Mock 객체 자신을 반환하도록 설정합니다.
        when(assignmentSubmissionRepository.save(submission)).thenReturn(submission);

        // when: 실제 테스트 대상인 서비스 메서드를 호출합니다.
        AssignmentSubmissionResponse response =
                assignmentSubmissionService.updateSubmission(submissionId, request);

        // then: 결과가 예상과 일치하는지 검증합니다.
        // 1. 반환된 DTO가 null이 아닌지, 내용은 올바른지 확인합니다.
        assertNotNull(response);
        assertEquals(newContent, response.content());
        assertEquals(newFileName, response.filePath());
        assertEquals(studentName, response.studentName());

        // 2. Mock 객체들의 특정 메서드가 정확한 인자와 함께 호출되었는지 확인합니다.
        verify(submission, times(1)).updateContent(newContent);
        verify(submission, times(1)).updateFilePath(newFileName);
        verify(fileStorageService, times(1)).storeFile(request.file(), "assignments");
        verify(fileStorageService, times(1)).deleteFile(oldFileName); // "old.pdf"가 삭제되는지 검증
        verify(assignmentSubmissionRepository, times(1)).save(submission);
    }
}
