package com.example.schedule.service;

import com.example.schedule.dto.RequestDto;
import com.example.schedule.dto.ResponseDto;
import com.example.schedule.dto.ScheduleInfoResponseDto;

import java.util.List;

public interface ScheduleService {
    ResponseDto saveSchedule(RequestDto request);

    ScheduleInfoResponseDto findScheduleById(Long id);

    List<ScheduleInfoResponseDto> findScheduleByUserId(Long userId);

    ScheduleInfoResponseDto updateSchedule(RequestDto request);

    void deleteSchedule(RequestDto request);

    List<ScheduleInfoResponseDto> findSchedulePage(int pageNumber, int pageSize);
}
