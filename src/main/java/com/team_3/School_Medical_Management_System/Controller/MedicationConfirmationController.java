
package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.ConfirmMedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.ConfirmMedicationSubmissionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medication-confirmations")
public class MedicationConfirmationController {

    @Autowired
    private ConfirmMedicationSubmissionServiceInterface confirmService;

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

    @PutMapping("/{confirmId}/status")
    public ResponseEntity<?> updateConfirmationStatus(
            @PathVariable int confirmId,
            @RequestBody(required = false) com.team_3.School_Medical_Management_System.DTO.StatusUpdateRequest request) {

        // Validate status is provided for PUT
        if (request == null || request.getStatus() == null) {
            return new ResponseEntity<>("Status is required for updates", HttpStatus.BAD_REQUEST);
        }

        try {
            // No validation for status value - accept any value provided by nurse
            String status = request.getStatus();

            // Get optional reason, evidence, and nurseId if provided
            String reason = request.getReason(); // Can be null
            String evidence = request.getEvidence(); // Can be null

            Integer nurseId = null;
            if (request.getNurseId() != null) {
                nurseId = request.getNurseId();
            }

            // Get the existing confirmation data first
            ConfirmMedicationSubmissionDTO existingConfirmation = confirmService.getConfirmationById(confirmId);
            if (existingConfirmation == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Only update fields that are provided in the request and are not empty strings
            // If a field is empty string or null, keep the existing value
            String finalStatus = (status != null && !status.isEmpty()) ? status : existingConfirmation.getStatus();
            String finalReason = (reason != null && !reason.isEmpty()) ? reason : existingConfirmation.getReason();
            String finalEvidence = (evidence != null && !evidence.isEmpty()) ? evidence : existingConfirmation.getEvidence();

            // For nurseId, only update if a new value was explicitly provided
            Integer finalNurseId = (nurseId != null) ? nurseId : existingConfirmation.getNurseId();

            // Call service to update only the provided non-empty fields
            ConfirmMedicationSubmissionDTO updatedConfirmation =
                    confirmService.updateStatusAndNurse(confirmId, finalStatus, finalReason, finalNurseId, finalEvidence);

            if (updatedConfirmation != null) {
                return new ResponseEntity<>(updatedConfirmation, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            // Log the exception
            System.err.println("Error updating confirmation status: " + ex.getMessage());

            // Return a more descriptive error message
            if (ex.getMessage() != null && ex.getMessage().contains("FOREIGN KEY constraint")) {
                return new ResponseEntity<>("The provided nurse ID does not exist in the system", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>("An error occurred while updating the status: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
