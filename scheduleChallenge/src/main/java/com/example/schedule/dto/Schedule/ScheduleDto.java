package com.example.schedule.dto.Schedule;

import com.example.schedule.entity.ScheduleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleDto {
    private Long id;
    private String task;
    private String name;
    private Long authorId;
    private String password;
    private LocalDateTime createPostTime;
    private LocalDateTime updatePostTime;

    public ScheduleDto(ScheduleEntity schedule) {
        this.id = schedule.getId();
        this.task = schedule.getTask();
        this.name = schedule.getName();
        this.authorId = schedule.getAuthorId();
        this.password = schedule.getPassword();
        this.createPostTime = schedule.getCreatePostTime();
        this.updatePostTime = schedule.getUpdatePostTime();
    }
}
