package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.ConsentFormRequestDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthConsentFormDTO;
import com.team_3.School_Medical_Management_System.Model.HealthConsentForm;
import com.team_3.School_Medical_Management_System.Service.HealthConsentFormService;
import io.swagger.v3.oas.annotations.Operation;
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


    @GetMapping("/all")
    @Operation(summary = "Lấy tất cả các mẫu đơn đồng ý về sức khỏe")
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

    // Cập nhật mẫu đơn đồng ý theo quyết định của phụ huynh
    @PutMapping("/{formId}")
    @Operation(summary = "Cập nhật mẫu đơn đồng ý về sức khỏe theo ID")
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

    // Lấy tất cả các mẫu đơn đồng ý cho một lịch khám sức khỏe cụ thể
    @GetMapping("/schedule/{scheduleId}")
    @Operation(summary = "Lấy tất cả các mẫu đơn đồng ý cho một lịch khám sức khỏe cụ thể")
    public ResponseEntity<List<HealthConsentFormDTO>> getConsentFormsBySchedule(@PathVariable int scheduleId) {
        List<HealthConsentForm> forms = healthConsentFormService.getConsentFormsBySchedule(scheduleId);
        List<HealthConsentFormDTO> formDTOs = forms.stream()
                .map(healthConsentFormService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(formDTOs, HttpStatus.OK);
    }

    // Lấy tất cả các mẫu đơn đã đồng ý cho một lịch khám sức khỏe cụ thể
    @GetMapping("/schedule/{scheduleId}/agreed")
    @Operation(summary = "Lấy tất cả các mẫu đơn đã đồng ý cho một lịch khám sức khỏe cụ thể")
    public ResponseEntity<List<HealthConsentFormDTO>> getAgreedConsentFormsBySchedule(@PathVariable int scheduleId) {
        List<HealthConsentForm> forms = healthConsentFormService.getAgreedConsentFormsBySchedule(scheduleId);
        List<HealthConsentFormDTO> formDTOs = forms.stream()
                .map(healthConsentFormService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(formDTOs, HttpStatus.OK);
    }

    // Lấy tất cả các mẫu đơn đồng ý theo ID phụ huynh
    @GetMapping("/parent/{parentId}")
    @Operation(summary = "Lấy tất cả các mẫu đơn đồng ý theo ID phụ huynh")
    public ResponseEntity<List<HealthConsentFormDTO>> getConsentFormsByParentId(@PathVariable Integer parentId) {
        List<HealthConsentForm> forms = healthConsentFormService.getConsentFormsByParentId(parentId);
        List<HealthConsentFormDTO> dtos = forms.stream()
                .map(healthConsentFormService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // Tạo mẫu đơn đồng ý cho một lớp học
    @PostMapping("/create-for-class")
    @Operation(summary = "Tạo mẫu đơn đồng ý cho một lớp học")
    public ResponseEntity<?> createConsentFormsForClass(@RequestBody ConsentFormRequestDTO request) {
        try {
            healthConsentFormService.createConsentFormsForClass(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + e.getMessage());
        }
    }

    // Lấy mẫu đơn đồng ý theo tên lớp và ID lịch khám sức khỏe
    @GetMapping("/by-class/{className}")
    @Operation(summary = "Lấy mẫu đơn đồng ý theo tên lớp và ID lịch khám sức khỏe")
    public ResponseEntity<List<HealthConsentForm>> getConsentFormsByClass(@PathVariable String className, int health_ScheduleID) {
        try {
            List<HealthConsentForm> forms = healthConsentFormService.getConsentFormsByClass(className, health_ScheduleID);
            return new ResponseEntity<>(forms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lấy mẫu đơn đồng ý theo tên lớp và ID lịch khám
    @GetMapping("/by-class-and-schedule/{className}/{scheduleId}")
    @Operation(summary = "Lấy mẫu đơn đồng ý theo tên lớp và ID lịch khám")
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

    // Lấy tất cả các mẫu đơn đã được chấp nhận cho một lịch khám
    @GetMapping("/accepted/{scheduleId}")
    @Operation(summary = "Lấy tất cả các mẫu đơn đã được chấp nhận cho một lịch khám")
    public ResponseEntity<List<HealthConsentForm>> getAcceptedConsentForms(@PathVariable Integer scheduleId) {
        try {
            List<HealthConsentForm> forms = healthConsentFormService.getAcceptedConsentForms(scheduleId);
            return new ResponseEntity<>(forms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Lấy tất cả các mẫu đơn đã bị từ chối cho một lịch khám
    @GetMapping("/rejected/{scheduleId}")
    @Operation(summary = "Lấy tất cả các mẫu đơn đã bị từ chối cho một lịch khám")
    public ResponseEntity<List<HealthConsentForm>> getRejectedConsentForms(@PathVariable Integer scheduleId) {
        try {
            List<HealthConsentForm> forms = healthConsentFormService.getRejectedConsentForms(scheduleId);
            return new ResponseEntity<>(forms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
