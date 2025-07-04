package com.team_3.School_Medical_Management_System.DTO;

import lombok.*;
import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Post_vaccination_observationsDTO {
    private Integer observation_id;
    private LocalDateTime observation_time;
    private String symptoms;
    private String severity;
    private String notes;
    private String status;
    private Integer VaccinationRecordID;
    private String VaccinationName;
    private String StudentName;
    private String editNurseName;
    private Integer editNurseID;
    private Integer StudentID;
    private Integer VaccinationID;
    private Integer parentID;
    private String className;
    private Integer createNurseId;
    private String createNurseName;
}


