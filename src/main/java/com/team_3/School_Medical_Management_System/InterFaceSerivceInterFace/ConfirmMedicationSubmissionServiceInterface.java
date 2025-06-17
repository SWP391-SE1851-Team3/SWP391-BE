package com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace;

import com.team_3.School_Medical_Management_System.DTO.ConfirmMedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;

import java.util.List;

public interface ConfirmMedicationSubmissionServiceInterface {
    ConfirmMedicationSubmissionDTO createConfirmation(ConfirmMedicationSubmissionDTO confirmDTO);
    ConfirmMedicationSubmissionDTO updateMedicationTaken(int confirmId, ConfirmMedicationSubmission.confirmMedicationSubmissionReceivedMedicine receivedMedicine);
    ConfirmMedicationSubmissionDTO getConfirmationById(int confirmId);
    ConfirmMedicationSubmissionDTO getConfirmationBySubmissionId(int medicationSubmissionId);
    List<ConfirmMedicationSubmissionDTO> getConfirmationsByNurse(int nurseId);
    List<ConfirmMedicationSubmissionDTO> getAllConfirmations();

}
