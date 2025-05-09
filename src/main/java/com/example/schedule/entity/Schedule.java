package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String user;
    private String password;
    private String todo;
    private String createdate;
    private String updatedate;

    public Schedule(String user, String password, String todo) {
        this.user = user;
        this.password = password;
        this.todo = todo;
    }

}
