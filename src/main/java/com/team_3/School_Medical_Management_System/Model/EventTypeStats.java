package com.team_3.School_Medical_Management_System.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventTypeStats {
    private String eventTypeName;
    private Long count;
    private Double percentage;
}
