package com.example.schedule.repository.Author;

import com.example.schedule.entity.AuthorEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcAuthorRepository implements IAuthorRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAuthorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AuthorEntity save(AuthorEntity author) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("Author").usingGeneratedKeyColumns("AuthorID");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Email", author.getEmail());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new AuthorEntity(key.longValue(), author.getEmail(), LocalDateTime.now(), LocalDateTime.now());
    }

    @Override
    public AuthorEntity findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Author WHERE AuthorID = ?", (rs, rowNum) ->
                new AuthorEntity(
                        rs.getLong("AuthorID"),
                        rs.getString("Email"),
                        rs.getTimestamp("createPostTime").toLocalDateTime(),
                        rs.getTimestamp("updatePostTime").toLocalDateTime()
                ), id);
    }
}