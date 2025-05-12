package com.example.schedule.repository;

import com.example.schedule.dto.*;
import com.example.schedule.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    //유저가 존재하는지 조회하기 위한 기능
    @Override
    public Optional<UseridResponseDto> findUser(String user) {
        List<UseridResponseDto> result = jdbcTemplate.query("SELECT userid FROM user WHERE user = ?", userMapper(), user);
        return result.stream().findAny();
    }

    //유저를 저장하기 위한 기능
    @Override
    public Long saveUser(String user, String mail) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        //user 테이블에 userid가 key인 데이터 테이블을 사용합니다.
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("userid");
        String now = LocalDate.now().toString();    //현재 날짜를 등록합니다.
        Map<String, Object> parameters = new HashMap<>();   // key-value로 값을 입력합니다.
        parameters.put("user", user);
        parameters.put("mail", mail);
        parameters.put("usercreatedate", now);
        parameters.put("userupdatedate", now);

        //insert를 실행하고 key 값을 전달받습니다.
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return key.longValue();
    }

    //일정 저장하기 위한 기능
    @Override
    public ResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        // todo 테이블에 key가 id인 테이블을 사용합니다.
        jdbcInsert.withTableName("todo").usingGeneratedKeyColumns("id");
        String now = LocalDate.now().toString();    //현재 날짜를 등록합니다.
        Map<String, Object> parameters = new HashMap<>();   // key-value로 값을 입력합니다.
        parameters.put("userid", schedule.getUserId());
        parameters.put("password", schedule.getPassword());
        parameters.put("todo", schedule.getTodo());
        parameters.put("todocreatedate", now);
        parameters.put("todoupdatedate", now);

        //insert를 실행하고 key 값을 전달받습니다.
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new ResponseDto(key.longValue(), schedule.getUserId(), schedule.getTodo(), now , now);
    }

    //일정 id를 이용한 단일 조회하기 위한 기능
    @Override
    public Optional<ScheduleInfoResponseDto> findScheduleById(Long id) {
        //쿼리에 해당하는 일정 정보를 반환합니다.
        List<ScheduleInfoResponseDto> result = jdbcTemplate.query("SELECT t.id, t.userid, u.user, t.todo, t.todocreatedate, t.todoupdatedate FROM todo t JOIN user u ON t.userid = u.userid WHERE id = ?",scheduleFindMapper(), id);
        return result.stream().findAny();
    }

    //유저 id를 이용한 다건 조회하기 위한 기능
    @Override
    public List<ScheduleInfoResponseDto> findScheduleByUserId(Long userid) {
        //쿼리에 해당하는 일정 정보 모두를 반환합니다.
        List<ScheduleInfoResponseDto> result = jdbcTemplate.query("SELECT t.id, t.userid, u.user, t.todo, t.todocreatedate, t.todoupdatedate FROM todo t JOIN user u ON t.userid = u.userid WHERE t.userid = ? ORDER BY t.todoupdatedate DESC",scheduleFindMapper(), userid);
        return result;
    }

    //페이지에 해당하는 일정을 다건 출력하기 위한 기능
    @Override
    public List<ScheduleInfoResponseDto> findSchedulePage(int pageNumber, int pageSize) {
        //쿼리에 해당하는 페이지에 출력될 일정 정보 모두를 반환합니다.
        List<ScheduleInfoResponseDto> result = jdbcTemplate.query("SELECT t.id, t.userid, u.user, t.todo, t.todocreatedate, t.todoupdatedate FROM todo t JOIN user u ON t.userid = u.userid LIMIT ? OFFSET ?",scheduleFindMapper(), pageSize, pageNumber * pageSize  );
        return result;
    }

    //일정에 저장된 비밀번호를 전달하기 위한 기능
    @Override
    public Optional<PasswordResponseDto> passwordGet(Long id) {
        //쿼리에 해당하는 유저 id와 비밀번호 정보를 반환합니다.
        List<PasswordResponseDto> result = jdbcTemplate.query("SELECT userid, password FROM todo WHERE id = ?",passwordMapper(), id);
        return result.stream().findAny();
    }

    //일정을 수정하기 위한 기능
    @Override
    public void updateSchedule(RequestDto request) {
        jdbcTemplate.update("UPDATE todo t JOIN user u ON t.userid = u.userid SET u.user = ?, t.todo = ?, t.todoupdatedate = date_format(now(), '%Y-%m-%d') WHERE t.id = ?", request.getUser(), request.getTodo(), request.getId());
    }

    //일정을 삭제하기 위한 기능
    @Override
    public void deleteSchedule(RequestDto request) {
        jdbcTemplate.update("DELETE FROM todo WHERE id = ?", request.getId());
    }

    //쿼리 결과가 userid를 반환하는 경우 사용됩니다.
    private RowMapper<UseridResponseDto> userMapper() {
        return new RowMapper<UseridResponseDto>() {
            @Override
            public UseridResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new UseridResponseDto(
                        rs.getLong("userid")
                );
            }
        };
    }

    //쿼리 결과가 userid, password를 반환하는 경우 사용됩니다.
    private RowMapper<PasswordResponseDto> passwordMapper() {
        return new RowMapper<PasswordResponseDto>() {
            @Override
            public PasswordResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new PasswordResponseDto(
                        rs.getLong("userid"),
                        rs.getString("password")
                );
            }
        };
    }

    //쿼리 결과가 id, userid, user, todo, todocreatedate, todoupdatedate를 반환하는 경우 사용됩니다.
    private RowMapper<ScheduleInfoResponseDto> scheduleFindMapper() {
        return new RowMapper<ScheduleInfoResponseDto>() {
            @Override
            public ScheduleInfoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleInfoResponseDto(
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
