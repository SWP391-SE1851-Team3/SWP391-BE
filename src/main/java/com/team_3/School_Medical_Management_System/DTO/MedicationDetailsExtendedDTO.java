package com.team_3.School_Medical_Management_System.DTO;

import com.team_3.School_Medical_Management_System.Model.MedicationDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationDetailsExtendedDTO {
    private int medicationSubmissionId;
    private int parentId;
    private int studentId;
    private String medicineImage;
    private String nurseName;
    private String studentClass;
    private List<MedicationDetailDTO> medicationDetails;
    private LocalDateTime submissionDate;
}
