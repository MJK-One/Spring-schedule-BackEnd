package com.example.schedule.repository.Schedule;

import com.example.schedule.dto.Schedule.ScheduleDto;
import com.example.schedule.entity.ScheduleEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcScheduleRepository implements IScheduleRepository {
    private final JdbcTemplate jdbcTemplate; // JdbcTemplate 의존성 주입

    // 생성자: JdbcTemplate 주입
    public JdbcScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 스케줄 저장 메서드
    @Override
    public ScheduleDto saveSchedule(ScheduleEntity schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        // 파라미터 맵 생성
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("task", schedule.getTask()); // 스케줄
        parameters.put("name", schedule.getName()); // 작성자명
        parameters.put("AuthorID", schedule.getAuthorId()); // 작성자 ID
        parameters.put("password", schedule.getPassword()); // 비밀번호
        parameters.put("createPostTime", LocalDateTime.now()); // 작성일
        parameters.put("updatePostTime", LocalDateTime.now()); // 수정일은 작성일과 동일

        // 스케줄 저장 후 생성된 키를 가져옴
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        // DTO 반환
        return new ScheduleDto(key.longValue(), schedule.getTask(), schedule.getName(), schedule.getAuthorId(), schedule.getPassword(), LocalDateTime.now(), LocalDateTime.now());
    }

    // 모든 스케줄 조회 메서드
    @Override
    public List<ScheduleDto> findAllSchedules(LocalDate findDate, String findName, Long findAuthor, int page, int size) {
        // 기본 SQL 쿼리
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE 1=1");
        List<Object> params = new ArrayList<>(); // 쿼리 파라미터를 저장할 리스트

        // 날짜 조건 추가
        if (findDate != null) {
            sql.append(" AND DATE(updatePostTime) = ?");
            params.add(findDate);
        }
        // 이름 조건 추가
        if (findName != null) {
            sql.append(" AND name = ?");
            params.add(findName);
        }
        // 작성자 ID 조건 추가
        if (findAuthor != null) {
            sql.append(" AND AuthorID = ?");
            params.add(findAuthor);
        }

        sql.append(" ORDER BY updatePostTime DESC"); // 수정일 기준 내림차순 정렬
        sql.append(" LIMIT ? OFFSET ?"); // 페이지네이션 추가
        params.add(size); // 페이지 크기
        params.add(page * size); // 오프셋 계산

        // 쿼리 실행 및 결과 반환
        return jdbcTemplate.query(sql.toString(),
                (rs, rowNum) -> new ScheduleDto(
                    rs.getLong("id"), // 결과 집합에서 "id" 컬럼 값을 Long으로 가져옴
                    rs.getString("task"), // 결과 집합에서 "task" 컬럼 값을 String으로 가져옴
                    rs.getString("name"), // 결과 집합에서 "name" 컬럼 값을 String으로 가져옴
                    rs.getLong("authorid"), // 결과 집합에서 "author" 컬럼 값을 Long으로 가져옴
                    rs.getString("password"), // 결과 집합에서 "password" 컬럼 값을 String으로 가져옴
                    rs.getTimestamp("createPostTime").toLocalDateTime(), // 결과 집합에서 "createPostTime" 컬럼 값을 LocalDateTime으로 변환
                    rs.getTimestamp("updatePostTime").toLocalDateTime() // 결과 집합에서 "updatePostTime" 컬럼 값을 LocalDateTime으로 변환
        ), params.toArray());
    }

    // ID로 스케줄 조회 메서드
    @Override
    public ScheduleEntity findScheduleById(Long id) {
        // ID로 스케줄을 조회하여 ScheduleEntity 반환
        return jdbcTemplate.queryForObject("SELECT * FROM schedule WHERE id = ?",
                (rs, rowNum) -> new ScheduleEntity(
                    rs.getLong("id"), // 결과 집합에서 "id" 컬럼 값을 Long으로 가져옴
                    rs.getString("task"), // 결과 집합에서 "task" 컬럼 값을 String으로 가져옴
                    rs.getString("name"), // 결과 집합에서 "name" 컬럼 값을 String으로 가져옴
                    rs.getLong("authorid"), // 결과 집합에서 "author" 컬럼 값을 Long으로 가져옴
                    rs.getString("password"), // 결과 집합에서 "password" 컬럼 값을 String으로 가져옴
                    rs.getTimestamp("createPostTime").toLocalDateTime(), // 결과 집합에서 "createPostTime" 컬럼 값을 LocalDateTime으로 변환
                    rs.getTimestamp("updatePostTime").toLocalDateTime() // 결과 집합에서 "updatePostTime" 컬럼 값을 LocalDateTime으로 변환
        ), id);
    }

    // 스케줄 업데이트 메서드
    @Override
    public int updateSchedule(Long id, String task, String name) {
        // 스케줄을 업데이트하고 변경된 행 수 반환
        return jdbcTemplate.update("UPDATE schedule SET task = ?, name = ?, updatePostTime = ? WHERE id = ?", task, name, LocalDateTime.now(), id);
    }

    // 스케줄 삭제 메서드
    @Override
    public int deleteSchedule(Long id) {
        // ID로 스케줄 삭제하고 삭제된 행 수 반환
        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }

}
