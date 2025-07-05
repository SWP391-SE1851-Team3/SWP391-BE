package com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace;

import com.team_3.School_Medical_Management_System.DTO.MedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicationSubmissionInfoDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicationDetailsExtendedDTO;
import com.team_3.School_Medical_Management_System.Model.MedicationDetail;
import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;

import java.util.List;

public interface MedicationSubmissionServiceInterface {
    MedicationSubmission submitMedication(MedicationSubmissionDTO medicationSubmissionDTO);
    List<MedicationSubmission> getAllMedicationSubmissionsByParentId(int parentId);
    List<MedicationSubmission> findAllSubmissions();
    public List<MedicationDetail> getDetailsBySubmissionId(int submissionId);
    void cancelMedicationSubmission(int submissionId);
    List<MedicationSubmissionInfoDTO> getAllMedicationSubmissionInfo();
    List<MedicationSubmissionInfoDTO> getMedicationSubmissionInfoByParentId(int parentId);
    MedicationSubmission getMedicationSubmissionById(int submissionId);
    MedicationDetailsExtendedDTO getDetailsBySubmissionIdExtended(int submissionId);
}
