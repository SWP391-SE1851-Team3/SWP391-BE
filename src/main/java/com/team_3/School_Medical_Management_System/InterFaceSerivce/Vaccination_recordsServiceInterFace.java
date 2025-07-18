package com.team_3.School_Medical_Management_System.InterFaceSerivce;

import com.team_3.School_Medical_Management_System.DTO.*;

import java.util.List;

public interface Vaccination_recordsServiceInterFace {
    public List<Vaccination_recordsDTO> getVaccination_records();
    public Vaccination_recordsDTO addVaccination_records(Vaccination_recordsDTO vaccinationRecordsDTO);
    public void deleteVaccination_records(int id);
    public Vaccination_recordsDTO getVaccination_records_by_id(int id);
    public Vaccination_records_edit_DTO updateVaccination_records(Vaccination_records_edit_DTO vaccination_records_edit_DTO);
    List<Vaccination_recordsDTO> getVaccination_recordsByStudentId(int studentId);
    public Vaccination_records_SentParent_DTO createEmail(Vaccination_records_SentParent_DTO dto);
    public Vaccination_records_SentParent_Edit_DTO updateAndResendEmail(Integer recordId, Vaccination_records_SentParent_Edit_DTO dto);
    public List<StudentVaccinationDTO> getStudentFollowedbyNurse();
    public StudentVaccinationDTO updateStudentFollowedbyNurse(StudentVaccinationDTO studentVaccinationDTO);

}
