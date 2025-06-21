package com.team_3.School_Medical_Management_System.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StudentDTO {
    @NotBlank(message = "ClassName Not allow empty")
    private String ClassName;




}
