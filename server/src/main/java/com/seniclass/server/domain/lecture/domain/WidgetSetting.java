package com.seniclass.server.domain.lecture.domain;

import com.seniclass.server.domain.common.model.BaseTimeEntity;
import com.seniclass.server.domain.lecture.enums.Level;
import com.seniclass.server.domain.lecture.enums.WidgetType;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WidgetSetting extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "widget_setting_id", nullable = false)
    private Long id;

    @Column(name = "widget_setting_type", nullable = false)
    private WidgetType widgetType;

    @Column(name = "widget_setting_row", nullable = false)
    private Integer row;

    @Column(name = "widget_setting_column", nullable = false)
    private Integer column;

    @Column(name = "widget_setting_width", nullable = false)
    private Integer width;

    @Column(name = "widget_setting_height", nullable = false)
    private Integer height;

    @Column(name = "widget_setting_visible", nullable = false)
    private Boolean visible;

    @Builder(access = AccessLevel.PRIVATE)
    public WidgetSetting(WidgetType widgetType, Integer row, Integer column, Integer width, Integer height, Boolean visible) {
        this.widgetType = widgetType;
        this.row = row;
        this.column = column;
        this.width = width;
        this.height = height;
        this.visible = visible;
    }

    public static WidgetSetting createWidgetSetting(WidgetType widgetType, Integer row, Integer column, Integer width, Integer height, Boolean visible) {
        return WidgetSetting.builder()
                .widgetType(widgetType)
                .row(row)
                .column(column)
                .width(width)
                .height(height)
                .visible(visible)
                .build();
    }
}

