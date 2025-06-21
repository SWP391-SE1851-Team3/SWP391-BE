package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.ConsentFormParentResponseDTO;
import com.team_3.School_Medical_Management_System.DTO.Consent_formsDTO;
import com.team_3.School_Medical_Management_System.DTO.ParentConfirmDTO;
import com.team_3.School_Medical_Management_System.Enum.ConsentFormStatus;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.ConsentFormsRepos;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.ConsentFormsRepository;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Consent_formsServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.Consent_formsInterFace;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.*;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class Consent_formsSerivce implements Consent_formsServiceInterFace {
    private Consent_formsInterFace consent_formsRepo;
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private Vaccine_BatchesRepo vaccinesRepo;

    @Autowired
    private ParentRepo parentRepo;

    @Autowired
    private ConsentFormsRepository consent_forms_repo;

    @Autowired
    private ConsentFormsRepos consent_formsRepos;


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
        Consent_forms  addConsent_add = consent_formsRepo.addConsent_forms(TransferModelsDTO.MappingConsentDTO(dto));
        return TransferModelsDTO.MappingConsent(addConsent_add);
    }


    @Override
    public List<Consent_formsDTO> getConsent_formsByParentName(String fullName) {
        var consent_formsName = consent_formsRepo.getConsent_formsByParentName(fullName);
        return consent_formsName.stream().map(TransferModelsDTO::MappingConsent).collect(Collectors.toList());
    }

    @Override
    public Consent_formsDTO getConsentFormForParent(Integer consentFormId) {
        Consent_forms entity = consent_formsRepo.getConsent_formsById(consentFormId);
        if (entity == null) {
            throw new RuntimeException("Consent Form not found");
        }
        return TransferModelsDTO.MappingConsent(entity);
    }

    @Override
    public List<Consent_formsDTO> getConsent_formsIsAgree(String dot) {
        var listArrgee = consent_formsRepo.getConsent_formsIsAgree(dot);

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
        consent_formsRepo.updateConsent_forms(entity);
    }

    @Override
    public Long countConsentFormsIsAgreeByBatch(String dot) {
        return consent_formsRepo.countConsentFormsIsAgreeByBatch(dot);
    }

    @Override
    public Long countConsentFormsDisAgreeByBatch(String dot)  {
        return consent_formsRepo.countConsentFormsDisAgreeByBatch(dot);
    }


    @Override
    public Long countConsentFormsPendingByBatch(String dot) {
        return consent_formsRepo.countConsentFormsPendingByBatch(dot);
    }

    @Override
    public Consent_formsDTO getConsentByStudentId(int studentId) {
        return TransferModelsDTO.MappingConsent(consent_formsRepo.getConsentByStudentId(studentId));
    }

    @Override
    public List<Consent_formsDTO> findPendingForParent() {
        var consent_forms = consent_formsRepo.findPendingForParent();
        return consent_forms.stream().map(TransferModelsDTO::MappingConsent).collect(Collectors.toList());
    }

    @Override
    public void processParentResponse(ConsentFormParentResponseDTO dto) {
        Consent_forms form = consent_formsRepos.findById(dto.getConsentFormId()).orElse(null);
        if (form == null) {
            throw new IllegalArgumentException("Không tìm thấy phiếu");
        }

        // Validate nếu từ chối mà không ghi lý do
        if (Integer.valueOf(0).equals(dto.getIsAgree()) &&
                (dto.getReason() == null || dto.getReason().trim().isEmpty())) {
            throw new IllegalArgumentException("Vui lòng ghi rõ lý do từ chối");
        }
        form.setIsAgree(dto.getIsAgree());
        form.setReason(dto.getReason());
        form.setHasAllergy(dto.getHasAllergy());
        form.setStatus(ConsentFormStatus.APPROVED);
        consent_formsRepos.save(form);
    }
}
