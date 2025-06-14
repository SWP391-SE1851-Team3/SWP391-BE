package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.MedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.MedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicationSubmissionInterFace;
import com.team_3.School_Medical_Management_System.Model.MedicationDetail;
import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicationSubmissionService implements MedicationSubmissionServiceInterface {

    @Autowired
    private MedicationSubmissionInterFace medicationSubmissionInterFace;

    @Override
    public MedicationSubmission submitMedication(MedicationSubmissionDTO medicationSubmissionDTO) {
        MedicationSubmission submission = new MedicationSubmission();
        submission.setParentId(medicationSubmissionDTO.getParentId());
        submission.setStudentId(medicationSubmissionDTO.getStudentId());
        submission.setMedicineImage(medicationSubmissionDTO.getMedicineImage());
        submission.setSubmissionDate(LocalDateTime.now());

        // Chuyển đổi danh sách MedicationDetailDTO thành MedicationDetail
        if (medicationSubmissionDTO.getMedicationDetails() != null && !medicationSubmissionDTO.getMedicationDetails().isEmpty()) {
            List<MedicationDetail> medicationDetails = medicationSubmissionDTO.getMedicationDetails().stream()
                    .map(detailDTO -> {
                        MedicationDetail detail = new MedicationDetail();
                        detail.setMedicationName(detailDTO.getMedicationName());
                        detail.setDosage(detailDTO.getDosage());
                        detail.setTimesToUse(detailDTO.getTimesToUse());
                        detail.setNotes(detailDTO.getNotes());
                        detail.setMedicationSubmission(submission);
                        return detail;
                    })
                    .collect(Collectors.toList());

            submission.setMedicationDetails(medicationDetails);
        }

        return medicationSubmissionInterFace.save(submission);
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

//    @Override
//    public List<MedicationSubmission> getAllPendingMedicationSubmissions() {
//        return medicationSubmissionInterFace.findByStatus(MedicationSubmission.SubmissionStatus.PENDING);
//    }

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
