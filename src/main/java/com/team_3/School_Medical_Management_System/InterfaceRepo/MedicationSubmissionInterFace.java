package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationSubmissionInterFace extends JpaRepository<MedicationSubmission, Integer> {
    // Các phương thức tìm kiếm tùy chỉnh
    List<MedicationSubmission> findByParentId(int parentId);
    List<MedicationSubmission> findByStudentIdIn(List<Integer> studentIds);

    @Query("SELECT m FROM MedicationSubmission m")
    List<MedicationSubmission> findAllSubmissions();

    @Modifying
    @Query("UPDATE MedicationSubmission m SET m.parentId = NULL WHERE m.parentId = :parentID")
    public void setNullParentIDByParentID(@Param("parentID") int parentID);
}
