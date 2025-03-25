package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

// 스케줄 엔티티 클래스
@Getter
@AllArgsConstructor
public class ScheduleEntity {
    private Long id; // 스케줄 ID
    private String task; // 스케줄
    private String name; // 스케줄 작성자명
    private String password; // 비밀번호
    private LocalDateTime createPostTime; // 작성일
    private LocalDateTime updatePostTime; // 수정일

    public ScheduleEntity(String task, String name, String password) {
        this.task = task;
        this.name = name;
        this.password = password;
    }
}
