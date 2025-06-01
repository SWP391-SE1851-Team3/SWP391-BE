package com.team_3.School_Medical_Management_System.DTO;

public class ManagerLoginResponeDTO {
    private String email;
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
