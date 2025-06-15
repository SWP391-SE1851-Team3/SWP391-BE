package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfirmMedicationSubmissionInterFace extends JpaRepository<ConfirmMedicationSubmission, Integer> {
    Optional<ConfirmMedicationSubmission> findByMedicationSubmissionId(int medicationSubmissionId);
    List<ConfirmMedicationSubmission> findByNurseId(int nurseId);
    List<ConfirmMedicationSubmission> findByStatus(ConfirmMedicationSubmission.confirmMedicationSubmissionStatus status);

}
