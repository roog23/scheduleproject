package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class PasswordDto {
    private final String password;

    public PasswordDto(String password) {
        this.password = password;
    }
}
