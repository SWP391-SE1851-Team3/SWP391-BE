package com.team_3.School_Medical_Management_System.InterfaceRepo;

public interface SystemStatsRepository {
    public Long countStudents();
    public Long countActiveStudents();
    public Long countParents();
    public Long countNurses();
    public Long countManagers();
}
