package com.seniclass.server.domain.lecture.domain;

import com.seniclass.server.domain.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadTime extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    private LocalTime scheduledAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lecture lecture;

    @Builder(access = AccessLevel.PRIVATE)
    private UploadTime(DayOfWeek dayOfWeek, LocalTime scheduledAt, Lecture lecture) {
        this.dayOfWeek = dayOfWeek;
        this.scheduledAt = scheduledAt;
        this.lecture = lecture;
    }

    public static UploadTime create(DayOfWeek dayOfWeek, LocalTime scheduledAt, Lecture lecture) {
        return UploadTime.builder()
                .dayOfWeek(dayOfWeek)
                .scheduledAt(scheduledAt)
                .lecture(lecture)
                .build();
    }
}
