package com.example.schedule.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

// 스케줄 생성 요청 DTO
@Getter
public class CreateScheduleRequest {
    @NotBlank(message = "Task cannot be empty") // 작업 필드 유효성 검사
    private String task;

    @NotBlank(message = "Name cannot be empty") // 이름 필드 유효성 검사
    private String name;

    @NotBlank(message = "Password cannot be empty") // 비밀번호 필드 유효성 검사
    private String password;
}