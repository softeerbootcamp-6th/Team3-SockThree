package com.seniclass.server.domain.student.domain;

import com.seniclass.server.domain.category.domain.SubCategory;
import com.seniclass.server.domain.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestingCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory subCategory;

    @Builder(access = AccessLevel.PRIVATE)
    private InterestingCategory(Student student, SubCategory subCategory) {
        this.student = student;
        this.subCategory = subCategory;
    }

    public static InterestingCategory createInterestingCategory(
            Student student, SubCategory subCategory) {
        return InterestingCategory.builder().student(student).subCategory(subCategory).build();
    }
}
