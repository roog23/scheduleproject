package com.example.schedule.service;

import com.example.schedule.dto.RequestDto;
import com.example.schedule.dto.ResponseDto;
import com.example.schedule.dto.ScheduleListDto;

import java.util.List;

public interface Service {
    ResponseDto saveSchedule(RequestDto request);

    ResponseDto findScheduleById(Long id);

    List<ScheduleListDto> findScheduleByUserId(RequestDto request);

    ResponseDto updateSchedule(RequestDto request);

    void deleteSchedule(RequestDto request);

    List<ScheduleListDto> findSchedulePage(int pageNumber, int pageSize);
}
