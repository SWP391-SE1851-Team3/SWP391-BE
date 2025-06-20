package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;

@Data
public class HealthConsentFormDTO {
    private int formID;
    private int studentID;
    private String studentName;
    private int healthScheduleID;
    private String healthScheduleName;
    private Boolean isAgreed;
    private String notes;
    private Boolean isProcessed;
}
