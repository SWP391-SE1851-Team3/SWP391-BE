package com.team_3.School_Medical_Management_System.InterfaceRepo;

import java.time.LocalDateTime;

public interface MedicationStatsRepository {
    public Long countTotalSubmissions(LocalDateTime startDate, LocalDateTime endDate);

    public Long countApprovedSubmissions();

    public Long countRejectedSubmissions();

    public Double calculateApprovalRate();
}
