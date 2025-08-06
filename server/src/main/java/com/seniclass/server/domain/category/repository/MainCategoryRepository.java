package com.seniclass.server.domain.category.repository;

import com.seniclass.server.domain.category.domain.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {}
