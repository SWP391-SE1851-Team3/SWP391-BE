package com.team_3.School_Medical_Management_System.InterfaceRepo;

import java.time.LocalDateTime;
import java.util.Date;

public interface MedicalEventStatsRepository {
     public Long countTotalEvents(LocalDateTime startDate, LocalDateTime endDate);
     public Long countEmergencyEvents(LocalDateTime startDate, LocalDateTime endDate);
     public Long countCompletedEvents(LocalDateTime startDate, LocalDateTime endDate);
     public Long countPendingEvents(LocalDateTime startDate, LocalDateTime endDate);
     public Double calculateNotificationRate(LocalDateTime startDate, LocalDateTime endDate);

}
