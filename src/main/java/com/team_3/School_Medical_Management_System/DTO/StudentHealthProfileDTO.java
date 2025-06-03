package com.team_3.School_Medical_Management_System.DTO;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class StudentHealthProfileDTO {
    @NotBlank(message = "Full name is required")
    private String fullName;
    @NotBlank(message = "AllergyDetails Not allow empty")
    private String AllergyDetails;
    @NotBlank(message = "ChronicDiseases Not allow empty")
    private String ChronicDiseases;
    @NotBlank(message = "TreatmentHistory Not allow empty")
    private String TreatmentHistory;
    @NotBlank(message = "VisionLeft Not allow empty")
    private String VisionLeft;
    @NotBlank(message = "VisionRight Not allow empty")
    private String VisionRight;
    @NotBlank(message = "Hearings_Score Not allow empty")
    private String Hearings_Score;
    @NotNull(message = "Height is required")
    private double Height;
    @NotNull(message = "Weight is required")
    private double Weight;
    @NotBlank(message = "NoteOfParent Not allow empty")
    private String NoteOfParent;


}
