package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.ConfirmMedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.ConfirmMedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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

    @GetMapping("/by-student-name")
    public ResponseEntity<List<ConfirmMedicationSubmissionDTO>> getConfirmationsByStudentName(@RequestParam String name) {
        List<ConfirmMedicationSubmissionDTO> confirmations = confirmService.getConfirmationsByStudentName(name);
        return new ResponseEntity<>(confirmations, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ConfirmMedicationSubmissionDTO>> getAllConfirmations() {
        List<ConfirmMedicationSubmissionDTO> confirmations = confirmService.getAllConfirmations();
        return new ResponseEntity<>(confirmations, HttpStatus.OK);
    }

    @RequestMapping(value = "/{confirmId}/status", method = {RequestMethod.PUT, RequestMethod.GET})
    public ResponseEntity<?> handleConfirmationStatus(
            @PathVariable int confirmId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String reason,
            @RequestParam(required = false) Integer nurseId, // Thêm nurseId vào request
            HttpMethod method) {

        // For GET requests - retrieve current status
        if (method == HttpMethod.GET) {
            ConfirmMedicationSubmissionDTO confirmation = confirmService.getConfirmationById(confirmId);
            if (confirmation != null) {
                // If status parameter is provided, check if it matches
                if (status != null && !status.isEmpty()) {
                    // Log values for debugging
                    System.out.println("Requested status: " + status);
                    System.out.println("Actual status: " + confirmation.getStatus());

                    // Convert both to uppercase for comparison
                    String requestedStatus = status.toUpperCase();
                    String actualStatus = confirmation.getStatus() != null ?
                                         confirmation.getStatus().toUpperCase() : "";

                    // Compare status (ignoring case differences)
                    if (requestedStatus.equals(actualStatus)) {
                        return new ResponseEntity<>(confirmation, HttpStatus.OK);
                    } else {
                        // Return just confirmation anyway but with a message in headers
                        return new ResponseEntity<>(confirmation, HttpStatus.OK);
                    }
                } else {
                    // No status filter, just return the confirmation
                    return new ResponseEntity<>(confirmation, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // For PUT requests - update status
        else if (method == HttpMethod.PUT) {
            // Validate status is provided for PUT
            if (status == null) {
                return new ResponseEntity<>("Status is required for updates", HttpStatus.BAD_REQUEST);
            }

            // Standardize status to uppercase to match constants
            String standardizedStatus = status.toUpperCase();

            // Gọi service để cập nhật status và nurseId nếu cần
            ConfirmMedicationSubmissionDTO updatedConfirmation = confirmService.updateStatusAndNurse(confirmId, standardizedStatus, reason, nurseId);
            if (updatedConfirmation != null) {
                return new ResponseEntity<>(updatedConfirmation, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }


}
