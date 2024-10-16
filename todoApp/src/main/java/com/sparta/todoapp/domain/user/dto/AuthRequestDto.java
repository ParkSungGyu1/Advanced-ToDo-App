package com.sparta.todoapp.domain.user.dto;

import lombok.Getter;

@Getter
public class AuthRequestDto {
    private String email;
    private String memberName;
    private String password;
    private boolean admin = false;

    public void initPassword(String password){
        this.password = password;
    }
}
