package com.example.schedule.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules") // 기본 URL 경로
public class ScheduleController {

    private final IScheduleService scheduleService; // 서비스 레이어의 의존성 주입

    // 생성자: IScheduleService 주입
    public ScheduleController(IScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 생성 API
    @PostMapping // HTTP POST 요청
    public ResponseEntity<ScheduleDto> createSchedule(@Validated @RequestBody CreateScheduleRequest dto) {
        // 유효성 검사를 통과한 요청 DTO를 사용하여 스케줄을 저장
        ScheduleDto createdSchedule = scheduleService.saveSchedule(dto);
        // 생성된 스케줄을 포함한 응답을 생성 (201 Created 상태 코드)
        return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
    }

    // 전체 일정 조회 API
    @GetMapping // HTTP GET 요청을 처리
    public ResponseEntity<List<ScheduleDto>> findAllSchedules(
            @RequestParam(required = false) LocalDate findDate, // 선택적 날짜 파라미터
            @RequestParam(required = false) String findName) { // 선택적 이름 파라미터
        // 요청된 조건에 따라 모든 스케줄을 조회
        return new ResponseEntity<>(scheduleService.findAllSchedules(findDate, findName), HttpStatus.OK);
    }

    // 선택한 일정 조회 API
    @GetMapping("/{id}") // URL 경로에 ID를 포함하여 요청
    public ResponseEntity<ScheduleDto> findScheduleById(@PathVariable Long id) {
        // ID로 스케줄을 조회하여 응답
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    // 선택한 일정 수정 API
    @PatchMapping("/{id}") // URL 경로에 ID를 포함하여 요청
    public ResponseEntity<ScheduleDto> updateSchedule(
            @PathVariable Long id, // URL 경로에서 ID를 추출
            @RequestBody CreateScheduleRequest dto) { // 요청 본문에서 DTO를 추출
        // 스케줄을 업데이트하고 응답
        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto.getTask(), dto.getName(), dto.getPassword()), HttpStatus.OK);
    }

    // 선택한 일정 삭제 API
    @DeleteMapping("/{id}") // URL 경로에 ID를 포함하여 요청
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        // ID로 스케줄을 삭제
        scheduleService.deleteSchedule(id);
        // 삭제 성공 응답 (204 No Content 상태 코드)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
