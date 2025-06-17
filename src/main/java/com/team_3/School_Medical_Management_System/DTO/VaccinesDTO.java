package com.team_3.School_Medical_Management_System.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class VaccinesDTO {
    private int Vaccine_id;
    @NotBlank(message = "Name Not allow empty")
    private String Name;
    @NotBlank(message = "Manufacturer Not allow empty")
    private String Manufacturer;
    @NotBlank(message = "Description Not allow empty")
    private String Description;
    @NotBlank(message = "Recommended_ages Not allow empty")
    private String Recommended_ages;
    private int Doses_required;
    @NotBlank(message = "Created_at Not allow empty")
    private String Created_at;
    @NotBlank(message = "Updated_at Not allow empty")
    private String Updated_at;

}
