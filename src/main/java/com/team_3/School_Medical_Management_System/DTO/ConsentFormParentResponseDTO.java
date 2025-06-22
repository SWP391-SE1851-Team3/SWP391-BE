package com.team_3.School_Medical_Management_System.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ConsentFormParentResponseDTO {
    @JsonIgnore
    private Integer consentFormId;
    private String isAgree;
    @NotBlank
    private String reason;
    @NotBlank
    private String hasAllergy;
    @JsonIgnore
    private String status;
}
