package com.seniclass.server.domain.lecture.domain;

import com.seniclass.server.domain.common.model.BaseTimeEntity;
import com.seniclass.server.global.enums.PATH;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id", nullable = false)
    private Long id;

    @Column(name = "video_name", nullable = false)
    private String name;

    @Column(name = "video_upload_path", nullable = false)
    private String uploadPath;

    @Column(name = "video_streaming_path", nullable = false)
    private String streamingPath;

    @Column(name = "video_publication_date_time", nullable = false)
    private LocalDateTime publicationDateTime;

    @Column(name = "video_streamable", nullable = false)
    private Boolean isStreamable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Chapter chapter;

    @Builder(access = AccessLevel.PRIVATE)
    private Video(String name, String uploadPath, String streamingPath, LocalDateTime publicationDateTime, Boolean isStreamable, Chapter chapter) {
        this.name = name;
        this.uploadPath = uploadPath;
        this.streamingPath = streamingPath;
        this.publicationDateTime = publicationDateTime;
        this.isStreamable = isStreamable;
        this.chapter = chapter;
    }

    public static Video createVideo(
            String name, String uploadPath, LocalDateTime publicationDateTime, Chapter chapter) {
        return Video.builder()
                .name(name)
                .uploadPath(uploadPath)
                .publicationDateTime(publicationDateTime)
                .isStreamable(false)
                .chapter(chapter)
                .build();
    }

    public void finalizeVideoUpload(String streamingPath) {
        this.streamingPath = PATH.CDN_STREAMING_PATH.getPath() + streamingPath;
        isStreamable = true;
    }
}
