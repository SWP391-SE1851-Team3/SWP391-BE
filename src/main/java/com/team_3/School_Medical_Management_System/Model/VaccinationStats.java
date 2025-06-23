package com.team_3.School_Medical_Management_System.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VaccinationStats {
    private Long totalBatches;
    private Long completedBatches;
    private Long totalVaccinated;
    private Double consentRate;
    private Long totalReactions;
}
