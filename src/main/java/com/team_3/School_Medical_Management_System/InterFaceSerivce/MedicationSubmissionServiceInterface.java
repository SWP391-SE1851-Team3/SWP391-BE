package com.team_3.School_Medical_Management_System.InterFaceSerivce;

import com.team_3.School_Medical_Management_System.DTO.MedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;

import java.util.List;

public interface MedicationSubmissionServiceInterface {
    MedicationSubmission submitMedication(MedicationSubmissionDTO medicationSubmissionDTO);
    List<MedicationSubmission> getAllMedicationSubmissionsByParentId(int parentId);
//    List<MedicationSubmission> getAllPendingMedicationSubmissions();
//    List<MedicationSubmission> getAllSubmissionsByStatus(MedicationSubmission.SubmissionStatus submissionStatus);
//    MedicationSubmission approveMedicationSubmission(int submissionId);
//    MedicationSubmission rejectMedicationSubmission(int submissionId, String reason);
//    MedicationSubmission confirmMedicationAdministered(int submissionId, String administrationNotes);
    List<MedicationSubmission> findAllSubmissions();
}
