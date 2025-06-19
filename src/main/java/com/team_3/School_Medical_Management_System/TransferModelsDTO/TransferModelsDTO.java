package com.team_3.School_Medical_Management_System.TransferModelsDTO;

import com.team_3.School_Medical_Management_System.DTO.*;
import com.team_3.School_Medical_Management_System.Model.*;

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
        consent_formsDTO.setScheduled_id(consent_forms.getSchedule().getSchedule_id());
        consent_formsDTO.setLocation(consent_forms.getSchedule().getLocation());
        consent_formsDTO.setScheduledDate(consent_forms.getSchedule().getScheduled_date());
        consent_formsDTO.setSend_date(consent_forms.getSend_date());
        consent_formsDTO.setExpire_date(consent_forms.getExpire_date());
        consent_formsDTO.setConsent_forms_id(consent_forms.getConsent_id());
        return consent_formsDTO;
    }

    public static Vaccination_scheduleDTO MappingVaccinationSchedule(Vaccination_schedule vaccination_schedule) {
        Vaccination_scheduleDTO entity = new Vaccination_scheduleDTO();
        entity.setLocation(vaccination_schedule.getLocation());
        entity.setStatus(vaccination_schedule.getStatus());
        entity.setNotes(vaccination_schedule.getNotes());
        entity.setScheduled_date(vaccination_schedule.getScheduled_date());
        entity.setFullName(vaccination_schedule.getNurse().getFullName());
        entity.setName(vaccination_schedule.getVaccine().getName());
        entity.setSchedule_id(vaccination_schedule.getSchedule_id());
        return entity;
    }

    public static Vaccine_batchesDTO MappingVaccineBatchDTO(Vaccine_batches vaccine_batches) {
        Vaccine_batchesDTO entity = new Vaccine_batchesDTO();
        entity.setBatch_id(vaccine_batches.getBatch_id());
        entity.setBatch_number(vaccine_batches.getBatch_number());
        entity.setVaccine_id(vaccine_batches.getVaccine().getVaccine_id());
        entity.setQuantity_received(vaccine_batches.getQuantity_received());
        entity.setReceived_date(vaccine_batches.getReceived_date());
        return entity;
    }

    public static Vaccine_batches MappingVaccineBatch(Vaccine_batchesDTO vaccine_batchesDTO) {
        Vaccine_batches entity = new Vaccine_batches();
        Vaccines vaccines = new Vaccines();
        entity.setBatch_id(vaccine_batchesDTO.getBatch_id());
        entity.setBatch_number(vaccine_batchesDTO.getBatch_number());
        vaccines.setVaccine_id(vaccine_batchesDTO.getVaccine_id());
        entity.setVaccine(vaccines);
        entity.setQuantity_received(vaccine_batchesDTO.getQuantity_received());
        entity.setReceived_date(vaccine_batchesDTO.getReceived_date());
        return entity;

    }

    public static Vaccination_recordsDTO MappingVaccinationRecordsDTO(Vaccination_records vaccination_records) {
        Vaccination_recordsDTO entity = new Vaccination_recordsDTO();
        entity.setVaccinationRecordID(vaccination_records.getVaccinationRecordID());
        entity.setStudent_Name(vaccination_records.getStudent().getFullName());
        entity.setBatch_id(vaccination_records.getBatch().getBatch_id());
        entity.setVaccine_Name(vaccination_records.getVaccines().getName());
        entity.setNotes(vaccination_records.getNotes());
        entity.setSchedule_id(vaccination_records.getSchedule().getSchedule_id());
        entity.setNurse_Name(vaccination_records.getNurse().getFullName());
        entity.setObservation_notes(vaccination_records.getObservation_notes());
        entity.setObservation_time(vaccination_records.getObservation_time());
        entity.setSeverity(vaccination_records.getSeverity());
        entity.setSymptoms(vaccination_records.getSymptoms());
        return entity;
    }

    public static Consent_formsRequestDTO  convertToParentViewDTO(Consent_forms entity) {
        Consent_formsRequestDTO dto = new Consent_formsRequestDTO();
        dto.setScheduledDate(entity.getSchedule().getScheduled_date());
        dto.setFullNameOfParent(entity.getParent().getFullName());
        dto.setFullNameOfStudent(entity.getStudent().getFullName());
        dto.setClassName(entity.getStudent().getClassName());
        dto.setConsent_forms_id(entity.getConsent_id());
        dto.setVaccineName(entity.getVaccine().getName());
        dto.setHasAllergy(entity.getHasAllergy());
        dto.setReason(entity.getReason());
        dto.setIsAgree(entity.getIsAgree());
        dto.setLocation(entity.getSchedule().getLocation());
        dto.setExpire_date(entity.getExpire_date());
        dto.setSend_date(entity.getSend_date());
        return dto;
    }
}
