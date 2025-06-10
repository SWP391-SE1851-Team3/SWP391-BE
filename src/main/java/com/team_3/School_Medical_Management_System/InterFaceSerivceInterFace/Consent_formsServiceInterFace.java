package com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace;

import com.team_3.School_Medical_Management_System.DTO.Consent_formsDTO;
import com.team_3.School_Medical_Management_System.DTO.Consent_formsRequestDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.Consent_formsInterFace;
import com.team_3.School_Medical_Management_System.Model.Consent_forms;

import java.util.List;

public interface Consent_formsServiceInterFace {
    public List<Consent_formsDTO> getConsent_forms();
    public Consent_formsDTO addConsent_forms(Consent_formsRequestDTO dto);
    public List<Consent_formsDTO>  getConsent_formsByParentName(String fullName);
    public Consent_formsDTO  getConsent_formsByInfor(int studentId, int scheduleId);
    public List<Consent_formsDTO> getConsent_formsIsAgree();

}
