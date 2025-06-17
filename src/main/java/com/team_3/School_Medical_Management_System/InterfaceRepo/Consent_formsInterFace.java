package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Consent_forms;

import java.util.List;

public interface Consent_formsInterFace {
    public List<Consent_forms> getConsent_forms();
    public Consent_forms addConsent_forms(Consent_forms consent_forms);
    public List<Consent_forms> getConsent_formsByParentName(String fullName);
    public List<Consent_forms>  getConsent_formsIsAgree(int batch_id);
}
