package com.seniclass.server.domain.lecture.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.lecture.dto.request.WidgetSettingUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.WidgetSettingResponse;
import com.seniclass.server.domain.lecture.service.WidgetSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "WidgetSetting", description = "위젯 설정 관리 API")
@RestController
@RequestMapping("/lectures/{lectureId}/widget-settings")
@RequiredArgsConstructor
public class WidgetSettingController {

    private final WidgetSettingService widgetSettingService;

    @Operation(summary = "위젯 설정 수정", description = "강사가 본인의 강의 위젯 설정을 수정합니다. TEACHER 권한 필요.")
    @PutMapping
    @RequireAuth(roles = {UserRole.TEACHER})
    public List<WidgetSettingResponse> updateWidgetSettings(
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId,
            @Parameter(description = "수정할 강의 ID") @PathVariable Long lectureId,
            @Parameter(description = "수정할 위젯 설정 정보") @Valid @RequestBody WidgetSettingUpdateRequest request) {
        return widgetSettingService.updateWidgetSettings(userId, lectureId, request);
    }
}
