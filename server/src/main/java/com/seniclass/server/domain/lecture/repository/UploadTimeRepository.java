package com.seniclass.server.domain.lecture.repository;

import com.seniclass.server.domain.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadTimeRepository extends JpaRepository<Student, Long> {}
