package com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace;

import com.team_3.School_Medical_Management_System.DTO.ConfirmMedicationSubmissionDTO;

import java.util.List;

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

}
