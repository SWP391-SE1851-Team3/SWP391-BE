package com.team_3.School_Medical_Management_System.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class SystemStats {
    private Long totalStudents;
    private Long activeStudents;
    private Long totalParents;
    private Long totalNurses;
    private Long totalManagers;
}
