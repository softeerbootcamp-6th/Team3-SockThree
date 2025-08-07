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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Column(nullable = false)
    private Long playedTime;

    @Builder(access = AccessLevel.PRIVATE)
    private VideoProgress(Student student, Video video, Long playedTime) {
        this.student = student;
        this.video = video;
        this.playedTime = playedTime;
    }

    public static VideoProgress createVideoProgress(Student student, Video video, Long playedTime) {
        return VideoProgress.builder().student(student).video(video).playedTime(playedTime).build();
    }

    public void updatePlayedTime(Long playedTime) {
        this.playedTime = playedTime;
    }
}
