package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationSubmissionRepository extends JpaRepository<MedicationSubmission, Integer> {
    List<MedicationSubmission> findByParentId(int parentId);
    List<MedicationSubmission> findByStatus(MedicationSubmission.SubmissionStatus status);
}
