package com.team_3.School_Medical_Management_System.InterfaceRepo;

public interface MedicationStatsRepository {
    public Long countTotalSubmissions();

    public Long countApprovedSubmissions();

    public Long countRejectedSubmissions();

    public Double calculateApprovalRate();
}
