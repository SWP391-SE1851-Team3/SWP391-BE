package com.team_3.School_Medical_Management_System.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Vaccination_records_SentParent_DTO {
    private Integer studentId;
    private String StudentName;
    private Integer vaccineBatchId;
    private String vaccineBatchName;
    private String symptoms;
    private String severity;
    private String notes;
    private String observation_notes;
    private LocalDateTime observation_time;
    private String status;
    private Integer CreateNurseID;
    private String className;
    private String CreateNurseName;
    private String editNurseName;
    private Integer editnurseID;
    private Integer cosentID;
    // Trả về cho phụ huynh
    private Integer parentID;
    @JsonIgnore
    private String email;

}
