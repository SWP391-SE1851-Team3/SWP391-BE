package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class HealthConsentFormDTO {
    private int formID;
    private int studentID;
    private int parentID; // New field added
    private String studentName;
    private String className;
    private int healthScheduleID;
    private String healthScheduleName;
    private String isAgreed;
    private String notes;

    // Added new fields for tracking when the form was sent and when it expires
    private Date send_date;
    private Date expire_date;
}
