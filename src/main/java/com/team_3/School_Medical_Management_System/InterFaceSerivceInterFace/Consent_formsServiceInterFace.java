package com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace;

import com.team_3.School_Medical_Management_System.DTO.ConsentFormParentResponseDTO;
import com.team_3.School_Medical_Management_System.DTO.Consent_formViewDTO;
import com.team_3.School_Medical_Management_System.DTO.Consent_formsDTO;
import com.team_3.School_Medical_Management_System.DTO.ParentConfirmDTO;

import java.util.List;

public interface Consent_formsServiceInterFace {
    public List<Consent_formViewDTO> getConsent_forms();
    public Consent_formsDTO addConsent_forms(Consent_formsDTO dto);
    public List<Consent_formsDTO>  getConsent_formsByParentName(String fullName);
    public Consent_formsDTO getConsentFormForParent(Integer consentFormId);
    public List<Consent_formsDTO> getConsent_formsIsAgree(String dot);
    public List<Consent_formsDTO>  getConsent_formsClass(String class_name);
    public void parentConfirm(ParentConfirmDTO dto);
    public Long countConsentFormsIsAgreeByBatch(String dot);
    public Long countConsentFormsDisAgreeByBatch(String dot);
    public Long countConsentFormsPendingByBatch(String dot);
    Consent_formsDTO getConsentByStudentId(int studentId);
    public List<Consent_formViewDTO> findPendingForParent();
    public void processParentResponse(ConsentFormParentResponseDTO dto);
}

