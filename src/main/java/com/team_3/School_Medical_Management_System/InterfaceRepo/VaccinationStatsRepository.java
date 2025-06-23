package com.team_3.School_Medical_Management_System.InterfaceRepo;

public interface VaccinationStatsRepository {
    public Long countTotalBatches();
    public Long countCompletedBatches();
    public Long countTotalVaccinated();
    public Double calculateConsentRate();
    public Long countTotalReactions();
}
