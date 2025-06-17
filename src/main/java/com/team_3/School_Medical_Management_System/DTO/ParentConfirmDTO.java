package com.team_3.School_Medical_Management_System.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ParentConfirmDTO {
    private int consentFormId;
    private int isAgree;
    private String reason;
    private String HasAllergy;
}

