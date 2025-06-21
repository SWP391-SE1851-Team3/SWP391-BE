package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class HealthCheck_StudentDTO {
    private int checkID;
    private int studentID;
    private float height;
    private float weight;
    private String visionLeft;
    private String visionRight;
    private String hearing;
    private String dentalCheck;
    private float temperature;

}
