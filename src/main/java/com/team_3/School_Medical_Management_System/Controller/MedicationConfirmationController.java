package com.team_3.School_Medical_Management_System.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team_3.School_Medical_Management_System.DTO.ConfirmMedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.ConfirmMedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.Service.FileStorageService;

@RestController
@RequestMapping("/api/medication-confirmations")
public class MedicationConfirmationController {

    @Autowired
    private ConfirmMedicationSubmissionServiceInterface confirmService;

    @Autowired
    private FileStorageService fileStorageService;

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
            String status = request.getStatus();
            String reason = request.getReason();
            Integer nurseId = request.getNurseId();
            ConfirmMedicationSubmissionDTO existingConfirmation = confirmService.getConfirmationById(confirmId);
            if (existingConfirmation == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            String finalStatus = (status != null && !status.isEmpty()) ? status : existingConfirmation.getStatus();
            String finalReason = (reason != null && !reason.isEmpty()) ? reason : existingConfirmation.getReason();
            Integer finalNurseId = (nurseId != null) ? nurseId : existingConfirmation.getNurseId();
            ConfirmMedicationSubmissionDTO updatedConfirmation =
                    confirmService.updateStatusAndNurse(confirmId, finalStatus, finalReason, finalNurseId);
            if (updatedConfirmation != null) {
                return new ResponseEntity<>(updatedConfirmation, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            System.err.println("Error updating confirmation status: " + ex.getMessage());
            if (ex.getMessage() != null && ex.getMessage().contains("FOREIGN KEY constraint")) {
                return new ResponseEntity<>("The provided nurse ID does not exist in the system", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("An error occurred while updating the status: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Upload evidence image for a confirmation (PNG, JPG, JPEG, GIF, ...)
     */
    @PostMapping(value = "/evidence-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadEvidenceImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("confirmId") int confirmId,
            @RequestParam(value = "saveAsBase64", defaultValue = "true") boolean saveAsBase64) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file to upload");
            }
            if (!fileStorageService.isImageFile(file)) {
                return ResponseEntity.badRequest().body("Only image files are allowed (PNG, JPG, JPEG, GIF, ...)");
            }
            if (file.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.badRequest().body("File size must be less than 5MB");
            }
            ConfirmMedicationSubmissionDTO confirmation = confirmService.getConfirmationById(confirmId);
            if (confirmation == null) {
                return ResponseEntity.badRequest().body("Confirmation not found");
            }
            String imageData;
            if (saveAsBase64) {
                imageData = fileStorageService.convertToBase64(file);
            } else {
                imageData = fileStorageService.storeFile(file);
            }
            confirmService.updateEvidence(confirmId, imageData);
            java.util.Map<String, Object> response = new java.util.HashMap<>();
            response.put("message", "Evidence image uploaded successfully");
            response.put("confirmId", confirmId);
            response.put("fileName", file.getOriginalFilename());
            response.put("fileSize", file.getSize());
            response.put("savedAsBase64", saveAsBase64);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading image: " + e.getMessage());
        }
    }

    /**
     * Get evidence image for a confirmation (returns base64 with header)
     */
    @GetMapping("/evidence-image/{confirmId}")
    public ResponseEntity<String> getEvidenceImage(@PathVariable int confirmId) {
        try {
            ConfirmMedicationSubmissionDTO confirmation = confirmService.getConfirmationById(confirmId);
            if (confirmation == null || confirmation.getEvidence() == null || confirmation.getEvidence().isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            String base64WithHeader = null;
            String imageData = confirmation.getEvidence();
            if (fileStorageService.isBase64(imageData)) {
                if (imageData.startsWith("data:")) {
                    base64WithHeader = imageData;
                } else {
                    base64WithHeader = "data:image/png;base64," + imageData;
                }
            } else {
                try {
                    String fileName = imageData;
                    byte[] fileBytes = Files.readAllBytes(fileStorageService.getFilePath(fileName));
                    String ext = fileName.toLowerCase();
                    String contentType = "image/png";
                    if (ext.endsWith(".jpg") || ext.endsWith(".jpeg")) contentType = "image/jpeg";
                    else if (ext.endsWith(".gif")) contentType = "image/gif";
                    String base64 = java.util.Base64.getEncoder().encodeToString(fileBytes);
                    base64WithHeader = "data:" + contentType + ";base64," + base64;
                } catch (IOException e) {
                    return ResponseEntity.notFound().build();
                }
            }
            return ResponseEntity.ok(base64WithHeader);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
