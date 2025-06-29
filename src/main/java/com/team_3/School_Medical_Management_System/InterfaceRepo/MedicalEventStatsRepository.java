package com.team_3.School_Medical_Management_System.InterfaceRepo;

import java.util.Date;

public interface MedicalEventStatsRepository {
     public Long countTotalEvents();
     public Long countEmergencyEvents();
     public Long countCompletedEvents();
     public Long countPendingEvents();
     public Double calculateNotificationRate();
     public Date getDate();
}
