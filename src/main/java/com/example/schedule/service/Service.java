package com.example.schedule.service;

import com.example.schedule.dto.RequestDto;
import com.example.schedule.dto.ResponseDto;

import java.util.List;

public interface Service {
    ResponseDto saveSchedule(RequestDto request);

    ResponseDto findScheduleById(Long id);

    List<ResponseDto> findScheduleByUserId(RequestDto request);

    ResponseDto updateSchedule(RequestDto request);

    void deleteSchedule(RequestDto request);
}
