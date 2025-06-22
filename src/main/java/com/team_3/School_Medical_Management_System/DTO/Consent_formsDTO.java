package com.team_3.School_Medical_Management_System.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Consent_formsDTO {
    private Integer consent_id;
    private Integer StudentId;
    private Integer VaccineBatchId;
    private String IsAgree;
    private String Reason;
    private String HasAllergy;
    @NotBlank(message = "notes Not allow empty")
    private String notes;
    private Integer ParentID;
    private LocalDateTime send_date;
    private LocalDateTime expire_date;
    private String status;
}


