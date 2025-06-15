package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String userName;
    private String fullName;
    private String phone;
    private String email;
    private int  isActive;
  private Integer roleID;
 ///  private String roleName;
    private String occupation; // Chỉ cho Parent
    private String relationship; // Chỉ cho Parent
    private String certification; // Chỉ cho SchoolNurse
    private String specialisation; // Chỉ cho SchoolNurse
}
