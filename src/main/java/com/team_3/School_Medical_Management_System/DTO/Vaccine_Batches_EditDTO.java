package com.team_3.School_Medical_Management_System.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Vaccine_Batches_EditDTO {
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Integer edit_nurse_id;
    private String dot;
    private LocalDateTime scheduled_date;
    private String location;
    private String status;
    private String notes;
    private Integer VaccineTypeID;
    private Integer BatchID;
    private String edit_nurse_name;
}
