package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 기본 응답을 위한 DTO
@Getter
@AllArgsConstructor
public class ResponseDto {

    private Long id;
    private Long userid;
    private String todo;
    private String createdate;
    private String updatedate;

}
