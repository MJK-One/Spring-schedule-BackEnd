package com.example.schedule.service;

import com.example.schedule.dto.Schedule.CreateScheduleRequest;
import com.example.schedule.dto.Schedule.ScheduleDto;
import com.example.schedule.entity.ScheduleEntity;
import com.example.schedule.repository.Schedule.IScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class DefaultScheduleService implements IScheduleService {
    private final IScheduleRepository scheduleRepository;

    public DefaultScheduleService(IScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
    @Override
    public ScheduleDto saveSchedule(CreateScheduleRequest dto) {
        ScheduleEntity schedule = new ScheduleEntity(dto.getTask(), dto.getName(), dto.getAuthorId(), dto.getPassword());
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleDto> findAllSchedules(LocalDate findDate, String findName, Long findAuthor, int page, int size) {
        return scheduleRepository.findAllSchedules(findDate, findName, findAuthor, page, size);
    }


    @Override
    public ScheduleDto findScheduleById(Long id) {
        ScheduleEntity schedule = scheduleRepository.findScheduleById(id);
        return new ScheduleDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleDto updateSchedule(Long id, String task, String name, String password) {
        // ID로 스케줄을 조회
        ScheduleEntity schedule = scheduleRepository.findScheduleById(id);

        // 비밀번호 확인: 비밀번호가 null인지 확인
        if (schedule.getPassword() == null || schedule.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Password is not set for this schedule");
        }

        // 비밀번호가 일치하는지 확인
        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid password");
        }

        // 스케줄 업데이트
        int updateRow = scheduleRepository.updateSchedule(id, task, name);

        // 변경된 행이 없을 경우 예외 발생
        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        // 수정된 스케줄의 DTO 반환
        return new ScheduleDto(schedule);
    }


    @Transactional
    @Override
    public void deleteSchedule(Long id, String password) {
        // 해당 ID로 스케줄을 조회
        ScheduleEntity schedule = scheduleRepository.findScheduleById(id);

        // 비밀번호 확인: 비밀번호가 null인지 확인
        if (schedule.getPassword() == null || schedule.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Password is not set for this schedule");
        }

        // 비밀번호가 일치하는지 확인
        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid password");
        }

        // 스케줄 삭제
        int deleteRow = scheduleRepository.deleteSchedule(id);

        // 삭제된 행이 없는 경우 예외 발생
        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
