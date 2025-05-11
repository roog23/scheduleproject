package com.example.schedule.repository;

import com.example.schedule.dto.*;
import com.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface Repository {
    ResponseDto saveSchedule(Schedule schedule);

    Optional<ResponseDto> findScheduleById(Long id);

    List<ResponseDto> findScheduleByUserId(Long userid);

    Optional<PasswordDto> passwordGet(Long id);

    void updateSchedule(RequestDto request);

    void deleteSchedule(RequestDto request);

    Optional<UseridDto> findUser(String user);

    Long saveUser(String user, String mail);

    List<ScheduleListDto> findSchedulePage(int pageNumber, int pageSize);
}
