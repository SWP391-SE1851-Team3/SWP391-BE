package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.Model.*;

import java.time.LocalDateTime;

public interface DashboardService {
    DashboardReportModel getFullReport(LocalDateTime startDate, LocalDateTime endDate);
    SystemStats getSystemStats();
    MedicalEventStats getMedicalEventStats(LocalDateTime startDate, LocalDateTime endDate);
    VaccinationStats getVaccinationStats(LocalDateTime startDate, LocalDateTime endDate);
    HealthCheckStats getHealthCheckStats(LocalDateTime startDate, LocalDateTime endDate);
    MedicationStats getMedicationStats(LocalDateTime startDate, LocalDateTime endDate);

}
