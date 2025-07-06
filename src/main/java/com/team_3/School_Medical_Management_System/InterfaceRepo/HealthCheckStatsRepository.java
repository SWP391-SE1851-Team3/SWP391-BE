package com.team_3.School_Medical_Management_System.InterfaceRepo;

import java.time.LocalDateTime;

public interface HealthCheckStatsRepository {
    public Long countTotalSchedules(LocalDateTime startDate, LocalDateTime endDate);
    public Long countCompletedSchedules(LocalDateTime startDate, LocalDateTime endDate);
    public Long countTotalChecked(LocalDateTime startDate, LocalDateTime endDate);
    public Double calculateConsentRate(LocalDateTime startDate, LocalDateTime endDate);
    public Double calculateAverageBMI(LocalDateTime startDate, LocalDateTime endDate);
}
