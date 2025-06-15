package com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace;

import com.team_3.School_Medical_Management_System.DTO.Vaccination_recordsDTO;

import java.util.List;

public interface Vaccination_recordsServiceInterFace {
    public List<Vaccination_recordsDTO> getVaccination_records();
    public Vaccination_recordsDTO addVaccination_records(Vaccination_recordsDTO vaccinationRecordsDTO);
    public void deleteVaccination_records(int id);
    public Vaccination_recordsDTO getVaccination_records_by_id(int id);
    public Vaccination_recordsDTO updateVaccination_records(Vaccination_recordsDTO vaccinationRecordsDTO);
    List<Vaccination_recordsDTO> getVaccination_recordsByStudentId(int studentId);
}
