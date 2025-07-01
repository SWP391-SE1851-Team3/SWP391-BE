package com.team_3.School_Medical_Management_System.InterfaceRepo;

import java.time.LocalDateTime;

public interface MedicationStatsRepository {
    public Long countTotalSubmissions(LocalDateTime startDate, LocalDateTime endDate);

    public Long countApprovedSubmissions(LocalDateTime startDate, LocalDateTime endDate);

    public Long countRejectedSubmissions(LocalDateTime startDate, LocalDateTime endDate);

    public Double calculateApprovalRate(LocalDateTime startDate, LocalDateTime endDate);
}
