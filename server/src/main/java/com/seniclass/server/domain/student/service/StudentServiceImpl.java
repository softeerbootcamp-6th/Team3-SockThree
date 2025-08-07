package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.auth.service.AuthContext;
import com.seniclass.server.domain.student.domain.Student;
import com.seniclass.server.domain.student.dto.StudentInfoResponse;
import com.seniclass.server.domain.student.dto.StudentUpdateRequest;
import com.seniclass.server.domain.student.exception.errorcode.StudentErrorCode;
import com.seniclass.server.domain.student.repository.StudentRepository;
import com.seniclass.server.global.exception.CommonException;
import java.util.List;
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
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public StudentInfoResponse getStudent(Long id) {
        Student student =
                studentRepository
                        .findById(id)
                        .orElseThrow(() -> new CommonException(StudentErrorCode.STUDENT_NOT_FOUND));
        return toResponse(student);
    }

    @Override
    public List<StudentInfoResponse> getStudentList() {
        return studentRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public StudentInfoResponse getCurrentStudentInfo() {
        Long currentUserId = AuthContext.getCurrentUserId();

        Student student =
                studentRepository
                        .findById(currentUserId)
                        .orElseThrow(() -> new CommonException(StudentErrorCode.STUDENT_NOT_FOUND));

        return toResponse(student);
    }

    @Override
    @Transactional
    public StudentInfoResponse updateCurrentStudentInfo(StudentUpdateRequest request) {
        Long currentUserId = AuthContext.getCurrentUserId();

        Student student =
                studentRepository
                        .findById(currentUserId)
                        .orElseThrow(() -> new CommonException(StudentErrorCode.STUDENT_NOT_FOUND));

        student.updateInfo(request);

        Student saved = studentRepository.save(student);
        log.info("Current student info updated: {}", saved.getEmail());
        return toResponse(saved);
    }

    @Override
    public Page<StudentInfoResponse> getAllStudents(Pageable pageable) {
        Page<Student> students = studentRepository.findAll(pageable);
        return students.map(this::toResponse);
    }

    @Override
    public StudentInfoResponse getStudentByEmail(String email) {
        Student student =
                studentRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new CommonException(StudentErrorCode.STUDENT_NOT_FOUND));

        return toResponse(student);
    }

    @Override
    public Page<StudentInfoResponse> searchStudentsByName(String name, Pageable pageable) {
        Page<Student> students = studentRepository.findByNameContainingIgnoreCase(name, pageable);
        return students.map(this::toResponse);
    }

    @Override
    public boolean existsById(Long studentId) {
        return studentRepository.existsById(studentId);
    }

    @Override
    public long getTotalStudentCount() {
        return studentRepository.count();
    }

    private StudentInfoResponse toResponse(Student student) {
        return new StudentInfoResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getAge(),
                student.getGender());
    }
}
