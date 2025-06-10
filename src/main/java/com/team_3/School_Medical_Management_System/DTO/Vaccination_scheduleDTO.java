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
//    private int Vaccination_ID;
    private String Name;
    private String batch_number;
    private Date scheduled_date;
    private String location;
    private String FullName;
    private  Date received_date;
    private String status;
    private String notes;
}
