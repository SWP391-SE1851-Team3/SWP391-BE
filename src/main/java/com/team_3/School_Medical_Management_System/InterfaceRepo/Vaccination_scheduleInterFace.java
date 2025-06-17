package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Vaccination_schedule;

import java.util.List;

public interface Vaccination_scheduleInterFace {
    public Vaccination_schedule vaccination_scheduleById(int id);
    public List<Vaccination_schedule> vaccination_schedules();
    public Vaccination_schedule addVaccination_schedule(Vaccination_schedule vaccination_schedule);
    public Vaccination_schedule updateVaccination_schedule(Vaccination_schedule vaccination_schedule);
}

