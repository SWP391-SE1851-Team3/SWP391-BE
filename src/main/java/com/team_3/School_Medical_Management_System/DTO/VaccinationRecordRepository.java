package com.team_3.School_Medical_Management_System.DTO;
import com.team_3.School_Medical_Management_System.Model.Vaccination_records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VaccinationRecordRepository extends JpaRepository<Vaccination_records, Integer> {
    @Query("SELECT v FROM Vaccination_records v WHERE v.consentForm.student.StudentID = :studentId")
    List<Vaccination_records> findByStudentId(@Param("studentId") int studentId);

    @Query("SELECT MAX(v.VaccinationRecordID) FROM Vaccination_records v WHERE v.consentForm.student.StudentID = :studentId")
    Integer findMaxRecordIdByStudentId(@Param("studentId") int studentId);

    @Query("""
        SELECT v FROM Vaccination_records v 
        WHERE v.consentForm.student.StudentID = :studentId 
          AND v.consentForm.vaccineBatches.BatchID = :batchId
    """)
    Optional<Vaccination_records> findByStudentIdAndBatchId(@Param("studentId") int studentId, @Param("batchId") int batchId);
}
