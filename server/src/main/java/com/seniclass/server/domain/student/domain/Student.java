package com.seniclass.server.domain.student.domain;

import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.student.dto.request.StudentUpdateRequest;
import com.seniclass.server.domain.student.enums.Gender;
import com.seniclass.server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "students")
public class Student extends User {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private String imageKey;

    @Builder(access = AccessLevel.PRIVATE)
    private Student(String name, String email, Integer age, Gender gender, String password, String imageKey) {
        super(email, password, UserRole.STUDENT);
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.imageKey = imageKey;
    }

    public static Student createStudent(
            String name, String email, Integer age, Gender gender, String password, String imageKey) {
        return Student.builder()
                .name(name)
                .email(email)
                .age(age)
                .gender(gender)
                .password(password)
                .imageKey(imageKey)
                .build();
    }

    public void updateInfo(StudentUpdateRequest request) {
        if (request.name() != null) {
            this.name = request.name();
        }
        if (request.age() != null) {
            this.age = request.age();
        }
        if (request.gender() != null) {
            this.gender = request.gender();
        }
    }
}
