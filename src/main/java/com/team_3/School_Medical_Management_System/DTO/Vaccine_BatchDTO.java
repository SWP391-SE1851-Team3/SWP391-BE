package com.team_3.School_Medical_Management_System.DTO;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine_BatchDTO {
    private Integer BatchID;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String dot;
    private LocalDateTime scheduled_date;
    private String location;
    private String status;
    private String notes;
    private Integer VaccineTypeID;
    private String edit_nurse_name;
    private Integer created_by_nurse_id;
    private String created_by_nurse_name;
    private Integer edit_nurse_id;
    private Long countAgreeConsentForms;    
}
