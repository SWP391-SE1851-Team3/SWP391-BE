package com.team_3.School_Medical_Management_System.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;


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
    private LocalDateTime Vaccine_created_at;
    private LocalDateTime Vaccine_updated_at;
    private Integer nurse_id;
    private String dot;
    private Integer quantity_received;
    private LocalDateTime scheduled_date;
    private String location;
    private String status;
    private String notes;
}
