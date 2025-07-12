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
        // Created
        vaccinesDTO.setCreated_by_nurse_id(vaccines.getCreatedByNurse() != null ? vaccines.getCreatedByNurse().getNurseID() : null);
        vaccinesDTO.setCreated_by_nurse_name(vaccines.getCreatedByNurse() != null ? vaccines.getCreatedByNurse().getFullName() : null);
        // Updated
        if (vaccines.getUpdatedByNurse() != null) {
            vaccinesDTO.setEdit_nurse_id(vaccines.getUpdatedByNurse().getNurseID());
            vaccinesDTO.setEdit_nurse_name(vaccines.getUpdatedByNurse().getFullName());
        } else {
            vaccinesDTO.setEdit_nurse_id(null);
            vaccinesDTO.setEdit_nurse_name(null);
        }
        vaccinesDTO.setLocation(vaccines.getLocation());
        vaccinesDTO.setStatus(vaccines.getStatus());
        vaccinesDTO.setNotes(vaccines.getNotes());
        vaccinesDTO.setScheduled_date(vaccines.getScheduled_date());

        vaccinesDTO.setVaccineTypeID(vaccines.getVaccineType().getVaccineTypeID());
        vaccinesDTO.setCreated_at(vaccines.getCreated_at());
        vaccinesDTO.setUpdated_at(vaccines.getUpdated_at());
        vaccinesDTO.setBatchID(vaccines.getBatchID());
