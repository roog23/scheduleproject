package com.example.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

// 요청을 위한 DTO
@Getter
public class RequestDto {
    private Long id;
    private Long userid;

    @NotBlank(message = "사용자를 반드시 입력해주세요.")
    private String user;

    @Email(message = "이메일 형태로 입력해주세요.")
    private String mail;

    @NotBlank(message = "비밀번호를 반드시 입력해주세요.")
    private String password;

    @NotBlank(message = "할일은 반드시 입력해주세요")
    @Size(max=200, message = "할일은 200자 이내로 입력해주세요.")
    private String todo;

    public void setUserId(Long userId) {
        this.userid =userId;
    }
}
