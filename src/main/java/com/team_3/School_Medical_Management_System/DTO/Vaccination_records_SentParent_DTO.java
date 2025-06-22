package com.team_3.School_Medical_Management_System.DTO;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Vaccination_records_SentParent_DTO {
    private Integer studentId;
    private Integer vaccineBatchId;
    private String symptoms;
    private String severity;
    private String notes;
    private String observation_notes;
    private LocalDateTime observation_time;
    private String status;
    private Integer NurseID;

    // Trả về cho phụ huynh
    private String parentName;
    private String email;









}
