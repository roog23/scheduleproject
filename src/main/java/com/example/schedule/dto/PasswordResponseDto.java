package com.example.schedule.dto;

import lombok.Getter;

// 유저 id, 비밀번호를 전달하는 비밀번호 확인을 위한 DTO
@Getter
public class PasswordResponseDto {
    private final Long userid;
    private final String password;

    public PasswordResponseDto(Long userid, String password) {
        this.userid = userid;
        this.password = password;
    }
}
