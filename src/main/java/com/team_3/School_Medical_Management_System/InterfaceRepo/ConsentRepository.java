package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Consent_forms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConsentRepository  extends JpaRepository<Consent_forms, Integer> {
    @Query("SELECT c FROM Consent_forms c WHERE c.student.StudentID = :studentId AND c.vaccineBatches.BatchID = :batchId AND c.status = :status")
    Optional<Consent_forms> findByStudentIdAndBatchIdAndStatus(
            @Param("studentId") Integer studentId,
            @Param("batchId") Integer batchId,
            @Param("status") String status
    );
}
