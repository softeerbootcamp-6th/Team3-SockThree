package com.seniclass.server.domain.lecture.repository;

import com.seniclass.server.domain.lecture.domain.WidgetSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WidgetSettingRepository extends JpaRepository<WidgetSetting, Long> {
    boolean existsByLectureId(Long lectureId);
}
