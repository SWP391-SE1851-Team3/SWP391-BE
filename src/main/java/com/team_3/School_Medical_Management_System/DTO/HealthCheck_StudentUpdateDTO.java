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
public class HealthCheck_StudentUpdateDTO {
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

    // Only include creation fields (cannot be changed, but needed for reference)
    private Integer updatedByNurseID;
    private Date update_at;
    private String updatedByNurseName;

    // Update fields are excluded
    // update_at, updatedByNurseID, and updatedByNurseName are not here
}
