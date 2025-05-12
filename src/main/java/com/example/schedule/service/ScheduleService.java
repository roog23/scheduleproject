package com.example.schedule.service;

import com.example.schedule.dto.RequestDto;
import com.example.schedule.dto.ResponseDto;
import com.example.schedule.dto.ScheduleInfoResponseDto;

import java.util.List;

public interface ScheduleService {

    //일정 생성, 저장
    ResponseDto saveSchedule(RequestDto request);

    //일정 id를 이용한 조회하기
    ScheduleInfoResponseDto findScheduleById(Long id);

    //유저 id를 이용한 조회하기
    List<ScheduleInfoResponseDto> findScheduleByUserId(Long userId);

    //페이지에 해당하는 일정을 출력하기
    List<ScheduleInfoResponseDto> findSchedulePage(int pageNumber, int pageSize);

    //일정을 수정하기
    ScheduleInfoResponseDto updateSchedule(RequestDto request);

    //일정을 삭제하기
    void deleteSchedule(RequestDto request);
}
