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
    private Long id;

    @Column private String filePath;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @Builder(access = AccessLevel.PRIVATE)
    private AssignmentSubmission(
            Student student, Assignment assignment, String filePath, String content) {
        this.student = student;
        this.assignment = assignment;
        this.filePath = filePath;
        this.content = content;
    }

    public static AssignmentSubmission createAssignmentSubmissionWithFile(
            Student student, Assignment assignment, String content, String filePath) {
        return AssignmentSubmission.builder()
                .student(student)
                .assignment(assignment)
                .content(content)
                .filePath(filePath)
                .build();
    }

    public void updateFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
