package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.HealthConsentFormDTO;
import com.team_3.School_Medical_Management_System.Model.HealthConsentForm;
import com.team_3.School_Medical_Management_System.Service.HealthConsentFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/health-consent")
public class HealthConsentFormController {

    @Autowired
    private HealthConsentFormService healthConsentFormService;

    // Get pending consent forms for a parent
    @GetMapping("/parent/{parentId}/pending")
    public ResponseEntity<List<HealthConsentFormDTO>> getPendingConsentFormsByParent(@PathVariable int parentId) {
        List<HealthConsentForm> forms = healthConsentFormService.getPendingConsentFormsByParent(parentId);
        List<HealthConsentFormDTO> formDTOs = forms.stream()
                .map(healthConsentFormService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(formDTOs, HttpStatus.OK);
    }

    // Update consent form with parent's decision
    @PutMapping("/{formId}")
    public ResponseEntity<HealthConsentFormDTO> updateConsentForm(
            @PathVariable int formId,
            @RequestParam boolean isAgreed,
            @RequestParam(required = false) String notes) {

        HealthConsentForm updatedForm = healthConsentFormService.updateConsentForm(formId, isAgreed, notes);

        if (updatedForm != null) {
            return new ResponseEntity<>(healthConsentFormService.convertToDTO(updatedForm), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all consent forms for a specific health check schedule
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<HealthConsentFormDTO>> getConsentFormsBySchedule(@PathVariable int scheduleId) {
        List<HealthConsentForm> forms = healthConsentFormService.getConsentFormsBySchedule(scheduleId);
        List<HealthConsentFormDTO> formDTOs = forms.stream()
                .map(healthConsentFormService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(formDTOs, HttpStatus.OK);
    }

    // Get all agreed consent forms for a specific health check schedule
    @GetMapping("/schedule/{scheduleId}/agreed")
    public ResponseEntity<List<HealthConsentFormDTO>> getAgreedConsentFormsBySchedule(@PathVariable int scheduleId) {
        List<HealthConsentForm> forms = healthConsentFormService.getAgreedConsentFormsBySchedule(scheduleId);
        List<HealthConsentFormDTO> formDTOs = forms.stream()
                .map(healthConsentFormService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(formDTOs, HttpStatus.OK);
    }
}
