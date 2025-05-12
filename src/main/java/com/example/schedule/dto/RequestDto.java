package com.example.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RequestDto {
    private Long id;
    private Long userid;

    @NotBlank
    private String user;

    @Email
    private String mail;

    @NotBlank
    private String password;

    @NotBlank
    @Size(max=200)
    private String todo;

    public void setUserId(Long userId) {
        this.userid =userId;
    }
}
