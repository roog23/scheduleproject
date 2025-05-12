package com.example.schedule.exception;

//일정 id를 이용한 조회가 비어있는 경우의 오류 출력 메시지
public class ScheduleNotFoundException extends RuntimeException{
    public ScheduleNotFoundException() {
        super("해당 일정을 찾을수 없습니다.");
    }
}
