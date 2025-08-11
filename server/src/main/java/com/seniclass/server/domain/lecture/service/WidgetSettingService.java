package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.dto.request.WidgetSettingUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.WidgetSettingResponse;
import java.util.List;

public interface WidgetSettingService {

    void createDefaultWidgetSettings(Lecture lecture);

    List<WidgetSettingResponse> updateWidgetSettings(
            Long userId, Long lectureId, WidgetSettingUpdateRequest request);
}
