package com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace;

import com.team_3.School_Medical_Management_System.Model.Consent_forms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsentFormsRepository extends JpaRepository<Consent_forms, Integer> {
    @Query(value = "SELECT TOP 1 * FROM consent_forms " +
            "WHERE StudentID = :studentId AND schedule_id = :scheduleId " +
            "ORDER BY consent_date DESC",
            nativeQuery = true)
    Optional<Consent_forms> findLatestByStudentAndSchedule(
            @Param("studentId") int studentId,
            @Param("scheduleId") int scheduleId
    );
}


