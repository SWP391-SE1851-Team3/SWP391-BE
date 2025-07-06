package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String userName;
    private String password;
    private String fullName;
    private String phone;
    private String email;

}
