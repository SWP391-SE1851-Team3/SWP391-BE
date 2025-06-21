package com.team_3.School_Medical_Management_System.TransferModelsDTO;

import com.team_3.School_Medical_Management_System.DTO.*;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_BatchesDTO;
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

    public static Vaccine_BatchesDTO MappingVaccines(com.team_3.School_Medical_Management_System.Model.Vaccine_Batches vaccines) {
        Vaccine_BatchesDTO vaccinesDTO = new Vaccine_BatchesDTO();
        vaccinesDTO.setDot(vaccines.getDot());
        vaccinesDTO.setNurse_id(vaccines.getNurse().getNurseID());
        vaccinesDTO.setLocation(vaccines.getLocation());
        vaccinesDTO.setStatus(vaccines.getStatus());
        vaccinesDTO.setNotes(vaccines.getNotes());
        vaccinesDTO.setScheduled_date(vaccines.getScheduled_date());
        vaccinesDTO.setQuantity_received(vaccines.getQuantity_received());
        vaccinesDTO.setVaccineTypeID(vaccines.getVaccineType().getVaccineTypeID());
        vaccinesDTO.setCreated_at(vaccines.getCreated_at());
        vaccinesDTO.setUpdated_at(vaccines.getUpdated_at());
        vaccinesDTO.setNurse_name(vaccines.getNurse().getFullName());
        return vaccinesDTO;
    }


    public static Vaccine_Batches MappingVaccineDTO(Vaccine_BatchesDTO vaccinesDTO) {
        Vaccine_Batches vaccines = new com.team_3.School_Medical_Management_System.Model.Vaccine_Batches();
        Vaccine_Types vaccineType = new Vaccine_Types();
        vaccineType.setVaccineTypeID(vaccinesDTO.getVaccineTypeID());
        vaccines.setVaccineType(vaccineType);
        vaccines.setCreated_at(vaccinesDTO.getCreated_at());
        vaccines.setUpdated_at(vaccinesDTO.getUpdated_at());
        vaccines.setDot(vaccinesDTO.getDot());
        vaccines.setQuantity_received(vaccinesDTO.getQuantity_received());
        vaccines.setScheduled_date(vaccinesDTO.getScheduled_date());
        vaccines.setLocation(vaccinesDTO.getLocation());
        vaccines.setStatus(vaccinesDTO.getStatus());
        vaccines.setNotes(vaccinesDTO.getNotes());
        SchoolNurse nurse = new SchoolNurse();
        nurse.setNurseID(vaccinesDTO.getNurse_id());
        nurse.setFullName(vaccinesDTO.getNurse_name());// <- Gán ID duy nhất
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
        consent_formsDTO.setName(consent_forms.getVaccineBatches().getVaccineType().getName());
        consent_formsDTO.setHasAllergy(consent_forms.getHasAllergy());
        consent_formsDTO.setIsAgree(consent_forms.getIsAgree());
        consent_formsDTO.setFullnameOfParent(consent_forms.getParent().getFullName());
        consent_formsDTO.setSend_date(consent_forms.getSend_date());
        consent_formsDTO.setExpire_date(consent_forms.getExpire_date());
        consent_formsDTO.setConsent_forms_id(consent_forms.getConsent_id());
        consent_formsDTO.setScheduledDate(consent_forms.getVaccineBatches().getScheduled_date());
        consent_formsDTO.setLocation(consent_forms.getVaccineBatches().getLocation());
        return consent_formsDTO;
    }

    public static Consent_forms MappingConsentDTO(Consent_formsDTO dto) {
        Consent_forms entity = new Consent_forms();
        entity.setConsent_id(dto.getConsent_forms_id());
        entity.setReason(dto.getReason());
        entity.setExpire_date(dto.getExpire_date());
        entity.setSend_date(dto.getSend_date());

        // Mapping Parent
        Parent parent = new Parent();
        parent.setFullName(dto.getFullNameOfStudent());
        entity.setParent(parent);

        // Mapping Vaccine Type
        Vaccine_Types vaccineType = new Vaccine_Types();
        vaccineType.setName(dto.getName());

        // Mapping Vaccine Batch
        Vaccine_Batches vaccineBatches = new Vaccine_Batches();
        vaccineBatches.setScheduled_date(dto.getScheduledDate()); // từ DTO
        vaccineBatches.setVaccineType(vaccineType);
        entity.setVaccineBatches(vaccineBatches);

        return entity;
    }

    public static Vaccination_recordsDTO MappingVaccinationRecords(Vaccination_records vaccination_records) {
        Vaccination_recordsDTO dto = new Vaccination_recordsDTO();
        dto.setSeverity(vaccination_records.getSeverity());
        dto.setObservation_notes(vaccination_records.getObservation_notes());
        dto.setObservation_time(vaccination_records.getObservation_time());
        dto.setSymptoms(vaccination_records.getSymptoms());
        dto.setNotes(vaccination_records.getNotes());
        if (vaccination_records.getVaccineBatches() != null) {
            dto.setBatchID(vaccination_records.getVaccineBatches().getBatchID());
        } else {
            dto.setBatchID(null);
        }
        if (vaccination_records.getNurse() != null) {
            dto.setNurse_id(vaccination_records.getNurse().getNurseID());
        } else {
            dto.setNurse_id(null);
        }
        if (vaccination_records.getStudent() != null) {
            dto.setStudent_id(vaccination_records.getStudent().getStudentID());
        } else {
            dto.setStudent_id(null);
        }
        dto.setNurse_name(vaccination_records.getNurse().getFullName());
        dto.setStatus(vaccination_records.getStatus());

        return dto;
    }


    public static Vaccination_records MappingVaccinationRecordsDTO(Vaccination_recordsDTO dto) {
        Vaccination_records entity = new Vaccination_records();
        entity.setSeverity(dto.getSeverity());
        entity.setObservation_time(dto.getObservation_time());
        entity.setSymptoms(dto.getSymptoms());
        entity.setObservation_notes(dto.getObservation_notes());

        Student student = new Student();
        student.setStudentID(dto.getStudent_id());
        entity.setStudent(student);

        Vaccine_Batches batch = new Vaccine_Batches();
        batch.setBatchID(dto.getBatchID());
        entity.setVaccineBatches(batch);
        // Map nurse
        SchoolNurse nurse = new SchoolNurse();
        nurse.setNurseID(dto.getNurse_id());
        nurse.setFullName(dto.getNurse_name());
        entity.setNurse(nurse);
        entity.setNotes(dto.getNotes());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public static Vaccine_TypesDTO MappingVaccineTypes(Vaccine_Types dto) {
        Vaccine_TypesDTO entity = new Vaccine_TypesDTO();
        entity.setManufacturer(dto.getManufacturer());
        entity.setCreated_at(dto.getCreated_at());
        entity.setUpdated_at(dto.getUpdated_at());
        entity.setRecommended_ages(dto.getRecommended_ages());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public static Vaccine_Types MappingVaccineTypesDTO(Vaccine_TypesDTO dto) {
        Vaccine_Types entity = new Vaccine_Types();
        entity.setManufacturer(dto.getManufacturer());
        entity.setCreated_at(dto.getCreated_at());
        entity.setUpdated_at(dto.getUpdated_at());
        entity.setRecommended_ages(dto.getRecommended_ages());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public static Vaccine_Batches_EditDTO MappingVaccineBatches(Vaccine_Batches vaccineBatches) {
        Vaccine_Batches_EditDTO entity = new Vaccine_Batches_EditDTO();

        // Dữ liệu tạo bởi
        entity.setCreated_by_nurse_id(vaccineBatches.getNurse().getNurseID());
        entity.setCreated_by_nurse_name(vaccineBatches.getNurse().getFullName());

        // Các trường chính
        entity.setCreated_at(vaccineBatches.getCreated_at());
        entity.setUpdated_at(vaccineBatches.getUpdated_at());
        entity.setDot(vaccineBatches.getDot());
        entity.setLocation(vaccineBatches.getLocation());
        entity.setStatus(vaccineBatches.getStatus());
        entity.setNotes(vaccineBatches.getNotes());
        entity.setScheduled_date(vaccineBatches.getScheduled_date());
        entity.setQuantity_received(vaccineBatches.getQuantity_received());

        // Các ID liên quan
        entity.setVaccineTypeID(vaccineBatches.getVaccineType().getVaccineTypeID());
        entity.setBatchID(vaccineBatches.getBatchID());

        // Dữ liệu sửa gần nhất
        entity.setEdit_nurse_id(vaccineBatches.getNurse().getNurseID());
        entity.setEdit_nurse_name(vaccineBatches.getNurse().getFullName());

        return entity;
    }


    public static Vaccine_Batches MappingVaccineBatchesDTO(Vaccine_Batches_EditDTO dto) {
        Vaccine_Batches entity = new Vaccine_Batches();

        // Dữ liệu tạo bởi
        SchoolNurse createdNurse = new SchoolNurse();
        createdNurse.setNurseID(dto.getCreated_by_nurse_id());
        createdNurse.setFullName(dto.getCreated_by_nurse_name());
        entity.setNurse(createdNurse);

        // Các trường chính
        entity.setCreated_at(dto.getCreated_at());
        entity.setUpdated_at(dto.getUpdated_at());
        entity.setDot(dto.getDot());
        entity.setLocation(dto.getLocation());
        entity.setStatus(dto.getStatus());
        entity.setNotes(dto.getNotes());
        entity.setScheduled_date(dto.getScheduled_date());
        entity.setQuantity_received(dto.getQuantity_received());

        // Các ID liên quan
        Vaccine_Types vaccineType = new Vaccine_Types();
        vaccineType.setVaccineTypeID(dto.getVaccineTypeID());
        entity.setVaccineType(vaccineType);
        entity.setBatchID(dto.getBatchID());
        SchoolNurse editNurse = new SchoolNurse();
        editNurse.setNurseID(dto.getEdit_nurse_id());
        editNurse.setFullName(dto.getEdit_nurse_name());
        entity.setNurse(editNurse); // giả định có field editNurse

        return entity;
    }

    public static Vaccination_records_edit_DTO MappingVaccinationRecordsEdit(Vaccination_records vaccinationRecords) {
        Vaccination_records_edit_DTO editDTO = new Vaccination_records_edit_DTO();
        editDTO.setVaccinationRecordID(vaccinationRecords.getVaccinationRecordID());
        editDTO.setNotes(vaccinationRecords.getNotes());
        editDTO.setStudent_id(vaccinationRecords.getStudent() != null ? vaccinationRecords.getStudent().getStudentID() : null);
        editDTO.setObservation_time(vaccinationRecords.getObservation_time());
        editDTO.setSymptoms(vaccinationRecords.getSymptoms());
        editDTO.setSeverity(vaccinationRecords.getSeverity());
        editDTO.setObservation_notes(vaccinationRecords.getObservation_notes());

        // Thông tin điều dưỡng sửa
        if (vaccinationRecords.getNurse() != null) {
            editDTO.setEdit_Nurse_id(vaccinationRecords.getNurse().getNurseID());
            editDTO.setEdit_nurse_name(vaccinationRecords.getNurse().getFullName());
        }

        // Thông tin lô vắc-xin
        if (vaccinationRecords.getVaccineBatches() != null) {
            editDTO.setBatchID(vaccinationRecords.getVaccineBatches().getBatchID());
        }

        editDTO.setStatus(vaccinationRecords.getStatus());

        // Điều dưỡng tạo
        if (vaccinationRecords.getNurse()!= null) {
            editDTO.setCreated_by_nurse_id(vaccinationRecords.getNurse().getNurseID());
            editDTO.setCreated_by_nurse_name(vaccinationRecords.getNurse().getFullName()); // giả sử có getFullName()
        }

        return editDTO;
    }


    public static Vaccination_records MappingVaccinationRecordsEditDTO(Vaccination_records_edit_DTO dto) {
        Vaccination_records record = new Vaccination_records();

        record.setVaccinationRecordID(dto.getVaccinationRecordID());
        record.setNotes(dto.getNotes());
        record.setObservation_time(dto.getObservation_time());
        record.setSymptoms(dto.getSymptoms());
        record.setSeverity(dto.getSeverity());
        record.setObservation_notes(dto.getObservation_notes());
        record.setStatus(dto.getStatus());

        // Student
        if (dto.getStudent_id() != null) {
            Student student = new Student();
            student.setStudentID(dto.getStudent_id());
            record.setStudent(student);
        }

        // Điều dưỡng chỉnh sửa
        if (dto.getEdit_Nurse_id() != null) {
            SchoolNurse editNurse = new SchoolNurse();
            editNurse.setNurseID(dto.getEdit_Nurse_id());
            editNurse.setFullName(dto.getEdit_nurse_name());
            record.setNurse(editNurse);
        }

        // Điều dưỡng tạo
        if (dto.getCreated_by_nurse_id() != null) {
            SchoolNurse creator = new SchoolNurse();
            creator.setNurseID(dto.getCreated_by_nurse_id());
            creator.setFullName(dto.getCreated_by_nurse_name());
            record.setNurse(creator);
        }

        // Lô vaccine
        if (dto.getBatchID() != null) {
            Vaccine_Batches batch = new Vaccine_Batches();
            batch.setBatchID(dto.getBatchID());
            record.setVaccineBatches(batch);
        }
        return record;
    }







}
