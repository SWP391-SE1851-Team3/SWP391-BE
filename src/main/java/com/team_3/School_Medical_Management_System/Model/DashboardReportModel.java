package com.team_3.School_Medical_Management_System.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardReportModel {
    private SystemStats systemStats;

    // 4 luồng chính
    private MedicalEventStats medicalEventStats;
    private VaccinationStats vaccinationStats;
    private HealthCheckStats healthCheckStats;
    private MedicationStats medicationStats;

    private LocalDateTime createdAt;

}
