package com.seniclass.server.domain.student.repository;

import com.seniclass.server.domain.student.domain.InterestingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestingCategoryRepository
        extends JpaRepository<InterestingCategory, Integer> {}
