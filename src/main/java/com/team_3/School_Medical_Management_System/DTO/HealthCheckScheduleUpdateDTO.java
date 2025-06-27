package com.team_3.School_Medical_Management_System.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HealthCheckScheduleUpdateDTO {
    private String name;
    private String location;
    private String notes;
    private String status;
    private Date schedule_Date;
    private Integer updatedByNurseID;
    private String updatedByNurseName;
}
