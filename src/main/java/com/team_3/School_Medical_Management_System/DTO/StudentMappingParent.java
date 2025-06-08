package com.team_3.School_Medical_Management_System.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class StudentMappingParent {
    private int StudentID;
    private int Gender;
    @NotBlank(message = "FullName Not allow empty")
    private String FullName;
    @NotBlank(message = "ClassName Not allow empty")
    private String ClassName;
    private int IsActive;
    private int ParentID;
}
