package com.team_3.School_Medical_Management_System.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SchoolNurseDTO {
    @Pattern(regexp = "^(84|0)(3|5|7|8|9)[0-9]{8}$", message = "Phone invalid")
    private String Phone;
    @Email
    private String Email;
    @NotBlank(message = "NotBlank Not allow empty")
    private String Certification;
    @NotBlank(message = "Specialisation Not allow empty")
    private String Specialisation;
}
