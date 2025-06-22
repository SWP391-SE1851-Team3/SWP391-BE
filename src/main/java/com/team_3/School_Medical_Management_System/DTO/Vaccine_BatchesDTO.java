package com.team_3.School_Medical_Management_System.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Vaccine_BatchesDTO {
    private Integer BatchID;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Integer nurse_id;
    private String dot;
    private Integer quantity_received;
    private LocalDateTime scheduled_date;
    private String location;
    private String status;
    private String notes;
    private Integer VaccineTypeID;
    private String nurse_name;
}
