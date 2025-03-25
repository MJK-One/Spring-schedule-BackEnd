package com.example.schedule.service;

import java.time.LocalDate;
import java.util.List;

// 스케줄 서비스 인터페이스
public interface IScheduleService {
    // 스케줄을 저장하는 메서드
    ScheduleDto saveSchedule(CreateScheduleRequest dto); // DTO를 기반으로 스케줄 생성 및 저장

    // 모든 스케줄을 조회하는 메서드
    List<ScheduleDto> findAllSchedules(LocalDate findDate, String findName); // 날짜 및 이름 조건에 따라 스케줄 목록 반환

    // 선택한 일정 조회 메서드
    ScheduleDto findScheduleById(Long id); // ID로 특정 스케줄 조회 및 반환

    // 선택한 일정 수정 메서드
    ScheduleDto updateSchedule(Long id, String task, String name, String password); // ID로 스케줄 수정, 비밀번호 확인 필요

    // 선택한 일정 삭제 메서드
    void deleteSchedule(Long id); // ID로 특정 스케줄 삭제
}