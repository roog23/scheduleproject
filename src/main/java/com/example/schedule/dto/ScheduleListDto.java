package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleListDto {
    Long id;
    Long userid;
    String user;
    private String todo;
    private String createdate;
    private String updatedate;
}
