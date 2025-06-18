package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.ConfirmMedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.DTO.StudentMappingParent;
import com.team_3.School_Medical_Management_System.DTO.MedicationSubmissionInfoDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.ConfirmMedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.MedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.SchoolNurseServiceInterFace;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.StudentServiceInterFace;
import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;
import com.team_3.School_Medical_Management_System.Model.MedicationDetail;
import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;
import com.team_3.School_Medical_Management_System.Model.Student;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medication-submission")
public class MedicationSubmissionController {

    @Autowired
    private MedicationSubmissionServiceInterface medicationSubmissionService;

    @Autowired
    private ConfirmMedicationSubmissionServiceInterface confirmService;

    @Autowired
    private StudentServiceInterFace studentService;

    @Autowired
    private SchoolNurseServiceInterFace nurseService;

    @GetMapping("/children/{parentID}")
    public ResponseEntity<List<StudentMappingParent>> getChildrenByParentId(@PathVariable int parentID) {
        List<StudentMappingParent> children = studentService.getStudentsByParentID(parentID);
        return new ResponseEntity<>(children, HttpStatus.OK);
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitMedication(@Valid @RequestBody MedicationSubmissionDTO medicationSubmissionDTO) {
        try {
            // Kiểm tra xem studentId có tồn tại không
            Student student = studentService.getStudent(medicationSubmissionDTO.getStudentId());
            if (student == null) {
                return new ResponseEntity<>("Student with ID " + medicationSubmissionDTO.getStudentId() + " does not exist",
                        HttpStatus.BAD_REQUEST);
            }

            MedicationSubmission submission = medicationSubmissionService.submitMedication(medicationSubmissionDTO);
            return new ResponseEntity<>(submission, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error submitting medication: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cancel/{submissionId}")
    public ResponseEntity<?> cancelMedicationSubmission(@PathVariable int submissionId) {
        try {
            medicationSubmissionService.cancelMedicationSubmission(submissionId);
            return new ResponseEntity<>("Medication submission cancelled successfully", HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error cancelling medication submission", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/submissions/{submissionId}/details")
    public ResponseEntity<List<MedicationDetail>> getSubmissionDetails(@PathVariable int submissionId) {
        List<MedicationDetail> details = medicationSubmissionService.getDetailsBySubmissionId(submissionId);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @GetMapping("/submissions/{parentId}")
    public ResponseEntity<List<MedicationSubmission>> getMedicationSubmissions(@PathVariable int parentId) {
        List<MedicationSubmission> submissions = medicationSubmissionService.getAllMedicationSubmissionsByParentId(parentId);
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }


    @PostMapping("/confirm-medication")
    @Operation(summary = "thêm nurse")
    public ResponseEntity<?> confirmMedication(
            @RequestParam int medicationSubmissionId,
            RedirectAttributes redirectAttributes) {
        // Lấy thông tin xác nhận từ confirmMedicationSubmissionService
        ConfirmMedicationSubmissionDTO confirmDTO = confirmService.getConfirmationBySubmissionId(medicationSubmissionId);
        if (confirmDTO == null) {
            return ResponseEntity.badRequest().body("No confirmation found for this medication submission id.");
        }

        // Lấy status và nurseId từ confirmDTO
        String status = confirmDTO.getStatus();
        Integer nurseId = confirmDTO.getNurseId();
        String reason = confirmDTO.getReason();
        Integer confirmId = confirmDTO.getConfirmId();
        String evidence = confirmDTO.getEvidence();

        // Lấy thông tin submission
        MedicationSubmission submission = medicationSubmissionService.getMedicationSubmissionById(medicationSubmissionId);
        String submissionDate = null;
        if (submission != null && submission.getSubmissionDate() != null) {
            submissionDate = submission.getSubmissionDate().toString();
        }

        // Kiểm tra logic như cũ
        if (status == null || status.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Status is required.");
        }
        if ("reject".equalsIgnoreCase(status) && (reason == null || reason.trim().isEmpty())) {
            return ResponseEntity.badRequest().body("Reason is required when status is 'reject'.");
        }
        if ("ADMINISTERED".equalsIgnoreCase(status) && nurseId == null) {
            return ResponseEntity.badRequest().body("NurseId is required when status is 'ADMINISTERED'.");
        }

        confirmDTO.setStatus(status);
        if (reason != null) {
            confirmDTO.setReason(reason);
        }

        confirmService.createConfirmation(confirmDTO);

        // Lấy tên nurse nếu có nurseId
        String nurseName = null;
        if (nurseId != null) {
            nurseName = nurseService.getNurseNameById(nurseId);
        }

        // Tạo response object chứa đầy đủ thông tin
        Map<String, Object> response = new HashMap<>();
        response.put("confirmationId", confirmId);
        response.put("status", status);
        response.put("nurseName", nurseName);
        response.put("reason", reason);
        response.put("evidence", evidence);
        response.put("submissionId", medicationSubmissionId);
        response.put("submissionDate", submissionDate);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/submissions-info")
    public ResponseEntity<List<MedicationSubmissionInfoDTO>> getAllMedicationSubmissionInfo() {
        List<MedicationSubmissionInfoDTO> infoList = medicationSubmissionService.getAllMedicationSubmissionInfo();
        return new ResponseEntity<>(infoList, HttpStatus.OK);
    }

    @GetMapping("/submissions-info/parent/{parentId}")
    public ResponseEntity<List<MedicationSubmissionInfoDTO>> getMedicationSubmissionInfoByParentId(@PathVariable int parentId) {
        List<MedicationSubmissionInfoDTO> infoList = medicationSubmissionService.getMedicationSubmissionInfoByParentId(parentId);
        return new ResponseEntity<>(infoList, HttpStatus.OK);
    }
}
