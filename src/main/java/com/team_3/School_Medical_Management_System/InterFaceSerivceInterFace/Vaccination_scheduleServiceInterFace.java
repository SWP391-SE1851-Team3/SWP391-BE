package com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace;

import com.team_3.School_Medical_Management_System.DTO.Vaccination_scheduleDTO;
import com.team_3.School_Medical_Management_System.Model.Vaccination_schedule;

import java.util.List;

public interface Vaccination_scheduleServiceInterFace {
    public Vaccination_scheduleDTO vaccination_scheduleById(int id);
    public List<Vaccination_scheduleDTO> vaccination_schedules();
    public Vaccination_scheduleDTO addVaccination_schedule(Vaccination_scheduleDTO vaccination_scheduleDTO);
    public Vaccination_scheduleDTO updateVaccination_schedule(Vaccination_scheduleDTO vaccination_scheduleDTO);
}
