package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.StudentHealthProfile;
import com.team_3.School_Medical_Management_System.Model.Vaccination_records;

import java.util.List;

public interface Vaccination_recordsInterFace {
    public List<Vaccination_records> getVaccination_records();
    public Vaccination_records addVaccination_records(Vaccination_records vaccination_records);
    public void deleteVaccination_records(int id);
    public Vaccination_records getVaccination_records_by_id(int id);
    public Vaccination_records updateVaccination_records(Vaccination_records vaccination_records);
    List<Vaccination_records> getVaccination_recordsByStudentId(int studentId);

}
