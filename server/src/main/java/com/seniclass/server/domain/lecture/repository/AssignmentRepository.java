package com.seniclass.server.domain.lecture.repository;

import com.seniclass.server.domain.lecture.domain.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {}
