package com.team_3.School_Medical_Management_System.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicationStats {
    private Long totalSubmissions;
    private Long approvedSubmissions;
    private Long rejectedSubmissions;
    private Double approvalRate;
}
