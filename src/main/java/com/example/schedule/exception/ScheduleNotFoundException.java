package com.example.schedule.exception;

public class ScheduleNotFoundException extends RuntimeException{
    public ScheduleNotFoundException() {
        super("해당 일정을 찾을수 없습니다.");
    }
}
