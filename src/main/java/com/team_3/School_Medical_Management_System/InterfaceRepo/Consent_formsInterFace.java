package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.DTO.Consent_form_dot;
import com.team_3.School_Medical_Management_System.Model.Consent_forms;


import java.util.List;

public interface Consent_formsInterFace {
    public List<Consent_forms> getConsent_forms();
    public Consent_forms addConsent_forms(Consent_forms consent_forms);
    public List<Consent_forms> getConsent_formsByParentName(String fullName);
    public List<Consent_forms>  getConsent_formsIsAgree(String dot);
    public List<Consent_forms>  getConsent_formsClass(String class_name);
    public Consent_forms getConsent_formsById(Integer consentFormId);
    public Consent_forms updateConsent_forms(Consent_forms consent_forms);
    public Long countConsentFormsIsAgreeByBatch(String dot);
    public Long countConsentFormsDisAgreeByBatch(String dot);
    public Long countConsentFormsPendingByBatch(String dot);
    List<Consent_forms>  getConsentByStudentId(int studentId);
    public List<Consent_forms> findPendingForParent(int parentId);
    public List<Consent_forms> getAllConsentForms();
    public Consent_forms updateConsent(Consent_forms consent_forms);
    public List<Consent_forms> getStudentConsentForms(String class_name);
    public List<Consent_form_dot> findDot();







}
