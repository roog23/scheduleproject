package com.example.schedule.repository;

import com.example.schedule.dto.*;
import com.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    ResponseDto saveSchedule(Schedule schedule);

    Optional<ScheduleInfoResponseDto> findScheduleById(Long id);

    List<ScheduleInfoResponseDto> findScheduleByUserId(Long userid);

    Optional<PasswordResponseDto> passwordGet(Long id);

    void updateSchedule(RequestDto request);

    void deleteSchedule(RequestDto request);

    Optional<UseridResponseDto> findUser(String user);

    Long saveUser(String user, String mail);

    List<ScheduleInfoResponseDto> findSchedulePage(int pageNumber, int pageSize);
}
