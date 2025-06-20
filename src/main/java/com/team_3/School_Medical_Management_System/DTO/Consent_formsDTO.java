package com.team_3.School_Medical_Management_System.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.team_3.School_Medical_Management_System.Enum.ConsentFormStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Consent_formsDTO {
    private int consent_forms_id;
    @NotBlank(message = "FullName Not allow empty")
    private String FullNameOfStudent ;
    @NotBlank(message = "ClassName Not allow empty")
    private String ClassName ;
    @NotBlank(message = "VaccineName Not allow empty")
    private String Name;
    private Integer IsAgree;
    @NotBlank(message = "Reason Not allow empty")
    private String Reason;
    @NotBlank(message = "HasAllergy Not allow empty")
    private String HasAllergy;
    @NotBlank(message = "notes Not allow empty")
    private String notes;
    @NotBlank(message = "fullnameOfParent Not allow empty")
    private String fullnameOfParent;
    private String Location;
    private LocalDateTime scheduledDate;
    private LocalDateTime send_date;
    private LocalDateTime expire_date;
}


