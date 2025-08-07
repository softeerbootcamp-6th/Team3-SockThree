package com.seniclass.server.domain.student.domain;

import com.seniclass.server.domain.common.model.BaseTimeEntity;
import com.seniclass.server.domain.lecture.domain.Assignment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssignmentSubmission extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_submission_id", nullable = false)
    private Long id;

    @Column(name = "assignment_submission_link", nullable = false)
    private String submissionLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @Builder(access = AccessLevel.PRIVATE)
    private AssignmentSubmission(Student student, Assignment assignment, String submissionLink) {
        this.student = student;
        this.assignment = assignment;
        this.submissionLink = submissionLink;
    }

    public static AssignmentSubmission createAssignmentSubmission(
            Student student, Assignment assignment, String submissionLink) {
        return AssignmentSubmission.builder()
                .student(student)
                .assignment(assignment)
                .submissionLink(submissionLink)
                .build();
    }

    public void updateSubmissionLink(String submissionLink) {
        this.submissionLink = submissionLink;
    }
}
