package com.team_3.School_Medical_Management_System.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ManagerLoginResponeDTO {
    @Email
    private String email;
    @NotBlank(message = "password Not allow empty")
    private String password;

    public ManagerLoginResponeDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // getter + setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
