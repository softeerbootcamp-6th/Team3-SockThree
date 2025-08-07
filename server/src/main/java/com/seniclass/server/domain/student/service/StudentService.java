package com.seniclass.server.domain.student.service;

import com.seniclass.server.domain.student.dto.StudentInfoResponse;
import com.seniclass.server.domain.student.dto.StudentUpdateRequest;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    // 기본 CRUD
    StudentInfoResponse getStudent(Long id);

    List<StudentInfoResponse> getStudentList();

    // 학생 본인 관련
    StudentInfoResponse getCurrentStudentInfo();

    StudentInfoResponse updateCurrentStudentInfo(StudentUpdateRequest request);

    // 관리 기능
    Page<StudentInfoResponse> getAllStudents(Pageable pageable);

    StudentInfoResponse getStudentByEmail(String email);

    Page<StudentInfoResponse> searchStudentsByName(String name, Pageable pageable);

    // 유틸리티
    boolean existsById(Long studentId);

    long getTotalStudentCount();
}
