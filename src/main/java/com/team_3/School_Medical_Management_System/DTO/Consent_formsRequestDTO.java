package com.team_3.School_Medical_Management_System.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Consent_formsRequestDTO {
    private int consent_forms_id;
    private String fullNameOfParent;
    private String fullNameOfStudent;
    private String className;
    private LocalDateTime scheduledDate;
    private String vaccineName;
    private String hasAllergy;
    private String reason;
    private int isAgree;
    private String location;
    private LocalDateTime send_date;
    private LocalDateTime expire_date;
}

