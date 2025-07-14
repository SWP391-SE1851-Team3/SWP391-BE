package com.team_3.School_Medical_Management_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
   /// private Integer id;
 // "Parent" hoặc "SchoolNurse"
    private String userName;
    private String password;
    private String fullName;
    private String phone;
    private String email;
    private int isActive;
    private Integer roleId;
    // Các trường riêng cho Parent
    private String occupation;
    private String relationship;
    // Các trường riêng cho SchoolNurse
    private String certification;
    private String specialisation;


    public UserDTO() {
    }



}
