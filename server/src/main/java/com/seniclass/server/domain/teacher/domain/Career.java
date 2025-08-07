package com.seniclass.server.domain.teacher.domain;

import com.seniclass.server.domain.teacher.enums.Type;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "careers")
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Teacher teacher;

    @Builder(access = AccessLevel.PRIVATE)
    private Career(String name, Type type, Teacher teacher) {
        this.name = name;
        this.type = type;
        this.teacher = teacher;
    }

    public static Career create(String name, Type type, Teacher teacher) {
        return Career.builder().name(name).type(type).teacher(teacher).build();
    }
}
