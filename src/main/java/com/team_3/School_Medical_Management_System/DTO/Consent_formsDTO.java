package com.team_3.School_Medical_Management_System.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Consent_formsDTO {
    @JsonIgnore
    private int consent_form_id;
    @NotBlank(message = "FullName Not allow empty")
    private String FullNameOfStudent ;
    @NotBlank(message = "ClassName Not allow empty")
    private String ClassName ;
    private int scheduled_id;
    @NotBlank(message = "VaccineName Not allow empty")
    private String Name;
    private int IsAgree;
    @NotBlank(message = "Reason Not allow empty")
    private String Reason;
    @NotBlank(message = "HasAllergy Not allow empty")
    private String HasAllergy;
    @NotBlank(message = "notes Not allow empty")
    private String notes;
    @NotBlank(message = "fullnameOfParent Not allow empty")
    private String fullnameOfParent;
}


