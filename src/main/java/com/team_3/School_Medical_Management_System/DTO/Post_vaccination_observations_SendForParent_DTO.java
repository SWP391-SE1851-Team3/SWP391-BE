package com.team_3.School_Medical_Management_System.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Post_vaccination_observations_SendForParent_DTO {
    private LocalDateTime observation_time;
    private String symptoms;
    private String severity;
    private String notes;
    private String status;
    private String VaccitypeName;
    private String StudentName;
    private String editNurseName;
    private Integer editNurseID;
    private String CreateNurseName;
    private Integer CreateNurseID;
    private Integer StudentID;
    private Integer VaccitypeID;
    private Integer parentID;
    @JsonIgnore
    private String email;
    private Integer VaccinationRecordID;
    private String className;
}
