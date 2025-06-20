package com.team_3.School_Medical_Management_System.DTO;

import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
