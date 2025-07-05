package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.*;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.ConfirmMedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.MedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.SchoolNurseServiceInterFace;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.StudentServiceInterFace;
import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;
import com.team_3.School_Medical_Management_System.Model.MedicationDetail;
import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;
import com.team_3.School_Medical_Management_System.Model.Student;
import com.team_3.School_Medical_Management_System.Service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
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

    @Autowired
    private FileStorageService fileStorageService;

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
    public ResponseEntity<MedicationDetailsExtendedDTO> getSubmissionDetails(@PathVariable int submissionId) {
        MedicationDetailsExtendedDTO details = medicationSubmissionService.getDetailsBySubmissionIdExtended(submissionId);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @GetMapping("/submissions/{parentId}")
    public ResponseEntity<List<MedicationSubmission>> getMedicationSubmissions(@PathVariable int parentId) {
        List<MedicationSubmission> submissions = medicationSubmissionService.getAllMedicationSubmissionsByParentId(parentId);
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }


    @PostMapping("/confirm-medication")
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

    /**
     * Upload ảnh thuốc (PNG) cho medication submission
     */
    @PostMapping(value = "/upload-medicine-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload medicine image", description = "Upload PNG image for medication submission")
    public ResponseEntity<?> uploadMedicineImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("submissionId") int submissionId,
            @RequestParam(value = "saveAsBase64", defaultValue = "true") boolean saveAsBase64) {

        try {
            // Kiểm tra file có tồn tại không
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file to upload");
            }

            // Kiểm tra file có phải PNG không
            if (!fileStorageService.isPngFile(file)) {
                return ResponseEntity.badRequest().body("Only PNG files are allowed");
            }

            // Kiểm tra kích thước file (tối đa 5MB)
            if (file.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.badRequest().body("File size must be less than 5MB");
            }

            // Lấy medication submission
            MedicationSubmission submission = medicationSubmissionService.getMedicationSubmissionById(submissionId);
            if (submission == null) {
                return ResponseEntity.badRequest().body("Medication submission not found");
            }

            String imageData;
            if (saveAsBase64) {
                // Lưu ảnh dưới dạng Base64 string
                imageData = fileStorageService.convertToBase64(file);
            } else {
                // Lưu file vào thư mục và lưu đường dẫn
                imageData = fileStorageService.storeFile(file);
            }

            submission.setMedicineImage(imageData);

            // Cập nhật submission
            medicationSubmissionService.updateMedicationSubmission(submission);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Image uploaded successfully");
            response.put("submissionId", submissionId);
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
     * Lấy ảnh thuốc từ database
     */
    @GetMapping("/medicine-image/{submissionId}")
    @Operation(summary = "Get medicine image", description = "Retrieve medicine image by submission ID")
    public ResponseEntity<byte[]> getMedicineImage(@PathVariable int submissionId) {
        try {
            MedicationSubmission submission = medicationSubmissionService.getMedicationSubmissionById(submissionId);
            if (submission == null || submission.getMedicineImage() == null || submission.getMedicineImage().isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            byte[] imageBytes;

            // Kiểm tra xem có phải Base64 string không
            if (fileStorageService.isBase64(submission.getMedicineImage())) {
                // Chuyển đổi từ Base64 thành byte array
                imageBytes = fileStorageService.convertFromBase64(submission.getMedicineImage());
            } else {
                // Đọc từ file system (trường hợp lưu đường dẫn file)
                try {
                    imageBytes = Files.readAllBytes(fileStorageService.getFilePath(submission.getMedicineImage()));
                } catch (IOException e) {
                    return ResponseEntity.notFound().build();
                }
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageBytes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Xóa ảnh thuốc
     */
    @DeleteMapping("/medicine-image/{submissionId}")
    @Operation(summary = "Delete medicine image", description = "Delete medicine image by submission ID")
    public ResponseEntity<?> deleteMedicineImage(@PathVariable int submissionId) {
        try {
            MedicationSubmission submission = medicationSubmissionService.getMedicationSubmissionById(submissionId);
            if (submission == null) {
                return ResponseEntity.badRequest().body("Medication submission not found");
            }

            // Nếu là file path, xóa file từ file system
            if (submission.getMedicineImage() != null &&
                    !fileStorageService.isBase64(submission.getMedicineImage())) {
                try {
                    fileStorageService.deleteFile(submission.getMedicineImage());
                } catch (IOException e) {
                    // Log error nhưng vẫn tiếp tục xóa record trong database
                }
            }

            // Xóa dữ liệu ảnh từ database
            submission.setMedicineImage(null);
            medicationSubmissionService.updateMedicationSubmission(submission);

            return ResponseEntity.ok("Medicine image deleted successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting image: " + e.getMessage());
        }
    }
}

