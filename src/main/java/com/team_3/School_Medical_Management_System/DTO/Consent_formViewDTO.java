package com.team_3.School_Medical_Management_System.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Consent_formViewDTO {
    private int consent_id;
    private String className;
    private String FullNameOfStudent;
    private String FullNameOfParent;
    private String VaccineName;
    private String location;
    private String IsAgree;
    private String Reason;
    private String HasAllergy;
    private LocalDateTime scheduledDate;
    private LocalDateTime send_date;
    private LocalDateTime expire_date;
    private String status;
    private Integer bacthID;
    private String dot;



}
