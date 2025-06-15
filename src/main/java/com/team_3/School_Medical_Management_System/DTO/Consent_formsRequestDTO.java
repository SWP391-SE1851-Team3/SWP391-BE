package com.team_3.School_Medical_Management_System.DTO;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Consent_formsRequestDTO {
    private String fullNameOfParent;
    private String fullNameOfStudent;
    private String className;
    private Date scheduledDate;
    private String vaccineName;
    private String hasAllergy;
    private String reason;
    private int isAgree;
}

