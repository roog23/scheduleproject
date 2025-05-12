package com.example.schedule.repository;

import com.example.schedule.dto.*;
import com.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    //유저가 존재하는지 조회하기 위한 기능
    Optional<UseridResponseDto> findUser(String user);

    //유저를 저장하기 위한 기능
    Long saveUser(String user, String mail);

    //일정 저장을 위한 기능
    ResponseDto saveSchedule(Schedule schedule);

    //일정 id를 이용한 단일 조회하기 위한 기능
    Optional<ScheduleInfoResponseDto> findScheduleById(Long id);

    //유저 id를 이용한 다건 조회하기 위한 기능
    List<ScheduleInfoResponseDto> findScheduleByUserId(Long userid);

    //페이지에 해당하는 일정을 다건 출력하기 위한 기능
    List<ScheduleInfoResponseDto> findSchedulePage(int pageNumber, int pageSize);

    //일정에 저장된 비밀번호를 전달하기 위한 기능
    Optional<PasswordResponseDto> passwordGet(Long id);

    //일정을 수정하기 위한 기능
    void updateSchedule(RequestDto request);

    //일정을 삭제하기 위한 기능
    void deleteSchedule(RequestDto request);

}
