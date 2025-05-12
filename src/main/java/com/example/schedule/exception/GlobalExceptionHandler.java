package com.example.schedule.exception;

import com.example.schedule.dto.ErrorResponseDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

//오류 처리를 관리해줍니다.
@ControllerAdvice
public class GlobalExceptionHandler {

    //일정 id를 이용한 조회가 실패한 경우 HTTP 상태와 메시지를 출력해줍니다.
    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(ScheduleNotFoundException e) {
        ErrorResponseDto response = new ErrorResponseDto(
                HttpStatus.NOT_FOUND.toString(),
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //유저 id를 이용한 조회가 실패한 경우 HTTP 상태와 메시지를 출력해줍니다.
    @ExceptionHandler(UserScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(UserScheduleNotFoundException e) {
        ErrorResponseDto response = new ErrorResponseDto(
                HttpStatus.NOT_FOUND.toString(),
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //입력된 비밀번호가 다른 경우 HTTP 상태와 메시지를 출력해줍니다.
    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleWrongPassword(WrongPasswordException e) {
        ErrorResponseDto response = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.toString(),
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Valid 어노테이션에 의해 발생한 오류에 대한 HTTP 상태와 메시지를 출력해줍니다.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValid(MethodArgumentNotValidException e) {
        //Valid에 의한 오류가 여러가지 발생할 수 있기 때문에 오류 메시지를 list에 담습니다.
        List<String> validMessage = e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        ErrorResponseDto response = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.toString(),
                validMessage.toString() // List에 담긴 오류메시지를 모두 출력해줍니다.
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
