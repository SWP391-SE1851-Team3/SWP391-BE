package com.team_3.School_Medical_Management_System.DTO;

import lombok.*;
import org.springframework.data.relational.core.sql.In;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdministerVaccineRequest {
    private Integer vaccineId;
    private Integer DoseCount;
}
