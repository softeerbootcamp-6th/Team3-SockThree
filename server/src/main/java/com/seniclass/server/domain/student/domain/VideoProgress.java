package com.seniclass.server.domain.student.domain;

import com.seniclass.server.domain.common.model.BaseTimeEntity;
import com.seniclass.server.domain.lecture.domain.Video;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoProgress extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_progress_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vedio_id", nullable = false)
    private Video video;

    @Column(name = "video_progress_current_time", nullable = false)
    private Long currentTime;

    @Builder(access = AccessLevel.PRIVATE)
    private VideoProgress(Student student, Video video, Long currentTime) {
        this.student = student;
        this.video = video;
        this.currentTime = currentTime;
    }

    public static VideoProgress createVideoProgress(
            Student student, Video video, Long currentTime) {
        return VideoProgress.builder()
                .student(student)
                .video(video)
                .currentTime(currentTime)
                .build();
    }

    public void updateCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }
}
