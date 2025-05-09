package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class RequestDto {
    private Long id;
    private String user;
    private String password;
    private String todo;
    private String createdate;
    private String updatedate;
}
