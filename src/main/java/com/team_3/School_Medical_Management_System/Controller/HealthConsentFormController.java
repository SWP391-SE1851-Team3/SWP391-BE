package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.ConsentFormRequestDTO;
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


    // Get all consent forms
    @GetMapping("/all")
    public ResponseEntity<List<HealthConsentFormDTO>> getAllConsentForms() {
        try {
            List<HealthConsentForm> forms = healthConsentFormService.getAllConsentForms();
            List<HealthConsentFormDTO> dtos = forms.stream()
                    .map(healthConsentFormService::convertToDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update consent form with parent's decision
    @PutMapping("/{formId}")
    public ResponseEntity<HealthConsentFormDTO> updateConsentForm(
            @PathVariable int formId,
            @RequestParam String isAgreed,
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

    // Get all consent forms by parentId
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<HealthConsentFormDTO>> getConsentFormsByParentId(@PathVariable Integer parentId) {
        List<HealthConsentForm> forms = healthConsentFormService.getConsentFormsByParentId(parentId);
        List<HealthConsentFormDTO> dtos = forms.stream()
                .map(healthConsentFormService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/create-for-class")
    public ResponseEntity<Void> createConsentFormsForClass(@RequestBody ConsentFormRequestDTO request) {
        try {
            healthConsentFormService.createConsentFormsForClass(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-class/{className}")
    public ResponseEntity<List<HealthConsentForm>> getConsentFormsByClass(@PathVariable String className) {
        try {
            List<HealthConsentForm> forms = healthConsentFormService.getConsentFormsByClass(className);
            return new ResponseEntity<>(forms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-class-and-schedule/{className}/{scheduleId}")
    public ResponseEntity<List<HealthConsentForm>> getConsentFormsByClassAndSchedule(
            @PathVariable String className,
            @PathVariable Integer scheduleId) {
        try {
            List<HealthConsentForm> forms = healthConsentFormService.getConsentFormsByClassAndSchedule(className, scheduleId);
            return new ResponseEntity<>(forms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/accepted/{scheduleId}")
    public ResponseEntity<List<HealthConsentForm>> getAcceptedConsentForms(@PathVariable Integer scheduleId) {
        try {
            List<HealthConsentForm> forms = healthConsentFormService.getAcceptedConsentForms(scheduleId);
            return new ResponseEntity<>(forms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rejected/{scheduleId}")
    public ResponseEntity<List<HealthConsentForm>> getRejectedConsentForms(@PathVariable Integer scheduleId) {
        try {
            List<HealthConsentForm> forms = healthConsentFormService.getRejectedConsentForms(scheduleId);
            return new ResponseEntity<>(forms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
