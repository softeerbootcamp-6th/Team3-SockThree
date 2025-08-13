package com.seniclass.server.domain.student.dto;

/** 과제 제출률 통계 조회 결과 */
public record AssignmentSubmissionStatsDto(
        Long assignmentId,
        String assignmentName,
        Long totalStudentCount,
        Long submittedStudentCount) {}
