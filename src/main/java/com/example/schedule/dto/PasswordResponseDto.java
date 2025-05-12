package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class PasswordResponseDto {
    private final Long userid;
    private final String password;

    public PasswordResponseDto(Long userid, String password) {
        this.userid = userid;
        this.password = password;
    }
}
