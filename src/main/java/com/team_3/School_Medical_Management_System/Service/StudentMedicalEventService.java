package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.MedicalEventDTO;
import com.team_3.School_Medical_Management_System.DTO.StudentMedicalEventDto;
import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicalEventDetailsRepository;
import com.team_3.School_Medical_Management_System.Model.MedicalEvent;
import com.team_3.School_Medical_Management_System.Model.MedicalEventDetails;
import com.team_3.School_Medical_Management_System.Model.MedicalEvent_EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentMedicalEventService {
    @Autowired
    private MedicalEventDetailsRepository repository;
    public List<StudentMedicalEventDto> getRecentMedicalEvents() {
        List<MedicalEventDetails> details = repository.findAllByOrderByMedicalEventEventDateTimeDesc();
        List<StudentMedicalEventDto> dtos = new ArrayList<>();

        for (MedicalEventDetails detail : details) {
            String studentName = detail.getStudent().getFullName() + " - " + detail.getStudent().getClassName();
            Long eventDetailsID = detail.getDetailsID();
           Integer eventId =  detail.getMedicalEvent().getEventID();
           // String eventType = ""; // Giá trị mặc định
            List<String> eventTypeName = new ArrayList<>();
            LocalDateTime time = detail.getMedicalEvent().getEventDateTime() != null
                    ? detail.getMedicalEvent().getEventDateTime()
                    : LocalDateTime.now();
            String status = detail.getProcessingStatus() != null
                    ? detail.getProcessingStatus()
                    : "Không xác định";
            String actions = "";

            // Lấy typeName từ MedicalEventType qua MedicalEvent_EventType
            if (detail.getMedicalEvent().getMedicalEventEventTypes()!= null && !detail.getMedicalEvent().getMedicalEventEventTypes().isEmpty()) {
                for (MedicalEvent_EventType eventTypeLink : detail.getMedicalEvent().getMedicalEventEventTypes()) {
                    if (eventTypeLink.getEventType() != null && eventTypeLink.getEventType().getTypeName() != null) {
                        eventTypeName.add(eventTypeLink.getEventType().getTypeName());
                    }
                }
            }

            dtos.add(new StudentMedicalEventDto(studentName, eventTypeName, time, status, actions,eventId,eventDetailsID));
        }

        return dtos;
    }



}
