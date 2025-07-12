package com.team_3.School_Medical_Management_System.DTO;

import lombok.*;


@Getter
@Setter
@ToString
public class LoginResponse {
    private String email;
    private String fullName;
    private String token;


    public LoginResponse(String email, String fullName ,String token) {
        this.email = email;
        this.fullName = fullName;
        this.token = token;


    }

}

