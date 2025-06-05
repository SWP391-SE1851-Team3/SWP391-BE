package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.MedicalEventDTO;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class MedicalEventService {

    // Tiêm các repository để truy cập cơ sở dữ liệu
    @Autowired
    private MedicalEventRepository medicalEventRepository;

    @Autowired
    private MedicalEventTypeRepository medicalEventTypeRepository;

    @Autowired
    private MedicalEventTypeMappingRepository medicalEventTypeMappingRepository;

    @Autowired
    private MedicalEventNurseMappingRepository medicalEventNurseMappingRepository;

    @Autowired
    private MedicalEventNotificationRepo medicalEventNotificationRepository;

    @Autowired
    private SchoolNurseRepository schoolNurseRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MedicalEventDetailRepository medicalEventDetailRepository;

    public MedicalEventDTO createMedicalEvent(MedicalEventDTO dto, Integer nurseId) {
        // 1. Kiểm tra nhân viên y tế có tồn tại không
        SchoolNurse nurse = schoolNurseRepository.findById(nurseId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên y tế với ID: " + nurseId));

        // 2. Kiểm tra phụ huynh có tồn tại không
        Parent parent = parentRepository.findById(dto.getParentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phụ huynh với ID: " + dto.getParentId()));

        // 3. Kiểm tra loại sự kiện y tế có tồn tại không
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh với ID: " + dto.getStudentId()));

        // 4. Tạo sự kiện y tế
        MedicalEvent event = new MedicalEvent();
        event.setUsageMethod(dto.getUsageMethod());
        event.setIsEmergency(dto.getIsEmergency() != null ? dto.getIsEmergency() : false);
        event.setHasParentBeenInformed(dto.getHasParentBeenInformed() != null ? dto.getHasParentBeenInformed() : false);
        event.setTemperature(dto.getTemperature());
        event.setHeartRate(dto.getHeartRate());
        event.setEventDateTime(dto.getEventDateTime());
        event.setParent(parent);

        // lƯU XUỐNG BẲNG DETAIL OK CHƯA OK
        MedicalEventDetail eventDetail = new MedicalEventDetail();
        eventDetail.setStudentID(student);
        eventDetail.setEventID(event);
        eventDetail.setNote(dto.getNote());
        eventDetail.setResult(dto.getResult());
        eventDetail.setProcessingStatus(dto.getProcessingStatus());
        medicalEventDetailRepository.save(eventDetail);
        //KIỂM TRA  VA LIÊN KẾT LOẠI SỰ KIỆN Y TẾ
        MedicalEventType eventType = medicalEventTypeRepository.findByTypeName(dto.getEventType())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại sự kiện: " + dto.getEventType()));
        MedicalEventTypeMapping typeMapping = new MedicalEventTypeMapping();
        typeMapping.setEvent(event);
        typeMapping.setEventType(eventType);
        medicalEventTypeMappingRepository.save(typeMapping);
        medicalEventRepository.save(event);
        return dto;
    }
}
