package com.team_3.School_Medical_Management_System.InterFaceSerivce;

import com.team_3.School_Medical_Management_System.DTO.*;

import java.time.LocalDateTime;
import java.util.List;

public interface Consent_formsServiceInterFace {
    public List<Consent_formViewDTO> getConsent_forms();
    public Consent_formsDTO addConsent_forms(Consent_formsDTO dto);
    public List<Consent_formsDTO>  getConsent_formsByParentName(String fullName);
    public Consent_formViewDTO getConsentFormForParent(Integer consentFormId);
    public List<Consent_formsDTO> getConsent_formsIsAgree(String dot);
    public List<Consent_formsDTO>  getConsent_formsClass(String class_name);
    public void parentConfirm(ParentConfirmDTO dto);
    public Long countConsentFormsIsAgreeByBatch(String dot);
    public Long countConsentFormsDisAgreeByBatch(String dot);
    public Long countConsentFormsPendingByBatch(String dot);
    List<Consent_formViewDTO>  getConsentByStudentId(int studentId);
    public List<Consent_formViewDTO> findPendingForParent(int parentId);
    public void processParentResponse(ConsentFormParentResponseDTO dto);
    public List<Consent_formViewDTO> getAllConsentForms();
    public Consent_formsDTO updateConsent(Consent_formsDTO consentFormsDTO);
    public void  sendConsentFormsByClassName(List<String> className, Integer batchId, LocalDateTime sendDate, LocalDateTime expireDate,String status);
    public List<Consent_form_dot> findDot();
    public List<Consent_formsDTO> getIsAgree();
    public List<Consent_formsDTO> getDisAgree();
    public List<Consent_formsDTO> getStatus();
    public List<Consent_formsDTO> getDisStatus();
}

