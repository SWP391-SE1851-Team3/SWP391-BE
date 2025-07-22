package com.team_3.School_Medical_Management_System.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginRequest {
    @NotBlank
    @Email(message = "Email must be a valid email address")
    private String emailName;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]*$",
            message = "Password must contain only letters and numbers, no special characters")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")

    private String password;

    @NotBlank
    private String role;

    // Getters and Setters


    public String getEmailName() {
        return emailName;
    }

    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
