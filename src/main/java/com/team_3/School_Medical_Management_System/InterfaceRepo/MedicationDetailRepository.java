package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicationDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicationDetailRepository extends JpaRepository<MedicationDetail,Long> {


@Modifying
@Transactional
@Query("DELETE FROM MedicationDetail md WHERE md.medicationSubmission.medicationSubmissionId = :submissionId")
void deleteByMedicationSubmission_MedicationSubmissionId(@Param("submissionId") int submissionId);
}
