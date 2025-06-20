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
    private int VaccinationRecordID;
    private String notes;
    private String  Vaccine_Name;
    private String Student_Name;;
    private LocalDateTime observation_time;
    private String symptoms;
    private String severity;
    private String observation_notes;
    private String Nurse_Name;
}

