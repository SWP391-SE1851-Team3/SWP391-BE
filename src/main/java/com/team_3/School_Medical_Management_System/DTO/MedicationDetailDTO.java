package com.team_3.School_Medical_Management_System.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class MedicationDetailDTO {
    private int medicationDetailId;
    @NotBlank(message = "Medication name cannot be empty")
    private String medicationName;

    @NotBlank(message = "Dosage cannot be empty")
    private String dosage;

    @NotBlank(message = "Times to use cannot be empty")
    private String timesToUse;

    private String notes;
}
