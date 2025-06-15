package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.Consent_formsDTO;
import com.team_3.School_Medical_Management_System.DTO.Consent_formsRequestDTO;
import com.team_3.School_Medical_Management_System.DTO.ParentConfirmDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.ConsentFormsRepository;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Consent_formsServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.Consent_formsInterFace;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.*;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class Consent_formsSerivce implements Consent_formsServiceInterFace {
    private Consent_formsInterFace consent_formsRepo;
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private VaccinesRepo vaccinesRepo;

    @Autowired
    private Vaccination_scheduleRepo vaccination_scheduleRepo;

    @Autowired
    private ParentRepo parentRepo;

    @Autowired
    private ConsentFormsRepository consent_forms_repo;


    @Autowired
    public Consent_formsSerivce(Consent_formsInterFace consent_formsRepo) {
        this.consent_formsRepo = consent_formsRepo;
    }

    @Override
    public List<Consent_formsDTO> getConsent_forms() {
        var consent_forms = consent_formsRepo.getConsent_forms();
        return consent_forms.stream().map(TransferModelsDTO::MappingConsent).collect(Collectors.toList());
    }

    @Override
    public Consent_formsDTO addConsent_forms(Consent_formsDTO dto) {
        Student student = studentRepo.GetStudentByFullName(dto.getFullNameOfStudent());
        if (student == null) {
            throw new RuntimeException("Student not found");
        }
        Vaccines vaccine = vaccinesRepo.GetVaccineByVaccineName(dto.getName());
        if (vaccine == null) {
            throw new RuntimeException("Vaccine not found");
        }

        Parent parent = student.getParent();
        if (parent == null) throw new RuntimeException("Parent not found");


        Vaccination_schedule schedule = vaccination_scheduleRepo.vaccination_scheduleById(dto.getScheduled_id());
        if (schedule == null) {
            throw new RuntimeException("Schedule not found");
        }

        Consent_forms entity = new Consent_forms();
        entity.setStudent(student);
        entity.setParent(parent);
        entity.setVaccine(vaccine);
        entity.setSchedule(schedule);
        entity.setConsent_date(new Date());
        consent_formsRepo.addConsent_forms(entity);
        return null;
    }


    @Override
    public List<Consent_formsDTO> getConsent_formsByParentName(String fullName) {
        var consent_formsName = consent_formsRepo.getConsent_formsByParentName(fullName);
        return consent_formsName.stream().map(TransferModelsDTO::MappingConsent).collect(Collectors.toList());
    }

    @Override
    public Consent_formsRequestDTO getConsentFormForParent(int consentFormId) {
        Consent_forms entity = consent_formsRepo.getConsent_formsById(consentFormId);
        if (entity == null) {
            throw new RuntimeException("Consent Form not found");
        }
        return TransferModelsDTO.convertToParentViewDTO(entity);
    }

    @Override
    public List<Consent_formsDTO> getConsent_formsIsAgree(int batch_id) {
        var listArrgee = consent_formsRepo.getConsent_formsIsAgree(batch_id);

        if (listArrgee.isEmpty()) {
            throw new RuntimeException("List of consent_forms is empty");
        }

        List<Consent_formsDTO> dtoList = listArrgee.stream()
                .map(TransferModelsDTO::MappingConsent)
                .collect(Collectors.toList());

        return dtoList;
    }

    @Override
    public List<Consent_formsDTO> getConsent_formsClass(String class_name) {
        var className = consent_formsRepo.getConsent_formsClass(class_name);
        if (className == null) throw new RuntimeException("Class not found");
        return className.stream().map(TransferModelsDTO::MappingConsent).collect(Collectors.toList());
    }

    @Override
    public void parentConfirm(ParentConfirmDTO dto) {
        Consent_forms entity = consent_formsRepo.getConsent_formsById(dto.getConsentFormId());
        if (entity == null) {
            throw new RuntimeException("Consent Form not found");
        }
        entity.setIsAgree(dto.getIsAgree());
        entity.setReason(dto.getReason());
        entity.setHasAllergy(dto.getHasAllergy());
        entity.setConsent_date(new Date()); // cập nhật lại ngày phụ huynh xác nhận
        consent_formsRepo.updateConsent_forms(entity);
    }

    @Override
    public Long countConsentFormsIsAgreeByBatch(int batch_id) {
        return consent_formsRepo.countConsentFormsIsAgreeByBatch(batch_id);
    }

    @Override
    public Long countConsentFormsDisAgreeByBatch(int batch_id) {
        return consent_formsRepo.countConsentFormsDisAgreeByBatch(batch_id);
    }

    @Override
    public Long countConsentFormsPendingByBatch(int batch_id) {
        return consent_formsRepo.countConsentFormsPendingByBatch(batch_id);
    }

    @Override
    public Consent_formsDTO getConsentByStudentId(int studentId) {
        return TransferModelsDTO.MappingConsent(consent_formsRepo.getConsentByStudentId(studentId));
    }
}
