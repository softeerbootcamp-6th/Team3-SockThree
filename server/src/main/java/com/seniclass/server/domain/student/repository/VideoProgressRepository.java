package com.seniclass.server.domain.student.repository;

import com.seniclass.server.domain.student.domain.VideoProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoProgressRepository extends JpaRepository<VideoProgress, Long> {}
