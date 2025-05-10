package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class UseridDto {
    private final Long userid;

    public UseridDto(Long user) {
        this.userid = user;
    }
}
