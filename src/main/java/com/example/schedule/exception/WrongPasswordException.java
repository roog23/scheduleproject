package com.example.schedule.exception;

// 비밀번호가 일치하지 않는 경우의 오류 출력 메시지
public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException() {
        super("잘못된 비밀번호를 입력하셨습니다.");
    }
}
