package com.team_3.School_Medical_Management_System.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Vaccine_Types_Edit_DTO {
    private String Name;
    private String Manufacturer;
    private String Description;
    private String Recommended_ages;
    private LocalDate Created_at;
    private LocalDate Updated_at;
    @JsonIgnore
    private Integer Vaccine_TypeId;
}
