package com.team_3.School_Medical_Management_System.Controller;


import com.team_3.School_Medical_Management_System.DTO.Consent_formsDTO;
import com.team_3.School_Medical_Management_System.DTO.Consent_formsRequestDTO;
import com.team_3.School_Medical_Management_System.DTO.ParentConfirmDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Consent_formsServiceInterFace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Consent_forms")
@CrossOrigin(origins = "http://localhost:5173")
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
    public ResponseEntity<Consent_formsDTO> postConsent_forms(@RequestBody Consent_formsDTO consentFormsDTO) {
        // Gọi Service để xử lý logic thêm phiếu tiêm
        Consent_formsDTO result = consent_formsServiceInterFace.addConsent_forms(consentFormsDTO);
        // Trả về ResponseEntity với status 201 (Created) và DTO
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/consent-info")
    public Consent_formsRequestDTO getConsentInfo(
            @RequestParam int consent_form_id) {
        Consent_formsRequestDTO dto = consent_formsServiceInterFace.getConsentFormForParent(consent_form_id);
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

    @GetMapping("/Consent_forms/{class_name}")
    public List<Consent_formsDTO> getConsent_formsByClass(@PathVariable String class_name) {
        return consent_formsServiceInterFace.getConsent_formsClass(class_name);
    }

    @PostMapping("/consent-forms/parent-confirm")
    public ResponseEntity<?> parentConfirm(@RequestBody ParentConfirmDTO dto) {
        consent_formsServiceInterFace.parentConfirm(dto);
        return ResponseEntity.ok("Phụ huynh xác nhận thành công");
    }

    @GetMapping("/ConsentFormsIsAgree/{batch_id}")
    public ResponseEntity<Long> countConsentFormsIsAgreeByBatch(@PathVariable int batch_id) {
        Long count = consent_formsServiceInterFace.countConsentFormsIsAgreeByBatch(batch_id);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/ConsentFormsDisAgree/{batch_id}")
    public ResponseEntity<Long> countConsentFormsDisAgreeByBatch(@PathVariable int batch_id) {
        Long count  = consent_formsServiceInterFace.countConsentFormsDisAgreeByBatch(batch_id);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/ConsentFormsPending/{batch_id}")
    public ResponseEntity<Long> countConsentFormsPendingByBatch(@PathVariable int batch_id) {
        Long count = consent_formsServiceInterFace.countConsentFormsDisAgreeByBatch(batch_id);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/byStudentId/{studentId}")
    public Consent_formsDTO getConsent_formsByStudentId(@PathVariable int studentId) {
        return consent_formsServiceInterFace.getConsentByStudentId(studentId);
    }

























}
