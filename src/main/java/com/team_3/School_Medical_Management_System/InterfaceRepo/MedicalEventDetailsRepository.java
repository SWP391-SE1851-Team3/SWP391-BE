package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicalEventDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
// dựa vào các interface này thì có thể tìm kiếm sự chung giữa 2 bảng đó
public interface MedicalEventDetailsRepository extends JpaRepository<MedicalEventDetails, Integer> {
    List<MedicalEventDetails> findAllByOrderByMedicalEventEventDateTimeDesc();

  @Query("SELECT m FROM MedicalEventDetails m WHERE m.detailsID = :eventDetailsId")
    Optional<MedicalEventDetails> findByDetailsID(@Param("eventDetailsId") Integer eventDetailsId);



    @Query("SELECT m FROM MedicalEventDetails m WHERE m.detailsID = :eventDetailsId AND m.student.StudentID = :studentId")
    Optional<MedicalEventDetails> findByMedicalEventDetailsAndStudentID(@Param("eventDetailsId") Integer eventDetailsId,
                                                                        @Param("studentId") Integer studentId);
    @Modifying
    @Query("DELETE FROM MedicalEventDetails m WHERE m.medicalEvent.eventID = :eventId")
    void deleteByMedicalEvent_EventID(@Param("eventId") Integer eventId);



 @Query("SELECT m FROM MedicalEventDetails m WHERE m.student.StudentID = :studentId AND m.parent.ParentID = :parentId")
 public List<MedicalEventDetails> findByStudentIdAndParentId(@Param("studentId") int studentId, @Param("parentId") int parentId);
}
