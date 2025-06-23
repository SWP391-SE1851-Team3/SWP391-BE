package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.Model.*;

public interface DashboardService {
    DashboardReportModel getFullReport();
    SystemStats getSystemStats();
    MedicalEventStats getMedicalEventStats();
    VaccinationStats getVaccinationStats();
    HealthCheckStats getHealthCheckStats();
    MedicationStats getMedicationStats();
}
