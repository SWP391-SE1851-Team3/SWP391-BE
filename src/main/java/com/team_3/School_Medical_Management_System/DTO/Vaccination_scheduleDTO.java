package com.team_3.School_Medical_Management_System.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team_3.School_Medical_Management_System.Model.SchoolNurse;
import com.team_3.School_Medical_Management_System.Model.Vaccines;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vaccination_scheduleDTO {
    private int schedule_id;
    private String Name;
    private Date scheduled_date;
    private String location;
    private String FullName;
    private String status;
    private String notes;
}
