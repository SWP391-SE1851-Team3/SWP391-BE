package com.team_3.School_Medical_Management_System.InterFaceSerivce;

import com.team_3.School_Medical_Management_System.DTO.ConsentFormRequestDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthConsentFormDTO;
import com.team_3.School_Medical_Management_System.Model.HealthConsentForm;

import java.util.List;

public interface HealthCheckConsentFormServiceInterFace {

    // Update consent form with parent's decision
    HealthConsentForm updateConsentForm(int formId, String isAgreed, String notes);

    // Get all consent forms for a specific health check schedule
    List<HealthConsentForm> getConsentFormsBySchedule(int scheduleId);

    // Get all agreed consent forms for a specific health check schedule
    List<HealthConsentForm> getAgreedConsentFormsBySchedule(int scheduleId);

    // Get all consent forms by parentId
    List<HealthConsentForm> getConsentFormsByParentId(Integer parentId);

    // Get all consent forms
    List<HealthConsentForm> getAllConsentForms();

    // Convert Entity to DTO
    HealthConsentFormDTO convertToDTO(HealthConsentForm form);

    // Create consent forms for multiple classes
    void createConsentFormsForMultipleClasses(ConsentFormRequestDTO request);

    // Get consent forms by class and schedule
    List<HealthConsentForm> getConsentFormsByClass(String className, int scheduleId);

    // Get consent forms by class name and schedule ID
    List<HealthConsentForm> getConsentFormsByClassAndSchedule(String className, Integer scheduleId);

    // Get accepted consent forms
    List<HealthConsentForm> getAcceptedConsentForms(Integer scheduleId);

    // Get rejected consent forms
    List<HealthConsentForm> getRejectedConsentForms(Integer scheduleId);
}
