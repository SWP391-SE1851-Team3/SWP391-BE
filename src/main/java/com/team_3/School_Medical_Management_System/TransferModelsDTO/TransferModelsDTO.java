package com.team_3.School_Medical_Management_System.TransferModelsDTO;

import com.team_3.School_Medical_Management_System.DTO.Consent_formsDTO;
import com.team_3.School_Medical_Management_System.DTO.StudentMappingParent;
import com.team_3.School_Medical_Management_System.DTO.Vaccination_scheduleDTO;
import com.team_3.School_Medical_Management_System.DTO.VaccinesDTO;
import com.team_3.School_Medical_Management_System.Model.Consent_forms;
import com.team_3.School_Medical_Management_System.Model.Student;
import com.team_3.School_Medical_Management_System.Model.Vaccination_schedule;
import com.team_3.School_Medical_Management_System.Model.Vaccines;

public class TransferModelsDTO {

    public static StudentMappingParent MappingStudent(Student student) {
        StudentMappingParent studentMappingParent = new StudentMappingParent();
        studentMappingParent.setStudentID(student.getStudentID());
        studentMappingParent.setParentID(student.getParent().getParentID());
        studentMappingParent.setFullName(student.getFullName());
        studentMappingParent.setClassName(student.getClassName());
        studentMappingParent.setGender(student.getGender());
        studentMappingParent.setIsActive(student.getIsActive());
        return studentMappingParent;
    }

    public static VaccinesDTO MappingVaccines(Vaccines vaccines) {
        VaccinesDTO vaccinesDTO = new VaccinesDTO();
        vaccinesDTO.setVaccine_id(vaccines.getVaccine_id());
        vaccinesDTO.setName(vaccines.getName());
        vaccinesDTO.setDescription(vaccines.getDescription());
        vaccinesDTO.setManufacturer(vaccines.getManufacturer());
        vaccinesDTO.setCreated_at(vaccines.getCreated_at());
        vaccinesDTO.setUpdated_at(vaccines.getUpdated_at());
        vaccinesDTO.setDoses_required(vaccines.getDoses_required());
        vaccinesDTO.setRecommended_ages(vaccines.getRecommended_ages());
        return vaccinesDTO;
    }

    public static Consent_formsDTO MappingConsent(Consent_forms consent_forms) {
        Consent_formsDTO consent_formsDTO = new Consent_formsDTO();
        consent_formsDTO.setFullNameOfStudent(consent_forms.getStudent().getFullName());
        consent_formsDTO.setClassName(consent_forms.getStudent().getClassName());
        consent_formsDTO.setReason(consent_forms.getReason());
        consent_formsDTO.setName(consent_forms.getVaccine().getName());
        consent_formsDTO.setHasAllergy(consent_forms.getHasAllergy());
        consent_formsDTO.setIsAgree(consent_forms.getIsAgree());
        consent_formsDTO.setNotes(consent_forms.getSchedule().getNotes());
        consent_formsDTO.setFullnameOfParent(consent_forms.getParent().getFullName());
        consent_formsDTO.setScheduled_date(consent_forms.getSchedule().getScheduled_date());
        return consent_formsDTO;
    }

    public static Vaccination_scheduleDTO MappingVaccinationSchedule(Vaccination_schedule vaccination_schedule) {
        Vaccination_scheduleDTO entity = new Vaccination_scheduleDTO();
        entity.setBatch_number(vaccination_schedule.getBatch_number());
        entity.setLocation(vaccination_schedule.getLocation());
        entity.setStatus(vaccination_schedule.getStatus());
        entity.setNotes(vaccination_schedule.getNotes());
        entity.setScheduled_date(vaccination_schedule.getScheduled_date());
        entity.setReceived_date(vaccination_schedule.getReceived_date());
        entity.setFullName(vaccination_schedule.getNurse().getFullName());
        entity.setName(vaccination_schedule.getVaccine().getName());
        entity.setSchedule_id(vaccination_schedule.getSchedule_id());
        return entity;
    }










}
