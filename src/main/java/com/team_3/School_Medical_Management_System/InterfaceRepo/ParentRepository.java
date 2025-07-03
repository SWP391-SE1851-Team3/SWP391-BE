package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ParentRepository extends JpaRepository<Parent, Integer> {
    // Truy xuất phụ huynh dựa trên ID học sinh
    @Query("SELECT s.parent FROM Student s WHERE s.StudentID = :studentId")
    Parent GetParentByStudentId(@Param("studentId") Integer studentId);
   // Optional<Parent> findByUsername (String username);

    @Query("SELECT p FROM Parent p WHERE p.IsActive =1")
    public List<Parent> getAllWithIsActive();
}
