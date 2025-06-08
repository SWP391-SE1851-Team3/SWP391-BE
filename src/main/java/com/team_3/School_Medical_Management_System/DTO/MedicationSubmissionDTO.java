package com.team_3.School_Medical_Management_System.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@NoArgsConstructor
@Getter
@Setter
public class MedicationSubmissionDTO {
    private int parentId;
    private int studentId;

    @NotBlank(message = "Medication name cannot be empty")
    private String medicationName;

    @NotBlank(message = "Dosage cannot be empty")
    private String dosage;

    @NotBlank(message = "Frequency Per Day cannot be empty")
    private int frequencyPerDay;

    @NotBlank(message = "Duration Days cannot be empty")
    private String durationDays;

    @NotBlank(message = "Start Date cannot be empty")
    private Date startDate;

    @NotBlank(message = "End Date cannot be empty")
    private Date endDate;



    private String notes;
}
