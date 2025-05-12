package com.example.schedule.dto;

import lombok.Getter;

// 유저 id를 전달하기 위한 DTO
@Getter
public class UseridResponseDto {
    private final Long userid;

    public UseridResponseDto(Long user) {
        this.userid = user;
    }
}
