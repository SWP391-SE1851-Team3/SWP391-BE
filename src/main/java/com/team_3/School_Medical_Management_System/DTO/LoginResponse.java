package com.team_3.School_Medical_Management_System.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginResponse {
    private String email;
    private int role;
    private int parentId;// 1: Manager, 2: Nurse, 3: Parent
}
