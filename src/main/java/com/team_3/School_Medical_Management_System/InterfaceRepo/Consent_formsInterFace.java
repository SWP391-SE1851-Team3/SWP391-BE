package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Consent_forms;
import com.team_3.School_Medical_Management_System.Model.StudentHealthProfile;

import java.util.List;

public interface Consent_formsInterFace {
    public List<Consent_forms> getConsent_forms();
    public Consent_forms addConsent_forms(Consent_forms consent_forms);
    public List<Consent_forms> getConsent_formsByParentName(String fullName);
    public List<Consent_forms>  getConsent_formsIsAgree(int batch_id);
    public List<Consent_forms>  getConsent_formsClass(String class_name);
    public Consent_forms getConsent_formsById(int consentFormId);
    public Consent_forms updateConsent_forms(Consent_forms consent_forms);
    public Long countConsentFormsIsAgreeByBatch(int batch_id);
    public Long countConsentFormsDisAgreeByBatch(int batch_id);
    public Long countConsentFormsPendingByBatch(int batch_id);
    Consent_forms getConsentByStudentId(int studentId);


}
