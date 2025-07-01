package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;

@Data
public class AccountDTO {

    private String username;
    private String password;
    private String email;


    public AccountDTO() {
        // Default constructor
    }


}
