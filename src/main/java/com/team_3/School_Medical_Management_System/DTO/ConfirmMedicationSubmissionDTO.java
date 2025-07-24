package com.team_3.School_Medical_Management_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmMedicationSubmissionDTO {
    private int confirmId;
    private int medicationSubmissionId;
    private String status;
    private Integer nurseId;
    private String reason;
    private String evidence;
}
