package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
