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
  @Column(name = "upload_time_id", nullable = false)
  private Long id;

  @Column(name = "upload_time_day_of_week", nullable = false)
  private DayOfWeek dayOfWeek;

  @Column(name = "upload_time_scheduled_at")
  private LocalTime scheduledAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lecture_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Lecture lecture;

  @Builder(access = AccessLevel.PRIVATE)
  public UploadTime(DayOfWeek dayOfWeek, LocalTime scheduledAt) {
    this.dayOfWeek = dayOfWeek;
    this.scheduledAt = scheduledAt;
  }

  public static UploadTime createUploadTime(DayOfWeek dayOfWeek, LocalTime scheduledAt) {
    return UploadTime.builder().dayOfWeek(dayOfWeek).scheduledAt(scheduledAt).build();
  }
}
