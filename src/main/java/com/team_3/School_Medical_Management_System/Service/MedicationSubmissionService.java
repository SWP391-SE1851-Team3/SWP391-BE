package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.MedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicationSubmissionInfoDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicationDetailDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.MedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ConfirmMedicationSubmissionInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicationSubmissionInterFace;
import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;
import com.team_3.School_Medical_Management_System.Model.MedicationDetail;
import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;
import com.team_3.School_Medical_Management_System.Model.Student;
import com.team_3.School_Medical_Management_System.Service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicationSubmissionService implements MedicationSubmissionServiceInterface {

    @Autowired
    private MedicationSubmissionInterFace medicationSubmissionInterFace;
    @Autowired
    private ConfirmMedicationSubmissionInterFace confirmMedicationSubmissionInterFace;
    @Autowired
    private StudentService studentService;

    @Override
    public MedicationSubmission submitMedication(MedicationSubmissionDTO medicationSubmissionDTO) {
        // Create and save the medication submission as before
        MedicationSubmission submission = new MedicationSubmission();
        submission.setParentId(medicationSubmissionDTO.getParentId());
        submission.setStudentId(medicationSubmissionDTO.getStudentId());
        submission.setMedicineImage(medicationSubmissionDTO.getMedicineImage());

        // Convert MedicationDetailDTO list to MedicationDetail
        if (medicationSubmissionDTO.getMedicationDetails() != null && !medicationSubmissionDTO.getMedicationDetails().isEmpty()) {
            List<MedicationDetail> medicationDetails = medicationSubmissionDTO.getMedicationDetails().stream()
                    .map(detailDTO -> {
                        MedicationDetail detail = new MedicationDetail();
                        detail.setMedicineName(detailDTO.getMedicineName());
                        detail.setDosage(detailDTO.getDosage());
                        detail.setTimeToUse(detailDTO.getTimeToUse());
                        detail.setNote(detailDTO.getNote());
                        detail.setMedicationSubmission(submission);
                        return detail;
                    })
                    .collect(Collectors.toList());

            submission.setMedicationDetails(medicationDetails);
        }

        // Set submissionDate khi submit đơn
        submission.setSubmissionDate(java.time.LocalDateTime.now());

        // Save the medication submission
        MedicationSubmission savedSubmission = medicationSubmissionInterFace.save(submission);

        // Create and save a confirmation record with PENDING status
        ConfirmMedicationSubmission confirmation = new ConfirmMedicationSubmission();
        confirmation.setMedicationSubmissionId(savedSubmission.getMedicationSubmissionId());
        confirmation.setStatus("PENDING"); // Using string literal instead of constant

        // Note: nurseId and evidence will be updated later when a nurse processes this

        confirmMedicationSubmissionInterFace.save(confirmation);

        return savedSubmission;
    }

    @Override
    public void cancelMedicationSubmission(int submissionId) {
        // Find the medication submission using your existing interface
        MedicationSubmission submission = medicationSubmissionInterFace.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Medication submission not found"));

        // Find the confirmation using your interface and handle Optional properly
        Optional<ConfirmMedicationSubmission> confirmationOptional = confirmMedicationSubmissionInterFace
                .findByMedicationSubmissionId(submissionId);

        // Check if confirmation exists and has PENDING status
        if (!confirmationOptional.isPresent() ||

                confirmationOptional.get().getStatus().equalsIgnoreCase("PENDING")) {


            // If confirmation exists, delete it first
            confirmationOptional.ifPresent(confirmation ->
                    confirmMedicationSubmissionInterFace.delete(confirmation));

            // Then delete the medication submission
            medicationSubmissionInterFace.delete(submission);
        } else {
            throw new IllegalStateException("Cannot cancel medication submission that has been processed or administered");
        }
    }

    public List<MedicationDetail> getDetailsBySubmissionId(int submissionId) {
        MedicationSubmission submission = medicationSubmissionInterFace.findById(submissionId)
                .orElseThrow(() -> new EntityNotFoundException("Medication submission not found with id: " + submissionId));
        return submission.getMedicationDetails();
    }

    @Override
    public List<MedicationSubmission> getAllMedicationSubmissionsByParentId(int parentId) {
        return medicationSubmissionInterFace.findByParentId(parentId);
    }

    @Override
    public List<MedicationSubmission> findAllSubmissions() {
        // Implement according to your business logic
        return medicationSubmissionInterFace.findAllSubmissions();
    }

    @Override
    public List<MedicationSubmissionInfoDTO> getAllMedicationSubmissionInfo() {
        List<MedicationSubmission> submissions = medicationSubmissionInterFace.findAll();
        return submissions.stream().map(submission -> {
            Student student = studentService.getStudent(submission.getStudentId());
            String studentName = student != null ? student.getFullName() : "Unknown";
            List<MedicationDetailDTO> detailDTOs = submission.getMedicationDetails().stream().map(detail -> {
                MedicationDetailDTO dto = new MedicationDetailDTO();
                dto.setMedicineName(detail.getMedicineName());
                dto.setDosage(detail.getDosage());
                dto.setTimeToUse(detail.getTimeToUse());
                dto.setNote(detail.getNote());
                return dto;
            }).collect(java.util.stream.Collectors.toList());
            // Lấy status từ ConfirmMedicationSubmission
            String status = "PENDING";
            Optional<ConfirmMedicationSubmission> confirmOpt = confirmMedicationSubmissionInterFace.findByMedicationSubmissionId(submission.getMedicationSubmissionId());
            if (confirmOpt.isPresent() && confirmOpt.get().getStatus() != null) {
                status = confirmOpt.get().getStatus();
            }
            java.util.Date submissionDate = null;
            if (submission.getSubmissionDate() != null) {
                submissionDate = java.sql.Timestamp.valueOf(submission.getSubmissionDate());
            }
            return new MedicationSubmissionInfoDTO(
                    studentName,
                    submissionDate,
                    status,
                    detailDTOs
            );
        }).collect(java.util.stream.Collectors.toList());
    }

    public List<MedicationSubmissionInfoDTO> getMedicationSubmissionInfoByParentId(int parentId) {
        List<MedicationSubmission> submissions = medicationSubmissionInterFace.findByParentId(parentId);
        return submissions.stream().map(submission -> {
            Student student = studentService.getStudent(submission.getStudentId());
            String studentName = student != null ? student.getFullName() : "Unknown";
            List<MedicationDetailDTO> detailDTOs = submission.getMedicationDetails().stream().map(detail -> {
                MedicationDetailDTO dto = new MedicationDetailDTO();
                dto.setMedicineName(detail.getMedicineName());
                dto.setDosage(detail.getDosage());
                dto.setTimeToUse(detail.getTimeToUse());
                dto.setNote(detail.getNote());
                return dto;
            }).collect(java.util.stream.Collectors.toList());
            // Lấy status từ ConfirmMedicationSubmission
            String status = "PENDING";
            Optional<ConfirmMedicationSubmission> confirmOpt = confirmMedicationSubmissionInterFace.findByMedicationSubmissionId(submission.getMedicationSubmissionId());
            if (confirmOpt.isPresent() && confirmOpt.get().getStatus() != null) {
                status = confirmOpt.get().getStatus();
            }
            java.util.Date submissionDate = null;
            if (submission.getSubmissionDate() != null) {
                submissionDate = java.sql.Timestamp.valueOf(submission.getSubmissionDate());
            }
            return new MedicationSubmissionInfoDTO(
                    studentName,
                    submissionDate,
                    status,
                    detailDTOs
            );
        }).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public MedicationSubmission getMedicationSubmissionById(int submissionId) {
        return medicationSubmissionInterFace.findById(submissionId).orElse(null);
    }

//    @Override
//    public List<MedicationSubmission> getAllPendingMedicationSubmissions() {
//        return medicationSubmissionInterFace.findByStatus(MedicationSubmission.SubmissionStatus.PENDING);
//    }
//
//    @Override
//    public MedicationSubmission approveMedicationSubmission(int submissionId) {
//        MedicationSubmission submission = medicationSubmissionInterFace.findById(submissionId)
//                .orElseThrow(() -> new EntityNotFoundException("Medication submission not found with id: " + submissionId));
//
//        submission.setStatus(MedicationSubmission.SubmissionStatus.APPROVED);
//        submission.setProcessedDate(LocalDateTime.now());
//        return medicationSubmissionInterFace.save(submission);
//    }
//
//    @Override
//    public MedicationSubmission rejectMedicationSubmission(int submissionId, String reason) {
//        MedicationSubmission submission = medicationSubmissionInterFace.findById(submissionId)
//                .orElseThrow(() -> new EntityNotFoundException("Medication submission not found with id: " + submissionId));
//
//        submission.setStatus(MedicationSubmission.SubmissionStatus.REJECTED);
//        submission.setRejectionReason(reason);
//        submission.setProcessedDate(LocalDateTime.now());
//        return medicationSubmissionInterFace.save(submission);
//    }
//
//    @Override
//    public MedicationSubmission confirmMedicationAdministered(int submissionId, String administrationNotes) {
//        MedicationSubmission submission = medicationSubmissionInterFace.findById(submissionId)
//                .orElseThrow(() -> new EntityNotFoundException("Medication submission not found with id: " + submissionId));
//
//        if (submission.getStatus() != MedicationSubmission.SubmissionStatus.APPROVED) {
//            throw new IllegalStateException("Cannot confirm administration for a submission that is not approved");
//        }
//
//        submission.setStatus(MedicationSubmission.SubmissionStatus.ADMINISTERED);
//        submission.setAdministeredDate(LocalDateTime.now());
//        submission.setAdministrationNotes(administrationNotes);
//
//        return medicationSubmissionInterFace.save(submission);
//    }
//
//    @Override
//    public List<MedicationSubmission> getAllSubmissionsByStatus(MedicationSubmission.SubmissionStatus submissionStatus) {
//        return medicationSubmissionInterFace.findByStatus(submissionStatus);
//    }
}
