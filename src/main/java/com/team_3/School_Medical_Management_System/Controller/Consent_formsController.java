package com.team_3.School_Medical_Management_System.Controller;


import com.team_3.School_Medical_Management_System.DTO.*;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Consent_formsServiceInterFace;
import com.team_3.School_Medical_Management_System.Model.Consent_forms;
import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping("/viewParent")
    public List<Consent_formViewDTO>  getConsent_forms() {
        var consent_forms = consent_formsServiceInterFace.getConsent_forms();
        return consent_forms;
    }
    @GetMapping("/viewNurse")
    public List<Consent_formsDTO>  getConsent_forms_nurse() {
        var listConset = consent_formsServiceInterFace.getAllConsentForms();
        return  listConset;
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
        System.out.println("Received dot: '" + dot + "'");
        Long count = consent_formsServiceInterFace.countConsentFormsPendingByBatch(dot);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/byStudentId/{studentId}")
    public List<Consent_formViewDTO>  getConsent_formsByStudentId(@PathVariable int studentId) {
        return consent_formsServiceInterFace.getConsentByStudentId(studentId);
    }

    @GetMapping("/Consent_forms/parent-pending")
    public ResponseEntity<?> getFormsPendingForParent(int parentId){
        List<Consent_formViewDTO> forms = consent_formsServiceInterFace.findPendingForParent(parentId);
        List<Map<String, Object>> result = forms.stream().map(form -> {
            Map<String, Object> map = new HashMap<>();
            map.put("FullNameOfStudent",form .getFullNameOfStudent());
            map.put("VaccinesName", form.getVaccineName());
            map.put("ParentName", form.getFullNameOfParent());
            map.put("location", form.getLocalDate());
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

    @PutMapping("/Consent_forms/parent-response/{id}")
    public ResponseEntity<?> updateByParent(@PathVariable int id, @RequestBody @Valid ConsentFormParentResponseDTO dto) {
        try {
           dto.setConsentFormId(id);
           if(consent_formsServiceInterFace.getConsentFormForParent(id) == null) {
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }

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

    @PutMapping("/editConsent/nurse/{id}")
    public ResponseEntity<?> updatebyNurse(@PathVariable int id, @RequestBody Consent_formsDTO form ) {
        try {
            form.setConsent_id(id);
            if(consent_formsServiceInterFace.getConsentFormForParent(id) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                consent_formsServiceInterFace.updateConsent(form);
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Cập nhật thành công"
                ));
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/consent-forms/send-by-classname")
    public ResponseEntity<?> sendByClass(@RequestBody SendConsentFormRequestDTO dto) {
        try {
            consent_formsServiceInterFace.sendConsentFormsByClassName(
                    dto.getClassName(),
                    dto.getBatchId(),
                    dto.getSendDate(),
                    dto.getExpireDate(),
                    dto.getStatus()
            );
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Đã gửi phiếu cho lớp " + dto.getClassName()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/findDot")
    @Operation(summary = "liên quan đến đợt")
    public ResponseEntity<?> findDot() {
        var dot = consent_formsServiceInterFace.findDot();
        if(dot == null || dot.isEmpty()) {
            return ResponseEntity.noContent().build(); // hoặc trả về 204
        } else {
            return ResponseEntity.ok(dot);
        }
    }



}
