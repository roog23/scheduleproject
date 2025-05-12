package com.example.schedule.exception;

public class UserScheduleNotFoundException extends RuntimeException{

    public UserScheduleNotFoundException() {
        super("해당 유저의 일정을 찾을수 없습니다.");
    }
}
