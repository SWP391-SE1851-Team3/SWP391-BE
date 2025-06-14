package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class MedicationSubmissionDTO {
    private int medicationSubmissionId;

    @NotNull(message = "Parent ID is required")
    private int parentId;

    @NotNull(message = "Student ID is required")
    private int studentId;

    private String medicineImage;

    @Valid
    @Size(min = 1, message = "At least one medication detail is required")
    private List<MedicationDetailDTO> medicationDetails;
}