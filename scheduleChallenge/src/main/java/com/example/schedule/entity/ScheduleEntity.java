package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 스케줄 엔티티 클래스
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity {
    private Long id; // 스케줄 ID
    private String task; // 스케줄
    private String name; // 스케줄 작성자명
    private Long authorId; // 작성자 ID
    private String password; // 비밀번호
    private LocalDateTime createPostTime; // 작성일
    private LocalDateTime updatePostTime; // 수정일

    public ScheduleEntity(String task, String name, Long authorId, String password) {
        this.task = task;
        this.name = name;
        this.authorId = authorId;
        this.password = password;
    }
}
