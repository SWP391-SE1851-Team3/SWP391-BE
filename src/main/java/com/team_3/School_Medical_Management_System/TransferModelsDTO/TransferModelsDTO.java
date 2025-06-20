package com.team_3.School_Medical_Management_System.TransferModelsDTO;

import com.team_3.School_Medical_Management_System.DTO.*;
import com.team_3.School_Medical_Management_System.Model.*;
import org.jetbrains.annotations.Async;

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
        vaccinesDTO.setVaccine_created_at(vaccines.getVaccine_created_at());
        vaccinesDTO.setVaccine_updated_at(vaccines.getVaccine_updated_at());
        vaccinesDTO.setRecommended_ages(vaccines.getRecommended_ages());
        vaccinesDTO.setDot(vaccines.getDot());
        vaccinesDTO.setNurse_id(vaccines.getNurse().getNurseID());
        vaccinesDTO.setLocation(vaccines.getLocation());
        vaccinesDTO.setStatus(vaccines.getStatus());
        vaccinesDTO.setNotes(vaccines.getNotes());
        vaccinesDTO.setScheduled_date(vaccines.getScheduled_date());
        vaccinesDTO.setQuantity_received(vaccines.getQuantity_received());
        return vaccinesDTO;
    }


    public static Vaccines MappingVaccineDTO(VaccinesDTO vaccinesDTO) {
        Vaccines vaccines = new Vaccines();
        vaccines.setVaccine_id(vaccinesDTO.getVaccine_id());
        vaccines.setName(vaccinesDTO.getName());
        vaccines.setDescription(vaccinesDTO.getDescription());
        vaccines.setManufacturer(vaccinesDTO.getManufacturer());
        vaccines.setRecommended_ages(vaccinesDTO.getRecommended_ages());
        vaccines.setVaccine_created_at(vaccinesDTO.getVaccine_created_at());
        vaccines.setVaccine_updated_at(vaccinesDTO.getVaccine_updated_at());
        vaccines.setDot(vaccinesDTO.getDot());
        vaccines.setQuantity_received(vaccinesDTO.getQuantity_received());
        vaccines.setScheduled_date(vaccinesDTO.getScheduled_date());
        vaccines.setLocation(vaccinesDTO.getLocation());
        vaccines.setStatus(vaccinesDTO.getStatus());
        vaccines.setNotes(vaccinesDTO.getNotes());
        SchoolNurse nurse = new SchoolNurse();
        nurse.setNurseID(vaccinesDTO.getNurse_id()); // <- Gán ID duy nhất
        vaccines.setNurse(nurse);
        vaccines.setScheduled_date(vaccinesDTO.getScheduled_date());
        vaccines.setQuantity_received(vaccinesDTO.getQuantity_received());
        return vaccines;
    }

    public static Consent_formsDTO MappingConsent(Consent_forms consent_forms) {
        Consent_formsDTO consent_formsDTO = new Consent_formsDTO();
        consent_formsDTO.setFullNameOfStudent(consent_forms.getStudent().getFullName());
        consent_formsDTO.setClassName(consent_forms.getStudent().getClassName());
        consent_formsDTO.setReason(consent_forms.getReason());
        consent_formsDTO.setName(consent_forms.getVaccine().getName());
        consent_formsDTO.setHasAllergy(consent_forms.getHasAllergy());
        consent_formsDTO.setIsAgree(consent_forms.getIsAgree());
        consent_formsDTO.setFullnameOfParent(consent_forms.getParent().getFullName());
        consent_formsDTO.setSend_date(consent_forms.getSend_date());
        consent_formsDTO.setExpire_date(consent_forms.getExpire_date());
        consent_formsDTO.setConsent_forms_id(consent_forms.getConsent_id());
        consent_formsDTO.setScheduledDate(consent_forms.getVaccine().getScheduled_date());
        consent_formsDTO.setLocation(consent_forms.getVaccine().getLocation());
        return consent_formsDTO;
    }

    public static Vaccination_recordsDTO MappingVaccinationRecords(Vaccination_records vaccination_records) {
        Vaccination_recordsDTO Vaccination_recordsDTO = new Vaccination_recordsDTO();
        Vaccination_recordsDTO.setVaccinationRecordID(vaccination_records.getVaccinationRecordID());
        Vaccination_recordsDTO.setSeverity(vaccination_records.getSeverity());
        Vaccination_recordsDTO.setObservation_notes(vaccination_records.getObservation_notes());
        Vaccination_recordsDTO.setVaccine_Name(vaccination_records.getVaccines().getName());
        Vaccination_recordsDTO.setObservation_time(vaccination_records.getObservation_time());
        Vaccination_recordsDTO.setSymptoms(vaccination_records.getSymptoms());
        Vaccination_recordsDTO.setNurse_Name(vaccination_records.getNurse().getFullName());
        Vaccination_recordsDTO.setNotes(vaccination_records.getNotes());
        Vaccination_recordsDTO.setStudent_Name(vaccination_records.getStudent().getFullName());
        return Vaccination_recordsDTO;

    }








}
