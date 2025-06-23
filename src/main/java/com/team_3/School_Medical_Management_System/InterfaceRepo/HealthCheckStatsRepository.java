package com.team_3.School_Medical_Management_System.InterfaceRepo;

public interface HealthCheckStatsRepository {
    public Long countTotalSchedules();
    public Long countCompletedSchedules();
    public Long countTotalChecked();
    public Double calculateConsentRate();
    public Double calculateAverageBMI();
}
