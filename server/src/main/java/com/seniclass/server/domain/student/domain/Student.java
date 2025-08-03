package com.seniclass.server.domain.student.domain;

import com.seniclass.server.domain.common.model.BaseTimeEntity;
import com.seniclass.server.domain.student.enums.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Student extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "student_id", nullable = false)
  private Long id;

  @Column(name = "student_name", nullable = false)
  private String name;

  @Column(name = "student_email", nullable = false, unique = true)
  private String email;

  @Column(name = "student_age", nullable = false)
  private Integer age;

  @Column(name = "student_gender", nullable = false)
  private Gender gender;

  @Column(name = "student_password", nullable = false)
  private String password;

  @Builder(access = AccessLevel.PRIVATE)
  private Student(String name, String email, Integer age, Gender gender, String password) {
    this.name = name;
    this.email = email;
    this.age = age;
    this.gender = gender;
    this.password = password;
    this.name = name;
  }

  public static Student createStudent(
      String name, String email, Integer age, Gender gender, String password) {
    return Student.builder()
        .name(name)
        .email(email)
        .age(age)
        .gender(gender)
        .password(password)
        .build();
  }
}
