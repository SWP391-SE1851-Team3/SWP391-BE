package com.team_3.School_Medical_Management_System.DTO;

import lombok.*;


@Getter
@Setter
@ToString
public class LoginResponse {
    private String email;
    private int role;
    private int parentId;// 1: Manager, 2: Nurse, 3: Parent
    private String fullName;
    private int  nurseId;
    private int managerId;


    public LoginResponse(String email, int role, int id, String fullName) {
        this.email = email;
        this.role = role;
        this.fullName = fullName;

        // Gán id đúng vào từng role
        if (role == 1) {
            this.parentId = id;
        } else if (role == 2) {
            this.nurseId = id;
        } else if (role == 3) {
            this.managerId = id;
        }
    }






}

