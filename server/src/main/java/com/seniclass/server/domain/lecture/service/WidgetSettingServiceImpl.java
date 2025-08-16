package com.seniclass.server.domain.lecture.service;

import com.seniclass.server.domain.lecture.domain.Lecture;
import com.seniclass.server.domain.lecture.domain.WidgetSetting;
import com.seniclass.server.domain.lecture.dto.WidgetSpec;
import com.seniclass.server.domain.lecture.dto.request.WidgetSettingUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.WidgetSettingResponse;
import com.seniclass.server.domain.lecture.enums.WidgetSize;
import com.seniclass.server.domain.lecture.enums.WidgetType;
import com.seniclass.server.domain.lecture.exception.errorcode.LectureErrorCode;
import com.seniclass.server.domain.lecture.exception.errorcode.WidgetSettingErrorCode;
import com.seniclass.server.domain.lecture.repository.LectureRepository;
import com.seniclass.server.domain.lecture.repository.WidgetSettingRepository;
import com.seniclass.server.domain.user.exception.errorcode.UserErrorCode;
import com.seniclass.server.global.exception.CommonException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WidgetSettingServiceImpl implements WidgetSettingService {

    private static final int MAX_ROWS = 6;
    private static final int MAX_COLS = 4;
    private static final int WIDGETS_COUNT = 6;

    private static final List<WidgetSpec> DEFAULT_SPECS =
            List.of(
                    new WidgetSpec(WidgetType.TEACHER_INFO, 0, 0, WidgetSize.LARGE, true),
                    new WidgetSpec(WidgetType.QNA, 0, 3, WidgetSize.SMALL, true),
                    new WidgetSpec(WidgetType.SUBMISSION, 1, 0, WidgetSize.SMALL, true),
                    new WidgetSpec(WidgetType.NEXT_LECTURE, 1, 1, WidgetSize.LARGE, true),
                    new WidgetSpec(WidgetType.STUDENTS_STATUS, 2, 0, WidgetSize.LARGE, true),
                    new WidgetSpec(WidgetType.REVIEW, 2, 3, WidgetSize.SMALL, true));

    private final WidgetSettingRepository widgetSettingRepository;
    private final LectureRepository lectureRepository;

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
                                                s.widgetSize(),
                                                s.visible(),
                                                lecture))
                        .toList();

        widgetSettingRepository.saveAll(entities);
    }

    @Transactional
    @Override
    public List<WidgetSettingResponse> updateWidgetSettings(
            Long userId, Long lectureId, WidgetSettingUpdateRequest request) {

        // 1) 요청 위젯 type이 6개인지 확인
        if (request.widgetSpecs().stream().map(WidgetSpec::type).collect(Collectors.toSet()).size()
                != WIDGETS_COUNT) {
            throw new CommonException(WidgetSettingErrorCode.WIDGET_ID_MISMATCH);
        }

        // 2) 강의 조회 + 권한 확인
        Lecture lecture =
                lectureRepository
                        .findById(lectureId)
                        .orElseThrow(() -> new CommonException(LectureErrorCode.LECTURE_NOT_FOUND));

        if (!Objects.equals(lecture.getTeacher().getId(), userId)) {
            throw new CommonException(UserErrorCode.USER_NOT_AUTHORIZED);
        }

        // 3) DB에서 현재 세팅 전부 조회 (쿼리 1번)
        List<WidgetSetting> settings = widgetSettingRepository.findAllByLectureId(lectureId);

        // 4) 배치/충돌 검증 (보이는 위젯만 점유로 간주)
        List<WidgetSpec> visibleSpecs =
                request.widgetSpecs().stream().filter(WidgetSpec::visible).toList();
        validateSpecs(visibleSpecs);

        // 5) 루프 내 재조회 제거: Map으로 올려놓고 순서대로 업데이트
        Map<WidgetType, WidgetSetting> dbMap =
                settings.stream().collect(Collectors.toMap(WidgetSetting::getWidgetType, ws -> ws));

        // 6) "클라이언트가 보낸 순서대로" 업데이트
        for (WidgetSpec spec : request.widgetSpecs()) {
            WidgetSetting setting = dbMap.get(spec.type());
            setting.updateWidgetSettingFromSpec(spec);
        }

        return dbMap.values().stream().map(WidgetSettingResponse::from).toList();
    }

    private void validateSpecs(List<WidgetSpec> specs) {
        int[][] grid = new int[MAX_ROWS][MAX_COLS];

        for (WidgetSpec s : specs) {

            if (s.row() < 0
                    || s.col() < 0
                    || s.row() >= MAX_ROWS
                    || s.col() >= MAX_COLS
                    || s.col() + s.widgetSize().getWidth() > MAX_COLS)
                throw new CommonException(WidgetSettingErrorCode.INVALID_POSITION);

            if (s.visible()) {
                for (int i = 0; i < s.widgetSize().getWidth(); i++) {
                    if (grid[s.row()][s.col() + i] == 1) {
                        throw new CommonException(WidgetSettingErrorCode.INVALID_PLACEMENT);
                    }
                    grid[s.row()][s.col() + i] = 1;
                }
            }
        }
    }
}
