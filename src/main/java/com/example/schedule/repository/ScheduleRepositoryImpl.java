package com.example.schedule.repository;

import com.example.schedule.dto.*;
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
        jdbcInsert.withTableName("todo").usingGeneratedKeyColumns("id");
        String now = LocalDate.now().toString();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userid", schedule.getUserId());
        parameters.put("password", schedule.getPassword());
        parameters.put("todo", schedule.getTodo());
        parameters.put("todocreatedate", now);
        parameters.put("todoupdatedate", now);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ResponseDto(key.longValue(), schedule.getUserId(), schedule.getTodo(), now , now);
    }

    @Override
    public Optional<ResponseDto> findScheduleById(Long id) {
        List<ResponseDto> result = jdbcTemplate.query("SELECT id, userid, todo, todocreatedate, todoupdatedate FROM todo WHERE id = ?",scheduleMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<ScheduleListDto> findScheduleByUserId(Long userid) {
        List<ScheduleListDto> result = jdbcTemplate.query("SELECT t.id, t.userid, u.user, t.todo, t.todocreatedate, t.todoupdatedate FROM todo t JOIN user u ON t.userid = u.userid WHERE t.userid = ? ORDER BY t.todoupdatedate DESC",scheduleFindMapper(), userid);
        return result;
    }

    @Override
    public List<ScheduleListDto> findSchedulePage(int pageNumber, int pageSize) {
        List<ScheduleListDto> result = jdbcTemplate.query("SELECT t.id, t.userid, u.user, t.todo, t.todocreatedate, t.todoupdatedate FROM todo t JOIN user u ON t.userid = u.userid LIMIT ? OFFSET ?",scheduleFindMapper(), pageSize, pageNumber * pageSize  );
        return result;
    }


    @Override
    public Optional<PasswordDto> passwordGet(Long id) {
        List<PasswordDto> result = jdbcTemplate.query("SELECT userid, password FROM todo WHERE id = ?",passwordMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public void updateSchedule(RequestDto request) {
        jdbcTemplate.update("UPDATE todo t JOIN user u ON t.userid = u.userid SET u.user = ?, t.todo = ?, t.todoupdatedate = date_format(now(), '%Y-%m-%d') WHERE t.id = ?", request.getUser(), request.getTodo(), request.getId());
    }

    @Override
    public void deleteSchedule(RequestDto request) {
        jdbcTemplate.update("DELETE FROM todo WHERE id = ?", request.getId());
    }

    @Override
    public Optional<UseridDto> findUser(String user) {
        List<UseridDto> result = jdbcTemplate.query("SELECT userid FROM user WHERE user = ?", userMapper(), user);
        return result.stream().findAny();
    }

    @Override
    public Long saveUser(String user, String mail) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("userid");
        String now = LocalDate.now().toString();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user", user);
        parameters.put("mail", mail);
        parameters.put("usercreatedate", now);
        parameters.put("userupdatedate", now);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return key.longValue();
    }

    private RowMapper<UseridDto> userMapper() {
        return new RowMapper<UseridDto>() {
            @Override
            public UseridDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new UseridDto(
                        rs.getLong("userid")
                );
            }
        };
    }

    private RowMapper<PasswordDto> passwordMapper() {
        return new RowMapper<PasswordDto>() {
            @Override
            public PasswordDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new PasswordDto(
                        rs.getLong("userid"),
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
                        rs.getLong("userid"),
                        rs.getString("todo"),
                        rs.getString("todocreatedate"),
                        rs.getString("todoupdatedate")
                );
            }
        };
    }

    private RowMapper<ScheduleListDto> scheduleFindMapper() {
        return new RowMapper<ScheduleListDto>() {
            @Override
            public ScheduleListDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleListDto(
                        rs.getLong("id"),
                        rs.getLong("userid"),
                        rs.getString("user"),
                        rs.getString("todo"),
                        rs.getString("todocreatedate"),
                        rs.getString("todoupdatedate")
                );
            }
        };
    }

}
