package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 일정 정보에 대한 응답을 해주는 DTO
@Getter
@AllArgsConstructor
public class ScheduleInfoResponseDto {
    private Long id;
    private Long userid;
    private String user;
    private String todo;
    private String createdate;
    private String updatedate;
}
