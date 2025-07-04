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
public class HealthCheck_ScheduleDTO {
    private int health_ScheduleID;
    private Date schedule_Date;
    private String name;
    private String location;
    private String notes;
    private String status;

    // Added new fields for tracking creation and updates
    private Date create_at;
    private Date update_at;
    private Integer CreatedByNurseID;
    private Integer UpdatedByNurseID;
    private String createdByNurseName;
    private String updatedByNurseName;
}
