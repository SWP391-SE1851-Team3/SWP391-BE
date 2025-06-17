package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.ConfirmMedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.ConfirmMedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medication-confirmations")
public class MedicationConfirmationController {

    @Autowired
    private ConfirmMedicationSubmissionServiceInterface confirmService;

    @PutMapping("/{confirmId}/status/approved")
    public ResponseEntity<ConfirmMedicationSubmissionDTO> approvedConfirmationStatus(
            @PathVariable int confirmId,
            @RequestParam ConfirmMedicationSubmission.confirmMedicationSubmissionStatus APPROVED) {
        ConfirmMedicationSubmissionDTO updatedConfirmation = confirmService.updateConfirmationStatus(confirmId, APPROVED);
        if (updatedConfirmation != null) {
            return new ResponseEntity<>(updatedConfirmation, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{confirmId}/status/rejected")
    public ResponseEntity<ConfirmMedicationSubmissionDTO> rejectedConfirmationStatus(
            @PathVariable int confirmId,
            @RequestParam(required = true) String reason,
            @RequestParam ConfirmMedicationSubmission.confirmMedicationSubmissionStatus REJECTED) {
        // First validate that reason isn't empty
        if (reason == null || reason.trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Now update the confirmation status with the reason
        ConfirmMedicationSubmissionDTO updatedConfirmation = confirmService.updateConfirmationStatusWithReason(
                confirmId, REJECTED, reason);

        if (updatedConfirmation != null) {
            return new ResponseEntity<>(updatedConfirmation, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{confirmId}/status/administered")
    public ResponseEntity<ConfirmMedicationSubmissionDTO> administeredConfirmationStatus(
            @PathVariable int confirmId,
            @RequestParam ConfirmMedicationSubmission.confirmMedicationSubmissionStatus ADMINISTERED) {
        ConfirmMedicationSubmissionDTO updatedConfirmation = confirmService.updateConfirmationStatus(confirmId, ADMINISTERED);
        if (updatedConfirmation != null) {
            return new ResponseEntity<>(updatedConfirmation, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{confirmId}")
    public ResponseEntity<ConfirmMedicationSubmissionDTO> getConfirmationById(@PathVariable int confirmId) {
        ConfirmMedicationSubmissionDTO confirmation = confirmService.getConfirmationById(confirmId);
        if (confirmation != null) {
            return new ResponseEntity<>(confirmation, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-submission/{submissionId}")
    public ResponseEntity<ConfirmMedicationSubmissionDTO> getConfirmationBySubmissionId(@PathVariable int submissionId) {
        ConfirmMedicationSubmissionDTO confirmation = confirmService.getConfirmationBySubmissionId(submissionId);
        if (confirmation != null) {
            return new ResponseEntity<>(confirmation, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-nurse/{nurseId}")
    public ResponseEntity<List<ConfirmMedicationSubmissionDTO>> getConfirmationsByNurse(@PathVariable int nurseId) {
        List<ConfirmMedicationSubmissionDTO> confirmations = confirmService.getConfirmationsByNurse(nurseId);
        return new ResponseEntity<>(confirmations, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ConfirmMedicationSubmissionDTO>> getAllConfirmations() {
        List<ConfirmMedicationSubmissionDTO> confirmations = confirmService.getAllConfirmations();
        return new ResponseEntity<>(confirmations, HttpStatus.OK);
    }

    @GetMapping("/confirm-medication-dashboard")
    public String medicationDashboard(Model model, @RequestParam(required = false) Integer nurseId) {
        if (nurseId == null) {
            // In a real app, get this from the session or authentication context
            nurseId = 1; // Default for testing
        }

        // Get pending medication submissions
        List<ConfirmMedicationSubmissionDTO> pedingConfirmations = confirmService.getAllConfirmations().stream()
                .filter(c -> c.getStatus() == ConfirmMedicationSubmission.confirmMedicationSubmissionStatus.PENDING )
                .collect(Collectors.toList());
        model.addAttribute("pendingConfirmations", pedingConfirmations);

        //Get rejected confirmations
        List<ConfirmMedicationSubmissionDTO> rejectedConfirmations = confirmService.getAllConfirmations().stream()
                .filter(c -> c.getStatus() == ConfirmMedicationSubmission.confirmMedicationSubmissionStatus.REJECTED )
                .collect(Collectors.toList());
        model.addAttribute("rejectedConfirmations", rejectedConfirmations);

        // Get approved confirmations
        List<ConfirmMedicationSubmissionDTO> approvedConfirmations = confirmService.getAllConfirmations().stream()
                .filter(c -> c.getStatus() == ConfirmMedicationSubmission.confirmMedicationSubmissionStatus.APPROVED )
                .collect(Collectors.toList());
        model.addAttribute("approvedConfirmations", approvedConfirmations);

        // Get administered confirmations
        List<ConfirmMedicationSubmissionDTO> administeredConfirmations = confirmService.getAllConfirmations().stream()
                .filter(c -> c.getStatus() == ConfirmMedicationSubmission.confirmMedicationSubmissionStatus.ADMINISTERED )
                .collect(Collectors.toList());
        model.addAttribute("administeredConfirmations", administeredConfirmations);

        model.addAttribute("nurseId", nurseId);

        return "medication-submission--confirmation";
    }
}