//        vaccinesDTO.setCountAgreeConsentForms(countAgree);
        return vaccinesDTO;
    }


    public static Vaccine_Batches MappingVaccineDTO(Vaccine_BatchesDTO vaccinesDTO) {
        Vaccine_Batches vaccines = new com.team_3.School_Medical_Management_System.Model.Vaccine_Batches();

        // Map loại vaccine
        Vaccine_Types vaccineType = new Vaccine_Types();
        vaccineType.setVaccineTypeID(vaccinesDTO.getVaccineTypeID());
        vaccines.setVaccineType(vaccineType);

        // Map CreatedByNurse
        if (vaccinesDTO.getCreated_by_nurse_id() != null) {
            SchoolNurse createdByNurse = new SchoolNurse();
            createdByNurse.setNurseID(vaccinesDTO.getCreated_by_nurse_id());
            createdByNurse.setFullName(vaccinesDTO.getCreated_by_nurse_name()); // nếu DTO có tên, gán luôn
            vaccines.setCreatedByNurse(createdByNurse);
        } else {
            vaccines.setCreatedByNurse(null);
        }

        // Map UpdatedByNurse (Edit Nurse)
        if (vaccinesDTO.getEdit_nurse_id() != null) {
            SchoolNurse updatedByNurse = new SchoolNurse();
            updatedByNurse.setNurseID(vaccinesDTO.getEdit_nurse_id());
            updatedByNurse.setFullName(vaccinesDTO.getEdit_nurse_name()); // nếu DTO có tên, gán luôn
            vaccines.setUpdatedByNurse(updatedByNurse);
        } else {
            vaccines.setUpdatedByNurse(null);
        }

        // Các thuộc tính còn lại
        vaccines.setCreated_at(vaccinesDTO.getCreated_at());
        vaccines.setUpdated_at(vaccinesDTO.getUpdated_at());
        vaccines.setDot(vaccinesDTO.getDot());

        vaccines.setScheduled_date(vaccinesDTO.getScheduled_date());
        vaccines.setLocation(vaccinesDTO.getLocation());
        vaccines.setStatus(vaccinesDTO.getStatus());
        vaccines.setNotes(vaccinesDTO.getNotes());
        vaccines.setBatchID(vaccinesDTO.getBatchID());

        return vaccines;
    }
    public static Consent_formsDTO MappingConsent(Consent_forms consent_forms) {
        Consent_formsDTO consent_formsDTO = new Consent_formsDTO();
        consent_formsDTO.setStudentId(consent_forms.getStudent().getStudentID());
        consent_formsDTO.setReason(consent_forms.getReason());
        consent_formsDTO.setVaccineBatchId(consent_forms.getVaccineBatches().getBatchID());
        consent_formsDTO.setHasAllergy(consent_forms.getHasAllergy());
        consent_formsDTO.setIsAgree(consent_forms.getIsAgree());
        consent_formsDTO.setParentID(consent_forms.getParent().getParentID());
        consent_formsDTO.setSend_date(consent_forms.getSend_date());
        consent_formsDTO.setExpire_date(consent_forms.getExpire_date());
        consent_formsDTO.setConsent_id(consent_forms.getConsent_id());
//        consent_formsDTO.setScheduledDate(consent_forms.getVaccineBatches().getScheduled_date());
//        consent_formsDTO.setLocation(consent_forms.getVaccineBatches().getLocation());
        consent_formsDTO.setStatus(consent_forms.getStatus());
        return consent_formsDTO;
    }

    public static Consent_forms MappingConsentDTO(Consent_formsDTO dto) {
        Consent_forms entity = new Consent_forms();

        // Gán ID (nếu bạn cho phép update, nếu không thì set null để persist mới)
        entity.setConsent_id(dto.getConsent_id());


        // Gán các trường cơ bản
        entity.setReason(dto.getReason());
        entity.setExpire_date(dto.getExpire_date());
        entity.setSend_date(dto.getSend_date());
        entity.setIsAgree(dto.getIsAgree());
        entity.setHasAllergy(dto.getHasAllergy());
        entity.setStatus(dto.getStatus());

        // Mapping Student
        Student student = new Student();
        student.setStudentID(dto.getStudentId());
        entity.setStudent(student);

        // Mapping Parent
        Parent parent = new Parent();
        parent.setParentID(dto.getParentID());
        entity.setParent(parent);

        // Mapping Vaccine_Batches
        Vaccine_Batches vaccineBatches = new Vaccine_Batches();
        vaccineBatches.setBatchID(dto.getVaccineBatchId());
//        vaccineBatches.setScheduled_date(dto.getScheduledDate());
//        vaccineBatches.setLocation(dto.getLocation()); // nếu cần lưu Location ở entity
        entity.setVaccineBatches(vaccineBatches);

        return entity;
    }


    public static Vaccination_recordsDTO MappingVaccinationRecords(Vaccination_records vaccination_records) {
        Vaccination_recordsDTO dto = new Vaccination_recordsDTO();

        // Vaccine info
        if(vaccination_records.getConsentForm() != null) {
            dto.setConsentId(vaccination_records.getConsentForm().getConsent_id());
        }else {
            dto.setConsentId(null);
        }


        if (vaccination_records.getVaccineBatches() != null &&
                vaccination_records.getVaccineBatches().getVaccineType() != null) {
            dto.setVaccineName(vaccination_records.getVaccineBatches().getVaccineType().getName());
            dto.setBatchID(vaccination_records.getVaccineBatches().getBatchID());
        } else {
            dto.setVaccineName(null);
            dto.setBatchID(null);
        }

        // Student info
        if (vaccination_records.getStudent() != null) {
            dto.setStudentName(vaccination_records.getStudent().getFullName());
            dto.setStudentID(vaccination_records.getStudent().getStudentID());
            dto.setClassName(vaccination_records.getStudent().getClassName());
        } else {
            dto.setStudentName(null);
            dto.setStudentID(null);
            dto.setClassName(null);
        }

        // Basic info
        dto.setVaccinationRecordID(vaccination_records.getVaccinationRecordID());
        dto.setSeverity(vaccination_records.getSeverity());
        dto.setObservation_notes(vaccination_records.getObservation_notes());
        dto.setObservation_time(vaccination_records.getObservation_time());
        dto.setSymptoms(vaccination_records.getSymptoms());
        dto.setNotes(vaccination_records.getNotes());
        dto.setStatus(vaccination_records.getStatus());

        // Nurse info - mapping riêng từng trường
        if (vaccination_records.getCreatedByNurse() != null) {
            dto.setCreateNurseID(vaccination_records.getCreatedByNurse().getNurseID());
            dto.setCreateNurseName(vaccination_records.getCreatedByNurse().getFullName());
        } else {
            dto.setCreateNurseID(null);
            dto.setCreateNurseName(null);
        }

        if (vaccination_records.getUpdatedByNurse() != null) {
            dto.setEditNurseID(vaccination_records.getUpdatedByNurse().getNurseID());
            dto.setEditNurseName(vaccination_records.getUpdatedByNurse().getFullName());
        } else {
            dto.setEditNurseID(null);
            dto.setEditNurseName(null);
        }

        return dto;
    }


    public static Vaccination_records MappingVaccinationRecordsDTO(Vaccination_recordsDTO dto) {
        Vaccination_records entity = new Vaccination_records();
        entity.setVaccinationRecordID(dto.getVaccinationRecordID());
        entity.setSeverity(dto.getSeverity());
        entity.setObservation_time(dto.getObservation_time());
        entity.setSymptoms(dto.getSymptoms());
        entity.setObservation_notes(dto.getObservation_notes());

        Student student = new Student();
        student.setStudentID(dto.getStudentID());
        entity.setStudent(student);

        Vaccine_Batches batch = new Vaccine_Batches();
        batch.setBatchID(dto.getBatchID());
        entity.setVaccineBatches(batch);
        // Map nurse
        SchoolNurse nurse = new SchoolNurse();
        nurse.setNurseID(dto.getCreateNurseID());
        nurse.setFullName(dto.getCreateNurseName());
        entity.setCreatedByNurse(nurse);
        entity.setNotes(dto.getNotes());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public static Vaccine_TypesDTO MappingVaccineTypes(Vaccine_Types vaccine_types) {
        Vaccine_TypesDTO entity = new Vaccine_TypesDTO();
        entity.setVaccineTypeID(vaccine_types.getVaccineTypeID());
        entity.setManufacturer(vaccine_types.getManufacturer());
        entity.setCreated_at(vaccine_types.getCreated_at());
        entity.setUpdated_at(vaccine_types.getUpdated_at());
        entity.setRecommended_ages(vaccine_types.getRecommended_ages());
        entity.setName(vaccine_types.getName());
        entity.setDescription(vaccine_types.getDescription());
        return entity;
    }

    public static Vaccine_Types MappingVaccineTypesDTO(Vaccine_TypesDTO dto) {
        Vaccine_Types entity = new Vaccine_Types();
        entity.setVaccineTypeID(dto.getVaccineTypeID());
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

        // Dữ liệu tạo bởi (CreatedByNurse)


        // Các trường chính
        entity.setCreated_at(vaccineBatches.getCreated_at());
        entity.setUpdated_at(vaccineBatches.getUpdated_at());
        entity.setDot(vaccineBatches.getDot());
        entity.setLocation(vaccineBatches.getLocation());
        entity.setStatus(vaccineBatches.getStatus());
        entity.setNotes(vaccineBatches.getNotes());
        entity.setScheduled_date(vaccineBatches.getScheduled_date());
      

        // VaccineType
        if (vaccineBatches.getVaccineType() != null) {
            entity.setVaccineTypeID(vaccineBatches.getVaccineType().getVaccineTypeID());
        } else {
            entity.setVaccineTypeID(null);
        }

        entity.setBatchID(vaccineBatches.getBatchID());

        // Dữ liệu sửa gần nhất (UpdatedByNurse)
        if (vaccineBatches.getUpdatedByNurse() != null) {
            entity.setEdit_nurse_id(vaccineBatches.getUpdatedByNurse().getNurseID());
            entity.setEdit_nurse_name(vaccineBatches.getUpdatedByNurse().getFullName());
        } else {
            entity.setEdit_nurse_id(null);
            entity.setEdit_nurse_name(null);
        }

        return entity;
    }

    public static Vaccine_Batches MappingVaccineBatchesDTO(Vaccine_Batches_EditDTO dto) {
        Vaccine_Batches entity = new Vaccine_Batches();




        // Người chỉnh sửa cuối (UpdatedByNurse/EditNurse)
        if (dto.getEdit_nurse_id() != null) {
            SchoolNurse editNurse = new SchoolNurse();
            editNurse.setNurseID(dto.getEdit_nurse_id());
            editNurse.setFullName(dto.getEdit_nurse_name());
            entity.setUpdatedByNurse(editNurse);
        } else {
            entity.setUpdatedByNurse(null);
        }

        // Các trường chính
        entity.setCreated_at(dto.getCreated_at());
        entity.setUpdated_at(dto.getUpdated_at());
        entity.setDot(dto.getDot());
        entity.setLocation(dto.getLocation());
        entity.setStatus(dto.getStatus());
        entity.setNotes(dto.getNotes());
        entity.setScheduled_date(dto.getScheduled_date());
      

        entity.setBatchID(dto.getBatchID());

        // Vaccine type
        if (dto.getVaccineTypeID() != null) {
            Vaccine_Types vaccineType = new Vaccine_Types();
            vaccineType.setVaccineTypeID(dto.getVaccineTypeID());
            entity.setVaccineType(vaccineType);
        } else {
            entity.setVaccineType(null);
        }

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
        if (vaccinationRecords.getUpdatedByNurse() != null) {
            editDTO.setEdit_Nurse_id(vaccinationRecords.getUpdatedByNurse().getNurseID());
            editDTO.setEdit_nurse_name(vaccinationRecords.getUpdatedByNurse().getFullName());
        }

        // Thông tin lô vắc-xin
        if (vaccinationRecords.getVaccineBatches() != null) {
            editDTO.setBatchID(vaccinationRecords.getVaccineBatches().getBatchID());
        }

        editDTO.setStatus(vaccinationRecords.getStatus());

        // Điều dưỡng tạo
        if (vaccinationRecords.getCreatedByNurse() != null) {
            editDTO.setCreated_by_nurse_id(vaccinationRecords.getCreatedByNurse().getNurseID());
            editDTO.setCreated_by_nurse_name(vaccinationRecords.getCreatedByNurse().getFullName()); // giả sử có getFullName()
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
            record.setUpdatedByNurse(editNurse);
        }

        // Điều dưỡng tạo
        if (dto.getCreated_by_nurse_id() != null) {
            SchoolNurse creator = new SchoolNurse();
            creator.setNurseID(dto.getCreated_by_nurse_id());
            creator.setFullName(dto.getCreated_by_nurse_name());
            record.setCreatedByNurse(creator);
        }

        // Lô vaccine
        if (dto.getBatchID() != null) {
            Vaccine_Batches batch = new Vaccine_Batches();
            batch.setBatchID(dto.getBatchID());
            record.setVaccineBatches(batch);
        }
        return record;
    }

    public static Vaccine_Types_Edit_DTO MappingVaccineTypesEdit(Vaccine_Types dto) {
        Vaccine_Types_Edit_DTO editDTO = new Vaccine_Types_Edit_DTO();
        editDTO.setDescription(dto.getDescription());
        editDTO.setManufacturer(dto.getManufacturer());
        editDTO.setRecommended_ages(dto.getRecommended_ages());
        editDTO.setCreated_at(dto.getCreated_at());
        editDTO.setCreated_at(dto.getCreated_at());
        editDTO.setUpdated_at(dto.getUpdated_at());
        editDTO.setVaccineTypeID(dto.getVaccineTypeID());
        editDTO.setName(dto.getName());
        return editDTO;
    }

    public static Vaccine_Types MappingVaccineTypesEditDTO(Vaccine_Types_Edit_DTO dto) {
        Vaccine_Types vaccineType = new Vaccine_Types();
        vaccineType.setDescription(dto.getDescription());
        vaccineType.setManufacturer(dto.getManufacturer());
        vaccineType.setRecommended_ages(dto.getRecommended_ages());
        vaccineType.setCreated_at(dto.getCreated_at());
        vaccineType.setUpdated_at(dto.getUpdated_at());
        vaccineType.setVaccineTypeID(dto.getVaccineTypeID());
        vaccineType.setName(dto.getName());
        return vaccineType;
    }

    public static Consent_formViewDTO MappingConent_View(Consent_forms consent_forms) {
        Consent_formViewDTO viewDTO = new Consent_formViewDTO();
        viewDTO.setFullNameOfStudent(consent_forms.getStudent().getFullName());
        viewDTO.setFullNameOfParent(consent_forms.getParent().getFullName());
        viewDTO.setVaccineName(consent_forms.getVaccineBatches().getVaccineType().getName());
        viewDTO.setExpire_date(consent_forms.getExpire_date());
        viewDTO.setIsAgree(consent_forms.getIsAgree());
        viewDTO.setReason(consent_forms.getReason());
        viewDTO.setScheduledDate(consent_forms.getVaccineBatches().getScheduled_date());
        viewDTO.setHasAllergy(consent_forms.getHasAllergy());
        viewDTO.setSend_date(consent_forms.getSend_date());
        viewDTO.setExpire_date(consent_forms.getExpire_date());
        viewDTO.setClassName(consent_forms.getStudent().getClassName());
        viewDTO.setConsent_id(consent_forms.getConsent_id());
        viewDTO.setStatus(consent_forms.getStatus());
        viewDTO.setLocation(consent_forms.getVaccineBatches().getLocation());
        viewDTO.setBacthID(consent_forms.getVaccineBatches().getBatchID());


        return viewDTO;
    }

    public static Vaccination_records_SentParent_DTO MappingVaccination_records_SentParent(Vaccination_records entity) {
        Vaccination_records_SentParent_DTO dto = new Vaccination_records_SentParent_DTO();

        dto.setNotes(entity.getNotes());
        dto.setSymptoms(entity.getSymptoms());
        dto.setSeverity(entity.getSeverity());
        dto.setObservation_notes(entity.getObservation_notes());
        dto.setObservation_time(entity.getObservation_time());
        dto.setStatus(entity.getStatus());

        if(entity.getConsentForm() != null) {
            dto.setCosentID(entity.getConsentForm().getConsent_id());
        }else{
            dto.setCosentID(null);
        }

        // Student mapping
        if (entity.getStudent() != null) {
            dto.setStudentId(entity.getStudent().getStudentID());
            dto.setClassName(entity.getStudent().getClassName());
            dto.setStudentName(entity.getStudent().getFullName());

            if (entity.getStudent().getParent() != null) {
                Parent parent = entity.getStudent().getParent();
                dto.setParentID(parent.getParentID());
                dto.setEmail(parent.getEmail());
            }
        }

        // Nurse mapping
        if (entity.getCreatedByNurse() != null) {
            dto.setCreateNurseID(entity.getCreatedByNurse().getNurseID());
            dto.setCreateNurseName(entity.getCreatedByNurse().getFullName());
        }

        if(entity.getUpdatedByNurse() != null) {
            dto.setEditnurseID(entity.getUpdatedByNurse().getNurseID());
            dto.setEditNurseName(entity.getUpdatedByNurse().getFullName());
        }

        // Vaccine batch & type mapping
        if (entity.getVaccineBatches() != null) {
            dto.setVaccineBatchId(entity.getVaccineBatches().getBatchID());

            if (entity.getVaccineBatches().getVaccineType() != null) {
                dto.setVaccineBatchName(entity.getVaccineBatches().getVaccineType().getName());
            }
        }
        return dto;
    }

    public static Vaccination_records_SentParent_Edit_DTO MappingVaccination_records_SentParent_Edit(
            Vaccination_records record
    ) {
        Vaccination_records_SentParent_Edit_DTO dto = new Vaccination_records_SentParent_Edit_DTO();
        dto.setNotes(record.getNotes());
        dto.setSymptoms(record.getSymptoms());
        dto.setSeverity(record.getSeverity());
        dto.setObservation_notes(record.getObservation_notes());
        dto.setObservation_time(record.getObservation_time());
        dto.setStatus(record.getStatus());

        if(record.getConsentForm() != null) {
            dto.setConsentId(record.getConsentForm().getConsent_id());
        }else{
            dto.setConsentId(null);
        }
        if (record.getStudent() != null) {
            dto.setStudentId(record.getStudent().getStudentID());
            if (record.getStudent().getParent() != null) {
                Parent parent = record.getStudent().getParent();
                dto.setParentID(parent.getParentID());
                dto.setEmail(parent.getEmail());
            }
        }

        // Người chỉnh sửa hiện tại (edit nurse)
        if (record.getUpdatedByNurse() != null) {
            dto.setEditNurseID(record.getUpdatedByNurse().getNurseID());
            dto.setEditNurseName(record.getUpdatedByNurse().getFullName());
        }else {
            dto.setEditNurseID(null);
            dto.setEditNurseName(null);

        }
        if(record.getCreatedByNurse() != null) {
            dto.setCreateNurseID(record.getCreatedByNurse().getNurseID());
            dto.setCreateNurseName(record.getCreatedByNurse().getFullName());
        }else {
            dto.setCreateNurseID(null);
            dto.setCreateNurseName(null);
        }
        if(record.getVaccineBatches() != null) {
            dto.setVaccineBatchId(record.getVaccineBatches().getBatchID());
        }

        return dto;
    }


    public static Post_vaccination_observationsDTO MappingVaccination_observations(Post_vaccination_observations entity) {
        Post_vaccination_observationsDTO postDTO = new Post_vaccination_observationsDTO();
        postDTO.setNotes(entity.getNotes());
        postDTO.setSymptoms(entity.getSymptoms());
        postDTO.setSeverity(entity.getSeverity());
        postDTO.setObservation_id(entity.getObservation_id());
        postDTO.setObservation_time(entity.getObservation_time());
        postDTO.setStudentName(entity.getVaccination_records().getStudent().getFullName());
        postDTO.setVaccinationName(entity.getVaccination_records().getVaccineBatches().getVaccineType().getName());
        postDTO.setStatus(entity.getStatus());
        if (entity.getCreatedByNurse()!= null) {
            postDTO.setCreateNurseId(entity.getVaccination_records().getCreatedByNurse().getNurseID());
            postDTO.setCreateNurseName(entity.getVaccination_records().getCreatedByNurse().getFullName());
        }else {
            postDTO.setCreateNurseId(null);
            postDTO.setCreateNurseName(null);
        }

        if(entity.getUpdatedByNurse()!= null) {
            postDTO.setEditNurseID(entity.getUpdatedByNurse().getNurseID());
            postDTO.setEditNurseName(entity.getUpdatedByNurse().getFullName());
        }else {
            postDTO.setEditNurseID(null);
            postDTO.setEditNurseName(null);
        }
        postDTO.setVaccinationRecordID(entity.getVaccination_records().getVaccinationRecordID());
        postDTO.setVaccinationName(entity.getVaccination_records().getVaccineBatches().getVaccineType().getName());
        postDTO.setVaccinationID(entity.getVaccination_records().getVaccineBatches().getBatchID());
        postDTO.setStudentName(entity.getVaccination_records().getStudent().getFullName());
        postDTO.setStudentID(entity.getVaccination_records().getStudent().getStudentID());
        postDTO.setParentID(entity.getVaccination_records().getStudent().getParent().getParentID());
        postDTO.setClassName(entity.getVaccination_records().getStudent().getClassName());

        return postDTO;
    }

    public static Post_vaccination_observations MappingVaccination_observationsDTO(Post_vaccination_observationsDTO dto) {
        Post_vaccination_observations postDTO = new Post_vaccination_observations();
        postDTO.setNotes(dto.getNotes());
        postDTO.setSymptoms(dto.getSymptoms());
        postDTO.setSeverity(dto.getSeverity());
//      postDTO.setObservation_id(dto.getObservation_id());
        postDTO.setObservation_time(dto.getObservation_time());
        postDTO.setStatus(dto.getStatus());

        if (dto.getObservation_id() != null) {
            postDTO.setObservation_id(dto.getObservation_id());
        }

        Vaccination_records vaccination_records = new Vaccination_records();
        vaccination_records.setVaccinationRecordID(dto.getVaccinationRecordID());
        postDTO.setVaccination_records(vaccination_records);
        return postDTO;
    }

    public static Post_vaccination_observations_edit_DTO MappingPost_vaccination_observations_edit_DTO(Post_vaccination_observations entity) {
        Post_vaccination_observations_edit_DTO dto = new Post_vaccination_observations_edit_DTO();

        dto.setObservation_id(entity.getObservation_id());
        dto.setObservation_time(entity.getObservation_time());
        dto.setSymptoms(entity.getSymptoms());
        dto.setSeverity(entity.getSeverity());
        dto.setNotes(entity.getNotes());
        dto.setStatus(entity.getStatus());

        if (entity.getVaccination_records() != null) {
            dto.setVaccinationRecordID(entity.getVaccination_records().getVaccinationRecordID());
        }
        if (entity.getUpdatedByNurse() != null) {
            dto.setEditNurseID(entity.getUpdatedByNurse().getNurseID());
            dto.setEditNurseName(entity.getUpdatedByNurse().getFullName());

        }

        if(entity.getCreatedByNurse()!= null) {
            dto.setCreateNurseID(entity.getCreatedByNurse().getNurseID());
            dto.setCreateNurseName(entity.getCreatedByNurse().getFullName());
        }
        return dto;
    }


    public static Post_vaccination_observations MappingPost_vaccination_observations(Post_vaccination_observations_edit_DTO editDTO) {
        Post_vaccination_observations entity = new Post_vaccination_observations();
        entity.setObservation_id(editDTO.getObservation_id());
        entity.setObservation_time(editDTO.getObservation_time());
        entity.setSymptoms(editDTO.getSymptoms());
        entity.setSeverity(editDTO.getSeverity());
        entity.setNotes(editDTO.getNotes());
        entity.setStatus(editDTO.getStatus());
        // Gán vaccination record bằng ID
        Vaccination_records vaccinationRecord = new Vaccination_records();
        vaccinationRecord.setVaccinationRecordID(editDTO.getVaccinationRecordID());
        entity.setVaccination_records(vaccinationRecord);
        // Gán nurse từ editNurseID
        if (editDTO.getEditNurseID() != null) {
            SchoolNurse nurse = new SchoolNurse();
            nurse.setNurseID(editDTO.getEditNurseID());
            entity.setUpdatedByNurse(nurse);
        }
        return entity;
    }


    public static Post_vaccination_observations_SendForParent_DTO MappingPost_vaccination_observations_SendForParent_DTO(Post_vaccination_observations entity) {
        Post_vaccination_observations_SendForParent_DTO dto = new Post_vaccination_observations_SendForParent_DTO();

        // 1. Basic fields
        dto.setObservation_time(entity.getObservation_time());
        dto.setSymptoms(entity.getSymptoms());
        dto.setSeverity(entity.getSeverity());
        dto.setNotes(entity.getNotes());
        dto.setStatus(entity.getStatus());



        // 3. Từ Vaccination Record
        Vaccination_records vr = entity.getVaccination_records();
        if (vr != null) {
            dto.setVaccinationRecordID(vr.getVaccinationRecordID());


            // Lấy vaccine info
            if (vr.getVaccineBatches() != null && vr.getVaccineBatches().getVaccineType() != null) {
                dto.setVaccitypeID(vr.getVaccineBatches().getVaccineType().getVaccineTypeID());
                dto.setVaccitypeName(vr.getVaccineBatches().getVaccineType().getName());
            } else {
                dto.setVaccitypeName("Không rõ loại vắc xin");
            }

            // Người tạo hồ sơ tiêm chủng
            if (vr.getCreatedByNurse() != null) {
                dto.setCreateNurseName(vr.getCreatedByNurse().getFullName());
                dto.setCreateNurseID(vr.getCreatedByNurse().getNurseID());
            }else {
                dto.setCreateNurseID(null);
                dto.setCreateNurseName(null);
            }
            if (vr.getUpdatedByNurse() != null) {
                dto.setEditNurseName(vr.getUpdatedByNurse().getFullName());
                dto.setEditNurseID(vr.getUpdatedByNurse().getNurseID());
            }else {
                dto.setEditNurseID(null);
                dto.setEditNurseName(null);
            }

            // Student + Parent
            Student student = vr.getStudent();
            if (student != null) {
                dto.setStudentID(student.getStudentID());
                dto.setStudentName(student.getFullName());
                dto.setClassName(student.getClassName());

                Parent parent = student.getParent();
                if (parent != null) {
                    dto.setParentID(parent.getParentID());
                    dto.setEmail(parent.getEmail());
                } else {
                    dto.setEmail("Không có email phụ huynh");
                }
            } else {
                dto.setStudentName("Không có học sinh");
            }
        } else {
            dto.setVaccitypeName("Không có hồ sơ tiêm chủng");
        }

        return dto;
    }


    public static Post_vaccination_observations_edit_Update_SendParent_DTO Post_vaccination_observations_edit_Update_SendParent_DTO(
            Post_vaccination_observations entity) {
        Post_vaccination_observations_edit_Update_SendParent_DTO dto = new Post_vaccination_observations_edit_Update_SendParent_DTO();

        // 1. Basic fields
        dto.setObservation_time(entity.getObservation_time());
        dto.setSymptoms(entity.getSymptoms());
        dto.setSeverity(entity.getSeverity());
        dto.setNotes(entity.getNotes());
        dto.setStatus(entity.getStatus());

        // 3. Từ Vaccination Record
        Vaccination_records vr = entity.getVaccination_records();
        if (vr != null) {
            dto.setVaccinationRecordID(vr.getVaccinationRecordID());

            // Lấy vaccine info
            if (vr.getVaccineBatches() != null && vr.getVaccineBatches().getVaccineType() != null) {
                dto.setVaccitypeID(vr.getVaccineBatches().getVaccineType().getVaccineTypeID());
                dto.setVaccitypeName(vr.getVaccineBatches().getVaccineType().getName());
            } else {
                dto.setVaccitypeName("Không rõ loại vắc xin");
            }

            // Người tạo hồ sơ tiêm chủng
            if (vr.getCreatedByNurse() != null) {
                dto.setCreateNurseID(vr.getCreatedByNurse().getNurseID());
                dto.setCreateNurseName(vr.getCreatedByNurse().getFullName());
            }else {
                dto.setEditNurseID(null);
                dto.setEditNurseName(null);
            }

            if (vr.getUpdatedByNurse() != null) {
                dto.setEditNurseName(vr.getUpdatedByNurse().getFullName());
                dto.setEditNurseID(vr.getUpdatedByNurse().getNurseID());
            }else {
                dto.setEditNurseName(null);
                dto.setEditNurseID(null);
            }
            // Student + Parent
            Student student = vr.getStudent();
            if (student != null) {
                dto.setStudentID(student.getStudentID());
                dto.setStudentName(student.getFullName());
                dto.setClassName(student.getClassName());

                Parent parent = student.getParent();
                if (parent != null) {
                    dto.setParentID(parent.getParentID());
                    dto.setEmail(parent.getEmail());
                } else {
                    dto.setEmail("Không có email phụ huynh");
                }
            } else {
                dto.setStudentName("Không có học sinh");
            }
        } else {
            dto.setVaccitypeName("Không có hồ sơ tiêm chủng");
        }

        return dto;
    }
//    public static MedicalSupplyDTO mapMedicalSupplyDTO(MedicalSupply entity) {
//        MedicalSupplyDTO dto = new MedicalSupplyDTO();
//        dto.setMedicalSupplyID(entity.getMedicalSupplyID());
//        dto.setSupplyName(entity.getSupplyName());
//        dto.setUnit(entity.getUnit());
//        dto.setQuantityAvailable(entity.getQuantityAvailable());
//        dto.setReorderLevel(entity.getReorderLevel());
//        dto.setStorageTemperature(entity.getStorageTemperature());
//        dto.setDateAdded(entity.getDateAdded());
//
//        if (entity.getVaccineType() != null) {
//            dto.setVaccineTypeID(entity.getVaccineType().getVaccineTypeID());
//        }
//        if (entity.getHealthCheck() != null) {
//            dto.setHealthCheckId(entity.getHealthCheck().getCheckID());
//        }
//        if(entity.getSupplyCategory() != null) {
//            dto.setCategoryID(entity.getSupplyCategory().getCategoryID());
//        }
//
//
//
//        return dto;
//    }
//    public static MedicalSupply mapToMedicalSupply(MedicalSupplyDTO dto) {
//        MedicalSupply supply = new MedicalSupply();
//        supply.setMedicalSupplyID(dto.getMedicalSupplyID());
//        supply.setSupplyName(dto.getSupplyName());
//        supply.setUnit(dto.getUnit());
//        supply.setQuantityAvailable(dto.getQuantityAvailable());
//        supply.setReorderLevel(dto.getReorderLevel());
//        supply.setStorageTemperature(dto.getStorageTemperature());
//        supply.setDateAdded(dto.getDateAdded());
//        if (dto.getVaccineTypeID() != null) {
//            Vaccine_Types type = new Vaccine_Types();
//            type.setVaccineTypeID(dto.getVaccineTypeID());
//            supply.setVaccineType(type);
//        }
//        if (dto.getHealthCheckId() != null) {
//            HealthCheck_Student hc = new HealthCheck_Student();
//            hc.setCheckID(dto.getHealthCheckId());
//            supply.setHealthCheck(hc);
//        }
//
//        if(dto.getCategoryID() != null) {
//            SupplyCategory supplyCategory = new SupplyCategory();
//            supplyCategory.setCategoryID(dto.getCategoryID());
//            supply.setSupplyCategory(supplyCategory);
//        }
//        return supply;
//    }


    public static Vaccine_BatchDTO MappingVaccine(com.team_3.School_Medical_Management_System.Model.Vaccine_Batches vaccines, Long countAgree) {
        Vaccine_BatchDTO vaccinesDTO = new Vaccine_BatchDTO();
        vaccinesDTO.setDot(vaccines.getDot());
        // Created
        vaccinesDTO.setCreated_by_nurse_id(vaccines.getCreatedByNurse() != null ? vaccines.getCreatedByNurse().getNurseID() : null);
        vaccinesDTO.setCreated_by_nurse_name(vaccines.getCreatedByNurse() != null ? vaccines.getCreatedByNurse().getFullName() : null);
        // Updated
        if (vaccines.getUpdatedByNurse() != null) {
            vaccinesDTO.setEdit_nurse_id(vaccines.getUpdatedByNurse().getNurseID());
            vaccinesDTO.setEdit_nurse_name(vaccines.getUpdatedByNurse().getFullName());
        } else {
            vaccinesDTO.setEdit_nurse_id(null);
            vaccinesDTO.setEdit_nurse_name(null);
        }
        vaccinesDTO.setLocation(vaccines.getLocation());
        vaccinesDTO.setStatus(vaccines.getStatus());
        vaccinesDTO.setNotes(vaccines.getNotes());
        vaccinesDTO.setScheduled_date(vaccines.getScheduled_date());
        vaccinesDTO.setVaccineTypeID(vaccines.getVaccineType().getVaccineTypeID());
        vaccinesDTO.setCreated_at(vaccines.getCreated_at());
        vaccinesDTO.setUpdated_at(vaccines.getUpdated_at());
        vaccinesDTO.setBatchID(vaccines.getBatchID());
        vaccinesDTO.setCountAgreeConsentForms(countAgree);
        return vaccinesDTO;
    }

}
