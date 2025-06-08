package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.ConfirmMedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.ConfirmMedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;
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

    @PostMapping
    public ResponseEntity<ConfirmMedicationSubmissionDTO> createConfirmation(@RequestBody ConfirmMedicationSubmissionDTO confirmDTO) {
        ConfirmMedicationSubmissionDTO createdConfirmation = confirmService.createConfirmation(confirmDTO);
        return new ResponseEntity<>(createdConfirmation, HttpStatus.CREATED);
    }

//    @PutMapping("/{confirmId}/status")
//    public ResponseEntity<ConfirmMedicationSubmissionDTO> updateConfirmationStatus(
//            @PathVariable int confirmId,
//            @RequestParam ConfirmMedicationSubmission.confirmMedicationSubmissionStatus APPROVED) {
//        ConfirmMedicationSubmissionDTO updatedConfirmation = confirmService.updateConfirmationStatus(confirmId, APPROVED);
//        if (updatedConfirmation != null) {
//            return new ResponseEntity<>(updatedConfirmation, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PutMapping("/{confirmId}/medication-taken")
    public ResponseEntity<ConfirmMedicationSubmissionDTO> updateMedicationTaken(
            @PathVariable int confirmId,
            @RequestParam ConfirmMedicationSubmission.confirmMedicationSubmissionReceivedMedicine YES) {
        ConfirmMedicationSubmissionDTO updatedConfirmation = confirmService.updateMedicationTaken(confirmId, YES);
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
}
