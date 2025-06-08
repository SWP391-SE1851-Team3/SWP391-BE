package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.ConfirmMedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.DTO.StudentMappingParent;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.ConfirmMedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.MedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.StudentServiceInterFace;
import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;
import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;
import com.team_3.School_Medical_Management_System.Model.Student;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
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

    @GetMapping("/children/{parentID}")
    public ResponseEntity<List<StudentMappingParent>> getChildrenByParentId(@PathVariable int parentID) {
        List<StudentMappingParent> children = studentService.getStudentsByParentID(parentID);
        return new ResponseEntity<>(children, HttpStatus.OK);
    }

    @PostMapping("/submit")
    public ResponseEntity<MedicationSubmission> submitMedication(@Valid @RequestBody MedicationSubmissionDTO medicationSubmissionDTO) {
        MedicationSubmission submission = medicationSubmissionService.submitMedication(medicationSubmissionDTO);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("/submissions/{parentId}")
    public ResponseEntity<List<MedicationSubmission>> getMedicationSubmissions(@PathVariable int parentId) {
        List<MedicationSubmission> submissions = medicationSubmissionService.getAllMedicationSubmissionsByParentId(parentId);
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }
    @GetMapping("/medication-dashboard")
    public String medicationDashboard(Model model, @RequestParam(required = false) Integer nurseId) {
        if (nurseId == null) {
            // In a real app, get this from the session or authentication context
            nurseId = 1; // Default for testing
        }

        // Get pending medication submissions

        // Get approved confirmations (where status=true but not yet administered)
        List<ConfirmMedicationSubmissionDTO> approvedConfirmations = confirmService.getAllConfirmations().stream()
                .filter(c -> c.getStatus() == ConfirmMedicationSubmission.confirmMedicationSubmissionStatus.APPROVED &&
                        c.getReceivedMedicine() == ConfirmMedicationSubmission.confirmMedicationSubmissionReceivedMedicine.YES)
                .collect(Collectors.toList());
        model.addAttribute("approvedConfirmations", approvedConfirmations);

        // Get administered confirmations
        List<ConfirmMedicationSubmissionDTO> administeredConfirmations = confirmService.getAllConfirmations().stream()
                .filter(c -> c.getStatus() == ConfirmMedicationSubmission.confirmMedicationSubmissionStatus.ADMINISTERED &&
                        c.getReceivedMedicine() == ConfirmMedicationSubmission.confirmMedicationSubmissionReceivedMedicine.YES)
                .collect(Collectors.toList());
        model.addAttribute("administeredConfirmations", administeredConfirmations);

        model.addAttribute("nurseId", nurseId);

        return "medication-submission--confirmation";
    }

    @PostMapping("/confirm-medication")
    public String confirmMedication(
            @RequestParam int medicationSubmissionId,
            @RequestParam int nurseId,
            @RequestParam ConfirmMedicationSubmission.confirmMedicationSubmissionStatus status,
            @RequestParam String evidence,
            RedirectAttributes redirectAttributes,
            @RequestParam
            ConfirmMedicationSubmission.confirmMedicationSubmissionReceivedMedicine receivedMedicine) {

        ConfirmMedicationSubmissionDTO confirmDTO = new ConfirmMedicationSubmissionDTO();
        confirmDTO.setMedicationSubmissionId(medicationSubmissionId);
        confirmDTO.setNurseId(nurseId);
        confirmDTO.setStatus(status);
        confirmDTO.setEvidence(evidence);
        confirmDTO.setReceivedMedicine(receivedMedicine);
        confirmDTO.setConfirmedAt(LocalDateTime.now());

        confirmService.createConfirmation(confirmDTO);

        return "redirect:/medication-submission/medication-dashboard";
    }

    @PostMapping("/medication-taken")
    public String medicationTaken(
            @RequestParam int confirmId,
            RedirectAttributes redirectAttributes) {

        ConfirmMedicationSubmissionDTO updatedConfirmation = confirmService.updateMedicationTaken(confirmId, ConfirmMedicationSubmission.confirmMedicationSubmissionReceivedMedicine.YES);

        if (updatedConfirmation != null) {
            redirectAttributes.addFlashAttribute("message", "Medication marked as administered successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update medication status");
        }

        return "redirect:/medication-submission/medication-dashboard";
    }
}


