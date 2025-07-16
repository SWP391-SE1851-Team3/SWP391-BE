package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;

@Data
public class AccountDTO {
   private int id;
    private String userName;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    // "Parent" hoặc "SchoolNurse"

 private int isActive;
    public AccountDTO() {
        // Default constructor
    }


}
