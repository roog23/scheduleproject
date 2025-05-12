package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class UseridResponseDto {
    private final Long userid;

    public UseridResponseDto(Long user) {
        this.userid = user;
    }
}
