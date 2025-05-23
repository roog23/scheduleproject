package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 일정 정보를 담는 객체
@Getter
@AllArgsConstructor
public class Schedule {

    private Long userId;
    private String user;
    private String password;
    private String todo;
    private String createdate;
    private String updatedate;

    public Schedule(Long userid, String user, String password, String todo) {
        this.userId = userid;
        this.user = user;
        this.password = password;
        this.todo = todo;
    }

}
