package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.lecture.dto.request.AssignmentCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.AssignmentUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.AssignmentResponse;

public interface AssignmentService {
    AssignmentResponse createAssignment(Long userId, AssignmentCreateRequest request);
    AssignmentResponse getAssignment(Long assignmentId);
    AssignmentResponse updateAssignment(Long userId, Long assignmentId, AssignmentUpdateRequest request);
    void deleteAssignment(Long userId, Long assignmentId);
}