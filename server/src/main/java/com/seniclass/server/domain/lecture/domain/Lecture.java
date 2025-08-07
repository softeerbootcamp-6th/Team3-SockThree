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
    @Column(name = "lecture_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory subCategory;

    @Column(name = "lecture_name", nullable = false)
    private String name;

    @Column(name = "lecture_cohort")
    private Integer cohort;

    @Column(name = "lecture_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(name = "lecture_start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "lecture_end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "lecture_max_student", nullable = false)
    private Integer maxStudent;

    @Column(name = "lecture_fee", nullable = false)
    private Integer fee;

    @Column(name = "lecture_instruction", nullable = false)
    private String instruction;

    @Lob
    @Column(name = "lecture_description", nullable = false)
    private String description;

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
            String description) {
        this.subCategory = subCategory;
        this.level = level;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxStudent = maxStudent;
        this.instruction = instruction;
        this.description = description;
    }
}
