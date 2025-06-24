package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class HealthConsultationDTO {
    private int consultID;
    private int studentID;
    private String studentName;
    private int checkID;
    private Date scheduledDate;
    private String status;
    private String reason;
}
