package com.seniclass.server.domain.lecture.domain;

import com.seniclass.server.domain.category.domain.SubCategory;
import com.seniclass.server.domain.common.model.BaseTimeEntity;
import com.seniclass.server.domain.lecture.enums.Level;
import com.seniclass.server.domain.teacher.domain.Teacher;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory subCategory;

    @Column(nullable = false)
    private String name;

    private Integer cohort;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Integer maxStudent;

    @Column(nullable = false)
    private Integer fee;

    @Column(nullable = false)
    private String instruction;

    @Lob
    @Column(nullable = false)
    private String description;

    private String thumbnailUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Teacher teacher;

    @Builder(access = AccessLevel.PRIVATE)
    private Lecture(
            SubCategory subCategory,
            String name,
            Integer cohort,
            Level level,
            LocalDate startDate,
            LocalDate endDate,
            Integer maxStudent,
            Integer fee,
            String instruction,
            String description,
            String thumbnailUrl,
            Teacher teacher) {
        this.subCategory = subCategory;
        this.name = name;
        this.cohort = cohort;
        this.level = level;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxStudent = maxStudent;
        this.fee = fee;
        this.instruction = instruction;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.teacher = teacher;
    }

    public static Lecture createLecture(
            SubCategory subCategory,
            String name,
            Integer cohort,
            Level level,
            LocalDate startDate,
            LocalDate endDate,
            Integer maxStudent,
            Integer fee,
            String instruction,
            String description,
            String thumbnailUrl,
            Teacher teacher) {
        return Lecture.builder()
                .subCategory(subCategory)
                .name(name)
                .cohort(cohort)
                .level(level)
                .startDate(startDate)
                .endDate(endDate)
                .maxStudent(maxStudent)
                .fee(fee)
                .instruction(instruction)
                .description(description)
                .thumbnailUrl(thumbnailUrl)
                .teacher(teacher)
                .build();
    }

    public void updateLecture(
            SubCategory subCategory,
            Level level,
            LocalDate startDate,
            LocalDate endDate,
            Integer maxStudent,
            String instruction,
            String description,
            String thumbnailUrl) {
        this.subCategory = subCategory;
        this.level = level;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxStudent = maxStudent;
        this.instruction = instruction;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }
}
