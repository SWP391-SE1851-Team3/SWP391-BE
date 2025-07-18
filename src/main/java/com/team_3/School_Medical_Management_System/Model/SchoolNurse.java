package com.team_3.School_Medical_Management_System.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@ToString
@Entity
@Table
@Setter
@Getter
public class SchoolNurse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer NurseID;
    @NotBlank(message = "UserName Not allow empty")
    private String UserName;
    @NotBlank(message = "Password Not allow empty")
    private String Password;
    @NotBlank(message = "FullName Not allow empty")
    private String FullName;
    @Pattern(regexp = "^(84|0)(3|5|7|8|9)[0-9]{8}$", message = "Phone invalid")
    private String Phone;
    @Email
    private String Email;
    private int IsActive;
    //    @JsonIgnore
    private int RoleID;
    @NotBlank(message = "Certification Not allow empty")
    private String Certification;
    @NotBlank(message = "Specialisation Not allow empty")
    private String Specialisation;


    // CASCADE relationships - Khi xóa Parent sẽ tự động xóa các bảng con


}
