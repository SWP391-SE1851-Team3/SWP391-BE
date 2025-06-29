package com.team_3.School_Medical_Management_System.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalEventStats {
    private Long totalEvents;
    private Long emergencyEvents;
    private Long completedEvents;
    private Long pendingEvents;
    private Double notificationRate;
    private Date createdDay;

}
