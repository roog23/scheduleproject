package com.example.schedule.repository;

import com.example.schedule.dto.PasswordDto;
import com.example.schedule.dto.RequestDto;
import com.example.schedule.dto.ResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@org.springframework.stereotype.Repository
public class ScheduleRepositoryImpl implements Repository{
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");
        String now = LocalDate.now().toString();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user", schedule.getUser());
        parameters.put("password", schedule.getPassword());
        parameters.put("todo", schedule.getTodo());
        parameters.put("createdate", now);
        parameters.put("updatedate", now);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ResponseDto(key.longValue(), schedule.getUser(), schedule.getTodo(), now , now);
    }

    @Override
    public Optional<ResponseDto> findScheduleById(Long id) {
        List<ResponseDto> result = jdbcTemplate.query("select id, user, todo, createdate, updatedate from schedule where id = ?",scheduleMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<ResponseDto> findScheduleByReq(String user, String updatedate) {
        List<ResponseDto> result = jdbcTemplate.query("SELECT id, user, todo, createdate, updatedate FROM schedule WHERE (? IS NULL OR user = ?) AND (? IS NULL OR updatedate = ?) ORDER BY updatedate DESC",scheduleMapper(), user, user, updatedate, updatedate);
        return result;
    }

    @Override
    public Optional<PasswordDto> passwordGet(Long id) {
        List<PasswordDto> result = jdbcTemplate.query("SELECT password FROM schedule WHERE id = ?",passwordMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public void updateSchedule(RequestDto request) {
        jdbcTemplate.update("update schedule set user = ?, todo = ?, updatedate = date_format(now(), '%Y-%m-%d') where id = ?", request.getUser(), request.getTodo(), request.getId());
    }

    @Override
    public void deleteSchedule(RequestDto request) {
        jdbcTemplate.update("delete from schedule where id = ?", request.getId());
    }

    private RowMapper<PasswordDto> passwordMapper() {
        return new RowMapper<PasswordDto>() {
            @Override
            public PasswordDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new PasswordDto(
                        rs.getString("password")
                );
            }
        };
    }

    private RowMapper<ResponseDto> scheduleMapper() {
        return new RowMapper<ResponseDto>() {
            @Override
            public ResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ResponseDto(
                        rs.getLong("id"),
                        rs.getString("user"),
                        rs.getString("todo"),
                        rs.getString("createdate"),
                        rs.getString("updatedate")
                );
                }
        };
    }

}
