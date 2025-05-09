package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {

    private Long id;
    private String user;
    private String todo;
    private String createdate;
    private String updatedate;

}
