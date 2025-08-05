package com.seniclass.server.domain.student.domain;

import com.seniclass.server.domain.common.model.BaseTimeEntity;
import com.seniclass.server.domain.lecture.domain.Lecture;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureQna extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_question_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lecture lecture;

    @Lob
    @Column(name = "lecture_qna_question", nullable = false)
    private String question;

    @Lob
    @Column(name = "lecture_qna_answer")
    private String answer;

    @Builder(access = AccessLevel.PRIVATE)
    public LectureQna(Student student, Lecture lecture, String question, String answer) {
        this.student = student;
        this.lecture = lecture;
        this.question = question;
        this.answer = answer;
    }

    public static LectureQna createLectureQna(Student student, Lecture lecture, String question) {
        return LectureQna.builder().student(student).lecture(lecture).question(question).build();
    }

    public void updateAnswer(String answer) {
        this.answer = answer;
    }
}
