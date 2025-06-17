package com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace;

import com.team_3.School_Medical_Management_System.DTO.Consent_formsDTO;
import com.team_3.School_Medical_Management_System.DTO.Consent_formsRequestDTO;
import com.team_3.School_Medical_Management_System.DTO.ParentConfirmDTO;
import com.team_3.School_Medical_Management_System.Model.Consent_forms;

import java.util.List;

public interface Consent_formsServiceInterFace {
    public List<Consent_formsDTO> getConsent_forms();
    public Consent_formsDTO addConsent_forms(Consent_formsDTO dto);
    public List<Consent_formsDTO>  getConsent_formsByParentName(String fullName);
    public Consent_formsRequestDTO getConsentFormForParent(int consentFormId);
    public List<Consent_formsDTO>  getConsent_formsIsAgree(int batch_id);
    public List<Consent_formsDTO>  getConsent_formsClass(String class_name);
    public void parentConfirm(ParentConfirmDTO dto);
    public Long countConsentFormsIsAgreeByBatch(int batch_id);
    public Long countConsentFormsDisAgreeByBatch(int batch_id);
    public Long countConsentFormsPendingByBatch(int batch_id);
    Consent_formsDTO getConsentByStudentId(int studentId);
}

