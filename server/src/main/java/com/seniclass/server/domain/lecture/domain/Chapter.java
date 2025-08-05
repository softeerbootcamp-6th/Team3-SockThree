package com.seniclass.server.domain.lecture.domain;

import com.seniclass.server.domain.common.model.BaseTimeEntity;
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
public class Chapter extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id", nullable = false)
    private Long id;

    @Column(name = "chapter_name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lecture lecture;

    @Builder(access = AccessLevel.PRIVATE)
    public Chapter(String name, Lecture lecture) {
        this.name = name;
        this.lecture = lecture;
    }

    public static Chapter createChapter(String name, Lecture lecture) {
        return Chapter.builder().name(name).lecture(lecture).build();
    }
}
