package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class HealthConsultationUpdateDTO {
    private int consultID;
    private int studentID;
    private Date consultDate;
    private String studentName;
    private int checkID;
    private String status;
    private String location;
    private String reason;

    // Only update-related fields
    private Integer UpdatedByNurseID;
    private Date update_at;
    private String updatedByNurseName;

    // No creation-related fields
    // CreatedByNurseID and createdByNurseName are excluded
}
