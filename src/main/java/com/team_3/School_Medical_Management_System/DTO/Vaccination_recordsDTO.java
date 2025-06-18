package com.team_3.School_Medical_Management_System.DTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.time.OffsetDateTime;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Vaccination_recordsDTO {
    @JsonIgnore
    private int VaccinationRecordID;
    private String notes;
    private String  Vaccine_Name;
    private String Student_Name;
    private int schedule_id;
    private int batch_id;
    private OffsetDateTime observation_time;
    private String symptoms;
    private String severity;
    private String observation_notes;
    private String Nurse_Name;
}

