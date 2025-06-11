package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;

@Data
public class StudentsDTO {
    private Integer studentID;
    private Boolean gender;
    private String fullName;
    private String className;
    private Boolean isActive;
    private Integer parentID;
    private String parentFullName;
}
