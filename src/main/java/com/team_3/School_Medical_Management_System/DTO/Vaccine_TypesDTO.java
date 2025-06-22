package com.team_3.School_Medical_Management_System.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine_TypesDTO {
    private Integer VaccineTypeID;
    private String Name;
    private String Manufacturer;
    private String Description;
    private String Recommended_ages;
    private LocalDate Created_at;
    private LocalDate Updated_at;
}
