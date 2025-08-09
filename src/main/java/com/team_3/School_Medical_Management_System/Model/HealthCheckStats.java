package com.team_3.School_Medical_Management_System.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthCheckStats {
    private Long totalSchedules;
    private Long completedSchedules;
    private Long countRejected;
    private Double consentRate;
    private Double averageBMI;
}
