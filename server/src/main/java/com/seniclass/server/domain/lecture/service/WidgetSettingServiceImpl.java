package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.domain.WidgetSetting;
import com.seniclass.server.domain.lecture.dto.WidgetSpec;
import com.seniclass.server.domain.lecture.enums.WidgetType;
import com.seniclass.server.domain.lecture.exception.errorcode.WidgetSettingErrorCode;
import com.seniclass.server.domain.lecture.repository.WidgetSettingRepository;
import com.seniclass.server.global.exception.CommonException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WidgetSettingServiceImpl implements WidgetSettingService {

    private static final int MAX_ROWS = 3;
    private static final int MAX_COLS = 4;

    private static final List<WidgetSpec> DEFAULT_SPECS =
            List.of(
                    new WidgetSpec(WidgetType.TEACHER_INFO, 0, 0, 1, 3, true),
                    new WidgetSpec(WidgetType.QNA, 0, 3, 1, 1, true),
                    new WidgetSpec(WidgetType.SUBMISSION, 1, 0, 1, 1, true),
                    new WidgetSpec(WidgetType.NEXT_LECTURE, 1, 1, 1, 3, true),
                    new WidgetSpec(WidgetType.STUDENTS_STATUS, 2, 0, 1, 3, true),
                    new WidgetSpec(WidgetType.REVIEW, 2, 3, 1, 1, true));

    private final WidgetSettingRepository widgetSettingRepository;

    @Transactional
    @Override
    public void createDefaultWidgetSettings(Lecture lecture) {

        if (widgetSettingRepository.existsByLectureId(lecture.getId())) {
            throw new CommonException(WidgetSettingErrorCode.ALREADY_EXIST);
        }

        validateSpecs(DEFAULT_SPECS);

        List<WidgetSetting> entities =
                DEFAULT_SPECS.stream()
                        .map(
                                s ->
                                        WidgetSetting.createWidgetSetting(
                                                s.type(),
                                                s.row(),
                                                s.col(),
                                                s.width(),
                                                s.height(),
                                                true,
                                                lecture))
                        .toList();

        widgetSettingRepository.saveAll(entities);
    }

    private void validateSpecs(List<WidgetSpec> specs) {
        int[][] grid = new int[MAX_ROWS][MAX_COLS];

        for (WidgetSpec s : specs) {

            if (s.width() <= 0 || s.height() != 1)
                throw new CommonException(WidgetSettingErrorCode.INVALID_SIZE);

            if (s.row() < 0
                    || s.col() < 0
                    || s.row() + s.height() > MAX_ROWS
                    || s.col() + s.width() > MAX_COLS)
                throw new CommonException(WidgetSettingErrorCode.INVALID_POSITION);

            if (s.visible()) {
                for (int i = 0; i < s.width(); i++) {
                    if (grid[s.row()][s.col() + i] == 1) {
                        throw new CommonException(WidgetSettingErrorCode.INVALID_PLACEMENT);
                    }
                    grid[s.row()][s.col() + i] = 1;
                }
            }
        }
    }
}
