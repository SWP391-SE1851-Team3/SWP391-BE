package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class HealthConsultationDTO {
    private int consultID;
    private int studentID;
    private Date consultDate;
    private String studentName;
    private String className;
    private int checkID;
    private String status;
    private String location;
    private String reason;

    // Added fields for tracking creation and updates
    private Integer CreatedByNurseID;
    private Integer UpdatedByNurseID;
    private Date create_at;
    private Date update_at;
    private String createdByNurseName;
    private String updatedByNurseName;
    private String scheduleName;
}
