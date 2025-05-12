package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

//에러 발생 시 응답하는 DTO
@Getter
@AllArgsConstructor
public class ErrorResponseDto {
    private String status;      // 에러 HTTP 상태
    private String message;     // 에러 메시지
}
