package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicalEventDetails;
import com.team_3.School_Medical_Management_System.Model.MedicalEventDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
// dựa vào các interface này thì có thể tìm kiếm sự chung giữa 2 bảng đó
public interface MedicalEventDetailsRepository extends JpaRepository<MedicalEventDetails, MedicalEventDetailsId> {
    List<MedicalEventDetails> findAllByOrderByMedicalEventEventDateTimeDesc();
    Optional<MedicalEventDetails> findByMedicalEvent_EventID(Integer eventId);

    void deleteByMedicalEvent_EventID(Integer eventId);


    @Query("SELECT med FROM MedicalEventDetails med JOIN med.medicalEvent me WHERE med.student.StudentID = :studentId AND me.parent.ParentID= :parentId")
    List<MedicalEventDetails> findByStudentIdAndParentId(@Param("studentId") int studentId, @Param("parentId") int parentId);
}
