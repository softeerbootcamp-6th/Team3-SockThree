package com.seniclass.server.domain.teacher.repository;

import com.seniclass.server.domain.teacher.domain.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {}
