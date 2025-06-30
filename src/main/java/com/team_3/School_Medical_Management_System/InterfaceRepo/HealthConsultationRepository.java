package com.team_3.School_Medical_Management_System.InterfaceRepo;


import com.team_3.School_Medical_Management_System.Model.HealthConsultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.team_3.School_Medical_Management_System.Model.HealthConsultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthConsultationRepository extends JpaRepository<HealthConsultation, Integer> {
    List<HealthConsultation> findByStudentID(int studentID);
    List<HealthConsultation> findByStatus(String status);

    @Query("DELETE FROM HealthConsultation hc WHERE hc.studentID = :studentId")

    void deleteByStudent_StudentID(@Param("studentId") int studentId);

}
