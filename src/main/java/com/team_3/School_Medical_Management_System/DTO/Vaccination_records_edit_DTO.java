package com.team_3.School_Medical_Management_System.DTO;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Vaccination_records_edit_DTO {
    private int VaccinationRecordID;
    private String notes;
    private Integer Student_id;;
    private LocalDateTime observation_time;
    private String symptoms;
    private String severity;
    private String observation_notes;
    private Integer edit_Nurse_id;
    private Integer BatchID;
    private String status;
    private String edit_nurse_name;
    private Integer created_by_nurse_id;
    private String created_by_nurse_name;
}
