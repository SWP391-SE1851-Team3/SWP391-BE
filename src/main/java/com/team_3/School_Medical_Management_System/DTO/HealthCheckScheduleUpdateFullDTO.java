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
public class HealthCheckScheduleUpdateFullDTO {
    private int health_ScheduleID;
    private Date schedule_Date;
    private String name;
    private String location;
    private String notes;
    private String status;

    // Only update-related fields
    private Date update_at;
    private Integer updatedByNurseID;
    private String updatedByNurseName;

    // Explicitly NO creation-related fields
    // createdByNurseID and createdByNurseName are excluded
}
