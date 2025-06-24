package com.team_3.School_Medical_Management_System.DTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Vaccination_recordsDTO {
    private Integer VaccinationRecordID;
    private String notes;
    private Integer StudentID;
    private String StudentName;
    private LocalDateTime observation_time;
    private String symptoms;
    private String severity;
    private String observation_notes;
    private Integer NurseID;
    private Integer BatchID;
    private String VaccineName;
    private String nurse_name;
    private String status;
    private String  className;
}

