package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Data
@NoArgsConstructor
@Getter
@Setter
public class HealthCheckStudentSimplifiedDTO {
    private int checkID;
    private int studentID;

    private float height;
    private float weight;
    private String visionLeft;
    private String visionRight;
    private String hearing;
    private String dentalCheck;
    private String temperature;
    private float bmi;
    private String overallResult;

    private Date create_at;
    private Date update_at;
    private String createdByNurseName;
    private String updatedByNurseName;
    private Integer createdByNurseID;
    private Integer updatedByNurseID;
    private int formID;
    private String className;
    private String fullName;



}
