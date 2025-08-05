package com.seniclass.server.domain.student.domain;

import com.seniclass.server.domain.common.model.BaseTimeEntity;
import com.seniclass.server.domain.lecture.domain.Lecture;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureEnrollment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_enrollment_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Builder(access = AccessLevel.PRIVATE)
    public LectureEnrollment(Student student, Lecture lecture) {
        this.student = student;
        this.lecture = lecture;
    }

    public static LectureEnrollment createLectureEnrollment(Student student, Lecture lecture) {
        return LectureEnrollment.builder().student(student).lecture(lecture).build();
    }
}
