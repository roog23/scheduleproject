package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class PasswordDto {
    private final Long userid;
    private final String password;

    public PasswordDto(Long userid, String password) {
        this.userid = userid;
        this.password = password;
    }
}
