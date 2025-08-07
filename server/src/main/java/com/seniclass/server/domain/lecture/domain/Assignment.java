package com.seniclass.server.domain.lecture.domain;

import com.seniclass.server.domain.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Assignment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String instruction;

    private String fileLink;

    private LocalDateTime dueDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lecture lecture;

    @Builder(access = AccessLevel.PRIVATE)
    private Assignment(
            String name,
            String instruction,
            String fileLink,
            LocalDateTime dueDateTime,
            Lecture lecture) {
        this.name = name;
        this.instruction = instruction;
        this.fileLink = fileLink;
        this.dueDateTime = dueDateTime;
        this.lecture = lecture;
    }

    public static Assignment create(
            String name,
            String instruction,
            String fileLink,
            LocalDateTime dueDateTime,
            Lecture lecture) {
        return Assignment.builder()
                .name(name)
                .instruction(instruction)
                .fileLink(fileLink)
                .dueDateTime(dueDateTime)
                .lecture(lecture)
                .build();
    }
}
