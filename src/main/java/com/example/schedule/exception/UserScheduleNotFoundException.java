package com.example.schedule.exception;

//유저 id를 이용한 조회가 비어있는 경우의 오류 출력 메시지
public class UserScheduleNotFoundException extends RuntimeException{

    public UserScheduleNotFoundException() {
        super("해당 유저의 일정을 찾을수 없습니다.");
    }
}
