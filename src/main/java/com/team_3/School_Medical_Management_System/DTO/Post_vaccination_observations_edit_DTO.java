package com.team_3.School_Medical_Management_System.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Post_vaccination_observations_edit_DTO {
    private Integer observation_id;
    private LocalDateTime observation_time;
    private String symptoms;
    private String severity;
    private String notes;
    private String status;
    private Integer VaccinationRecordID;
    private Integer editNurseID;
    private Integer createNurseID;
    private String editNurseName;
    private String createNurseName;
}
