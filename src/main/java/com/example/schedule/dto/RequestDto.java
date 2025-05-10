package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class RequestDto {
    private Long id;
    private Long userid;
    private String user;
    private String mail;
    private String password;
    private String todo;
    private String updatedate;

    public void setUserId(Long userId) {
        this.userid =userId;
    }
}
