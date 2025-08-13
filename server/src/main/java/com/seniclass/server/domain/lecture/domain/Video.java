package com.seniclass.server.domain.lecture.domain;

import com.seniclass.server.domain.common.model.BaseTimeEntity;
import com.seniclass.server.global.enums.PATH;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long duration;

    @Column(nullable = false)
    private String uploadPath;

    private String streamingPath;

    @Column(nullable = false)
    private LocalDateTime publicationDateTime;

    @Column(nullable = false)
    private Boolean streamable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Chapter chapter;

    @Builder(access = AccessLevel.PRIVATE)
    private Video(
            String name,
            Long duration,
            String uploadPath,
            String streamingPath,
            LocalDateTime publicationDateTime,
            boolean streamable,
            Chapter chapter) {
        this.name = name;
        this.duration = duration;
        this.uploadPath = uploadPath;
        this.streamingPath = streamingPath;
        this.publicationDateTime = publicationDateTime;
        this.streamable = streamable;
        this.chapter = chapter;
    }

    public static Video createVideo(
            String name, Long duration,String uploadPath, LocalDateTime publicationDateTime, Chapter chapter) {
        return Video.builder()
                .name(name)
                .duration(duration)
                .uploadPath(uploadPath)
                .publicationDateTime(publicationDateTime)
                .streamable(false)
                .chapter(chapter)
                .build();
    }

    public void finalizeVideoUpload(String streamingPath) {
        this.streamingPath = PATH.CDN_STREAMING_PATH.getPath() + streamingPath;
        streamable = true;
    }
}
