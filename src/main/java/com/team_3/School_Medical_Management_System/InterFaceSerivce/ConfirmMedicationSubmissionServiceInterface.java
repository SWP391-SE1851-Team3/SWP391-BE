package com.team_3.School_Medical_Management_System.InterFaceSerivce;

import java.util.List;

import com.team_3.School_Medical_Management_System.DTO.ConfirmMedicationSubmissionDTO;

public interface ConfirmMedicationSubmissionServiceInterface {
    ConfirmMedicationSubmissionDTO createConfirmation(ConfirmMedicationSubmissionDTO confirmDTO);

    ConfirmMedicationSubmissionDTO updateConfirmationStatus(int confirmId, String status);

    ConfirmMedicationSubmissionDTO getConfirmationById(int confirmId);
    ConfirmMedicationSubmissionDTO getConfirmationBySubmissionId(int medicationSubmissionId);
    List<ConfirmMedicationSubmissionDTO> getConfirmationsByNurse(int nurseId);
    List<ConfirmMedicationSubmissionDTO> getAllConfirmations();
    ConfirmMedicationSubmissionDTO updateConfirmationStatusWithReason(int confirmId,
                                                                      String status, String reason);
    ConfirmMedicationSubmissionDTO updateStatusAndNurse(int confirmId, String status, String reason, Integer nurseId);
    ConfirmMedicationSubmissionDTO updateEvidence(int confirmId, String evidence);
//    List<ConfirmMedicationSubmissionDTO> getConfirmationsByStudentName(String studentName);
}
