package com.team_3.School_Medical_Management_System.Controller;


import com.team_3.School_Medical_Management_System.DTO.Consent_formsDTO;
import com.team_3.School_Medical_Management_System.DTO.Consent_formsRequestDTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccination_scheduleDTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_batchesDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Consent_formsServiceInterFace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Consent_forms")
public class Consent_formsController {

    private Consent_formsServiceInterFace consent_formsServiceInterFace;
    @Autowired
    public Consent_formsController(Consent_formsServiceInterFace consent_formsServiceInterFace) {
        this.consent_formsServiceInterFace = consent_formsServiceInterFace;
    }

    @GetMapping
    public List<Consent_formsDTO>  getConsent_forms() {
        var consent_forms = consent_formsServiceInterFace.getConsent_forms();
        return consent_forms;
    }

    @GetMapping("/consent_formsParentName")
    public List<Consent_formsDTO> getConsent_forms_by_parent(@RequestParam String Consent_formsParentName) {
        var consent_forms = consent_formsServiceInterFace.getConsent_formsByParentName(Consent_formsParentName);
        return consent_forms;
    }



    @PostMapping
    public ResponseEntity<Consent_formsDTO> postConsent_forms(@RequestBody Consent_formsRequestDTO consentFormsRequestDTO) {
        // Gọi Service để xử lý logic thêm phiếu tiêm
        Consent_formsDTO result = consent_formsServiceInterFace.addConsent_forms(consentFormsRequestDTO);
        // Trả về ResponseEntity với status 201 (Created) và DTO
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/consent-info")
    public Consent_formsDTO getConsentInfo(
            @RequestParam int studentId,
            @RequestParam int scheduleId) {
        Consent_formsDTO dto = consent_formsServiceInterFace.getConsent_formsByInfor(studentId, scheduleId);
        return dto;
    }

    @GetMapping("/approved/{id}")
    public List<Consent_formsDTO> getParentIsAgree(@PathVariable int id) {
        var listArgee = consent_formsServiceInterFace.getConsent_formsIsAgree(id);
        if (listArgee == null) {
            return null;
        }else {
            return listArgee;
        }
    }











}
