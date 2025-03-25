package com.example.schedule.repository.Schedule;

import com.example.schedule.dto.Schedule.ScheduleDto;
import com.example.schedule.entity.ScheduleEntity;

import java.time.LocalDate;
import java.util.List;

// 스케줄 레포지토리 인터페이스
public interface IScheduleRepository {
    // 스케줄 저장
    ScheduleDto saveSchedule(ScheduleEntity schedule);

    // 모든 스케줄 조회
    List<ScheduleDto> findAllSchedules(LocalDate findDate, String findName, Long findAuthor, int page, int size);

    // ID로 스케줄 조회
    ScheduleEntity findScheduleById(Long id);

    // 스케줄 업데이트
    int updateSchedule(Long id, String task, String name);

    // 스케줄 삭제
    int deleteSchedule(Long id);
}