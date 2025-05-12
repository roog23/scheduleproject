package com.example.schedule.exception;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException() {
        super("잘못된 비밀번호를 입력하셨습니다.");
    }
}
