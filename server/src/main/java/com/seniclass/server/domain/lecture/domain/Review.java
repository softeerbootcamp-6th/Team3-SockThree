package com.seniclass.server.domain.lecture.domain;

import com.seniclass.server.domain.common.model.BaseTimeEntity;
import com.seniclass.server.domain.student.domain.Student;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id", "lecture_id"})})
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Lob
    private String content;

    @Column(nullable = false)
    private Double rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Builder(access = AccessLevel.PRIVATE)
    private Review(String content, Double rating, Lecture lecture, Student student) {
        this.content = content;
        this.rating = rating;
        this.lecture = lecture;
        this.student = student;
    }

    public static Review createReview(
            String content, Double rating, Lecture lecture, Student student) {
        return Review.builder()
                .content(content)
                .rating(rating)
                .lecture(lecture)
                .student(student)
                .build();
    }

    public void updateReview(String content, Double rating) {
        this.content = content;
        this.rating = rating;
    }
}
