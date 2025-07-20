package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicalEvent_EventType;
import com.team_3.School_Medical_Management_System.Model.MedicalEvent_EventTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalEvent_EventTypeRepo extends JpaRepository<MedicalEvent_EventType, MedicalEvent_EventTypeId> {
    // Interface này sẽ tự động cung cấp các phương thức CRUD cho MedicalEvent_EventType
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần thiết
    MedicalEvent_EventType findByMedicalEvent_EventID(Integer eventId);

    @Modifying
    @Query("DELETE FROM MedicalEvent_EventType met WHERE met.medicalEvent.eventID = :eventId")
    void deleteByMedicalEvent_EventID(@Param("eventId") Integer eventId);
   // void deleteByMedicalEvent_EventID(Integer eventId);



    @Query("select met FROM MedicalEvent_EventType met WHERE met.medicalEvent.eventID = :eventId")
 public List<MedicalEvent_EventType> findByMedicalEvent_Event(@Param("eventId") Integer eventId);
}
