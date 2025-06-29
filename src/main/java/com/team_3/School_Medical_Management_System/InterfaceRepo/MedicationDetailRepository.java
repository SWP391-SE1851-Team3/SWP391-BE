package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationDetailRepository extends JpaRepository<MedicationDetail,Long> {

    public void deleteByMedicationSubmission_MedicationSubmissionId(int medicationSubmissionId);
}
