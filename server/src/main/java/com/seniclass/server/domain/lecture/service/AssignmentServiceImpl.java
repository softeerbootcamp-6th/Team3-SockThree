package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.lecture.domain.Assignment;
import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.dto.request.AssignmentCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.AssignmentUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.AssignmentResponse;
import com.seniclass.server.domain.lecture.exception.errorcode.LectureErrorCode;
import com.seniclass.server.domain.lecture.repository.AssignmentRepository;
import com.seniclass.server.domain.lecture.repository.LectureRepository;
import com.seniclass.server.domain.user.exception.errorcode.UserErrorCode;
import com.seniclass.server.global.exception.CommonException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final LectureRepository lectureRepository;

    @Override
    public AssignmentResponse createAssignment(Long userId, AssignmentCreateRequest request) {
        Lecture lecture =
                lectureRepository
                        .findById(request.lectureId())
                        .orElseThrow(() -> new CommonException(LectureErrorCode.LECTURE_NOT_FOUND));

        validateTeacherOwnership(userId, lecture);

        Assignment assignment =
                Assignment.createAssignment(
                        request.title(),
                        request.content(),
                        request.fileLink(),
                        request.dueDateTime(),
                        lecture);
        Assignment savedAssignment = assignmentRepository.save(assignment);

        return AssignmentResponse.from(savedAssignment);
    }

    @Override
    @Transactional(readOnly = true)
    public AssignmentResponse getAssignment(Long assignmentId) {
        Assignment assignment = getAssignmentEntity(assignmentId);
        return AssignmentResponse.from(assignment);
    }

    @Override
    public AssignmentResponse updateAssignment(
            Long userId, Long assignmentId, AssignmentUpdateRequest request) {
        Assignment assignment = getAssignmentEntity(assignmentId);
        validateTeacherOwnership(userId, assignment.getLecture());

        assignment.updateAssignment(
                request.title(), request.content(), request.fileLink(), request.dueDateTime());

        return AssignmentResponse.from(assignment);
    }

    @Override
    public void deleteAssignment(Long userId, Long assignmentId) {
        Assignment assignment = getAssignmentEntity(assignmentId);
        validateTeacherOwnership(userId, assignment.getLecture());

        assignmentRepository.deleteById(assignmentId);
    }

    private Assignment getAssignmentEntity(Long assignmentId) {
        return assignmentRepository
                .findById(assignmentId)
                .orElseThrow(() -> new CommonException(LectureErrorCode.ASSIGNMENT_NOT_FOUND));
    }

    private void validateTeacherOwnership(Long userId, Lecture lecture) {
        if (!Objects.equals(lecture.getTeacher().getId(), userId)) {
            throw new CommonException(UserErrorCode.USER_NOT_AUTHORIZED);
        }
    }
}
