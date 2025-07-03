package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;

@Data
public class AccountDTO {

    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    // "Parent" hoặc "SchoolNurse"


    public AccountDTO() {
        // Default constructor
    }


}
