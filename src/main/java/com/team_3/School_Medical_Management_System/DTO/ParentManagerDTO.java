package com.team_3.School_Medical_Management_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParentManagerDTO {
    private String userName;
    private String password;
    private String fullName;
    private String phone;
    private String email;
    private int isActive;
    private Integer roleId;
    private String occupation;
    private String relationship;

    public ParentManagerDTO(String userName, String password, String fullName, String phone, String email, int isActive, Integer roleId) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.isActive = isActive;
        this.roleId = roleId;
    }
}
