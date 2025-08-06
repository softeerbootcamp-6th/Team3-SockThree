package com.seniclass.server.domain.teacher.domain;

import com.seniclass.server.domain.auth.enums.UserRole;
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
@Table(name = "teachers")
public class Teacher extends User {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private String instruction;

    @Builder(access = AccessLevel.PRIVATE)
    private Teacher(
            String name,
            String email,
            Integer age,
            Gender gender,
            String password,
            String instruction) {
        super(email, password, UserRole.TEACHER);
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.instruction = instruction;
    }

    public static Teacher createTeacher(
            String name,
            String email,
            Integer age,
            Gender gender,
            String password,
            String instruction) {
        return Teacher.builder()
                .name(name)
                .email(email)
                .age(age)
                .gender(gender)
                .password(password)
                .instruction(instruction)
                .build();
    }
}
