package com.team_3.School_Medical_Management_System.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SignUpRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;


    private String FullName;
    @Pattern(regexp = "^(84|0)(3|5|7|8|9)[0-9]{8}$", message = "Phone invalid")
    private String Phone;

    private int IsActive;

    @NotBlank(message = "Certification Not allow empty")
    private String Certification;
    @NotBlank(message = "Specialisation Not allow empty")
    private String Specialisation;

    public String getSpecialisation() {
        return Specialisation;
    }

    public void setSpecialisation(String specialisation) {
        Specialisation = specialisation;
    }

    public String getCertification() {
        return Certification;
    }

    public void setCertification(String certification) {
        Certification = certification;
    }

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    // Thêm trường role (có thể tùy chọn nếu dùng endpoint riêng)

}