package com.seniclass.server.domain.lecture.domain;

import com.seniclass.server.domain.common.model.BaseTimeEntity;
import com.seniclass.server.domain.lecture.enums.WidgetSize;
import com.seniclass.server.domain.lecture.enums.WidgetType;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WidgetSetting extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WidgetType widgetType;

    @Column(nullable = false)
    private Integer rowPosition;

    @Column(nullable = false)
    private Integer colPosition;

    @Column(nullable = false)
    private WidgetSize widgetSize;

    @Column(nullable = false)
    private Boolean visible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lecture lecture;

    @Builder(access = AccessLevel.PRIVATE)
    private WidgetSetting(
            WidgetType widgetType,
            Integer rowPosition,
            Integer colPosition,
            WidgetSize widgetSize,
            Boolean visible,
            Lecture lecture) {
        this.widgetType = widgetType;
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
        this.widgetSize = widgetSize;
        this.visible = visible;
        this.lecture = lecture;
    }

    public static WidgetSetting createWidgetSetting(
            WidgetType widgetType,
            Integer rowPosition,
            Integer colPosition,
            WidgetSize widgetSize,
            Boolean visible,
            Lecture lecture) {
        return WidgetSetting.builder()
                .widgetType(widgetType)
                .rowPosition(rowPosition)
                .colPosition(colPosition)
                .widgetSize(widgetSize)
                .visible(visible)
                .lecture(lecture)
                .build();
    }
}
