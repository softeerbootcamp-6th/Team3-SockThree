package com.seniclass.server.domain.lecture.repository;

import com.seniclass.server.domain.lecture.domain.UploadTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadTimeRepository extends JpaRepository<UploadTime, Long> {}
