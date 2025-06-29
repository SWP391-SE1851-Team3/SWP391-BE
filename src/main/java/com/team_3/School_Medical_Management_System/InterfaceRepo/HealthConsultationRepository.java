package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.HealthConsultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HealthConsultationRepository extends JpaRepository<HealthConsultation, Integer> {

    @Query("DELETE FROM HealthConsultation hc WHERE hc.student.StudentID = :studentId")

    void deleteByStudent_StudentID(@Param("studentId") int studentId);



}
