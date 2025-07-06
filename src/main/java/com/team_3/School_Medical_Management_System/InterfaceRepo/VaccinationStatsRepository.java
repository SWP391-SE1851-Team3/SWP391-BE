package com.team_3.School_Medical_Management_System.InterfaceRepo;

import java.time.LocalDateTime;

public interface VaccinationStatsRepository {
    public Long countTotalBatches(LocalDateTime startDate, LocalDateTime endDate);
    public Long countCompletedBatches(LocalDateTime startDate, LocalDateTime endDate);
    public Long countTotalVaccinated(LocalDateTime startDate, LocalDateTime endDate);
    public Double calculateConsentRate(LocalDateTime startDate, LocalDateTime endDate);
    public Long countTotalReactions(LocalDateTime startDate, LocalDateTime endDate);
}
