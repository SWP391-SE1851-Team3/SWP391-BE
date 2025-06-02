package com.team_3.School_Medical_Management_System.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ParentLoginResponseDTO {
    @Email
    private String Email;
    @NotBlank(message = "Password Not allow empty")
    private String Password;

    public ParentLoginResponseDTO(String email, String password) {
        Email = email;
        Password = password;
    }
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
