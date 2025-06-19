package com.team_3.School_Medical_Management_System.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ConsentFormParentResponseDTO {

    private Integer consentFormId;

    private Integer isAgree;

    @NotBlank
    private String reason;

    @NotBlank
    private String hasAllergy;
}
