package com.team_3.School_Medical_Management_System.DTO;

import java.util.List;

public class JwtResponse {
    private String token;
    private Long id;
    private String fullName;
    private String email;
    private List<String> roles;

    public JwtResponse(String token, Long id, String fullName, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.roles = roles;
    }

    // Getters
    public String getToken() { return token; }
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public List<String> getRoles() { return roles; }
}
