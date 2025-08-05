package com.seniclass.server.domain.student.repository;

import com.seniclass.server.domain.student.domain.LectureEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureEnrollmentRepository extends JpaRepository<LectureEnrollment, Long> {}
