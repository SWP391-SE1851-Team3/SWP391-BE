package com.team_3.School_Medical_Management_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDTO {
    private int managerId;
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String email;
    private int isActive;
    private int roleId;
}
