package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.MedicalEventNotification;
import com.team_3.School_Medical_Management_System.Model.MedicalEventTypeMapping;
import com.team_3.School_Medical_Management_System.Model.MedicalEventTypeMappingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalEventTypeMappingRepository extends JpaRepository<MedicalEventTypeMapping, MedicalEventTypeMappingId> {
    //Optional<MedicalEventTypeventIDeMapping> findByEvent_EventId(Integer eventId);
 //   Optional<MedicalEventTypeMapping> findByEventID_eventID(Integer eventId);


}
