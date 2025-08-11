package com.seniclass.server.domain.lecture.repository;

import com.seniclass.server.domain.lecture.domain.WidgetSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WidgetSettingRepository extends JpaRepository<WidgetSetting, Integer> {
    boolean existsByLectureId(Long lectureId);
}
