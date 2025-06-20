package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class HealthConsultationDTO {
    private int consultationID;
    private int studentID;
    private String studentName;
    private int checkID;
    private String issue;
    private String recommendation;
    private Date scheduledDate;
    private boolean status;
    private String notes;
}
