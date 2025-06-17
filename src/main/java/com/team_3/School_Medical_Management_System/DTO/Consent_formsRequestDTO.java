package com.team_3.School_Medical_Management_System.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Consent_formsRequestDTO {
    private int Student_ID;
    private int Patient_ID;
    private int Vaccine_ID;
    private int scheduleId;
    private int isAgree;
    private String hasAllergy;
    private String reason;
}

