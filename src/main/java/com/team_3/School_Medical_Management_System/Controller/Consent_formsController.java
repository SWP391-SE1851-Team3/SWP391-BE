package com.team_3.School_Medical_Management_System.Controller;


import com.team_3.School_Medical_Management_System.DTO.ConsentFormParentResponseDTO;
import com.team_3.School_Medical_Management_System.DTO.Consent_formsDTO;
import com.team_3.School_Medical_Management_System.DTO.ParentConfirmDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Consent_formsServiceInterFace;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Consent_formsDTO result = consent_formsServiceInterFace.addConsent_forms(consentFormsDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/consent-info")
    public Consent_formsDTO getConsentInfo(
            @RequestParam int consent_form_id) {
        Consent_formsDTO dto = consent_formsServiceInterFace.getConsentFormForParent(consent_form_id);
        return dto;
    }

    @GetMapping("/approved/{dot}")
    public List<Consent_formsDTO> getParentIsAgree(@PathVariable String dot) {
        var listArgee = consent_formsServiceInterFace.getConsent_formsIsAgree(dot);
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

    @GetMapping("/ConsentFormsIsAgree/{dot}")
    public ResponseEntity<Long> countConsentFormsIsAgreeByBatch(@PathVariable String dot) {
        Long count = consent_formsServiceInterFace.countConsentFormsIsAgreeByBatch(dot);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/ConsentFormsDisAgree/{dot}")
    public ResponseEntity<Long> countConsentFormsDisAgreeByBatch(@PathVariable String dot) {
        Long count  = consent_formsServiceInterFace.countConsentFormsDisAgreeByBatch(dot);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/ConsentFormsPending/{dot}")
    public ResponseEntity<Long> countConsentFormsPendingByBatch(@PathVariable String dot) {
        Long count = consent_formsServiceInterFace.countConsentFormsDisAgreeByBatch(dot);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/byStudentId/{studentId}")
    public Consent_formsDTO getConsent_formsByStudentId(@PathVariable int studentId) {
        return consent_formsServiceInterFace.getConsentByStudentId(studentId);
    }

    @GetMapping("/Consent_forms/parent-pending")
    public ResponseEntity<?> getFormsPendingForParent(){
        List<Consent_formsDTO> forms = consent_formsServiceInterFace.findPendingForParent();
        List<Map<String, Object>> result = forms.stream().map(form -> {
            Map<String, Object> map = new HashMap<>();
            map.put("consent_forms_id", form.getConsent_forms_id());// Trả về dữ liệu tự do , và tự định nghĩa field
            map.put("fullNameOfStudent", form.getFullNameOfStudent());
            map.put("className", form.getClassName());
            map.put("vaccineName", form.getName());
            map.put("notes", form.getNotes());
            map.put("fullnameOfParent", form.getFullnameOfParent());
            map.put("location", form.getLocation());
            map.put("scheduledDate", form.getScheduledDate());
            map.put("send_date", form.getSend_date());
            map.put("expire_date", form.getExpire_date());
            map.put("isAgree", null);
            map.put("reason", "");
            map.put("hasAllergy", "");
            return map;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PutMapping("/Consent_forms/parent-response")
    public ResponseEntity<?> updateByParent(@RequestBody @Valid ConsentFormParentResponseDTO dto) {
        try {
            consent_formsServiceInterFace.processParentResponse(dto);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Phản hồi thành công"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Lỗi máy chủ: " + e.getMessage()
            ));
        }
    }
}
