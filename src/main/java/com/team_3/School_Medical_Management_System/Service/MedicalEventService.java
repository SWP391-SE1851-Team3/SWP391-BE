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
    private SchoolNurse nurse;
    private Parent parent;
    private Student student;
    private MedicalEvent medicalEvent;
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
        nurse = schoolNurseRepository.findById(nurseId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên y tế với ID: " + nurseId));

        // 2. Kiểm tra phụ huynh có tồn tại không
        parent = parentRepository.findById(dto.getParentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phụ huynh với ID: " + dto.getParentId()));

        // 3. Kiểm tra loại sự kiện y tế có tồn tại không
        student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh với ID: " + dto.getStudentId()));

        // 4. Tạo sự kiện y tế
        MedicalEvent event = new MedicalEvent();
        event.setUsageMethod(dto.getUsageMethod() != null ? dto.getUsageMethod() : "Y tá đã để chống");
        event.setIsEmergency(dto.getIsEmergency() != null ? dto.getIsEmergency() : false);
        event.setHasParentBeenInformed(dto.getHasParentBeenInformed() != null ? dto.getHasParentBeenInformed() : false);
        event.setTemperature(dto.getTemperature());
        event.setHeartRate(dto.getHeartRate());
        event.setEventDateTime(dto.getEventDateTime());
        event.setParent(parent);
        medicalEventRepository.save(event);

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

        // 8. Gửi thông báo cho phụ huynh nếu cần
        if (dto.getHasParentBeenInformed() != null && dto.getHasParentBeenInformed()) {
            MedicalEventNotification notification = new MedicalEventNotification();
            notification.setParentID(parent);
            notification.setEventID(event);
            notification.setTitle("Thông báo sự kiện y tế");
            notification.setContent("Học sinh " + student.getFullName() + " gặp sự kiện y tế: " + dto.getEventType());
            medicalEventNotificationRepository.save(notification); // Lưu vào bảng MedicalEventNotification
        }

        // 9. Trả về DTO
        dto.setEventId(event.getEventID()); // Cập nhật eventId cho DTO
        return dto;
    }

    public MedicalEventDTO updateEventDTO(Integer eventId, MedicalEventDTO dto, Integer nurseId) {
        // 1. Kiểm tra nhân viên y tế có tồn tại không
        nurse = schoolNurseRepository.findById(nurseId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên y tế với ID: " + nurseId));
        parent = parentRepository.findById(dto.getParentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phụ huynh với ID: " + dto.getParentId()));
        // 2. Kiểm tra sự kiện y tế có tồn tại không
        medicalEvent = medicalEventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sự kiện y tế với ID: " + dto.getEventId()));
        student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh với ID: " + dto.getStudentId()));
        // 3. Cập nhật thông tin sự kiện y tế
        medicalEvent.setUsageMethod(dto.getUsageMethod() != null ? dto.getUsageMethod() : "Y ta đã xử lí ");
        medicalEvent.setIsEmergency(dto.getIsEmergency() != null ? dto.getIsEmergency() : false);
        medicalEvent.setHasParentBeenInformed(dto.getHasParentBeenInformed() != null ? dto.getHasParentBeenInformed() : false);
        medicalEvent.setTemperature(dto.getTemperature());
        medicalEvent.setHeartRate(dto.getHeartRate());
        medicalEvent.setEventDateTime(dto.getEventDateTime());
        medicalEventRepository.save(medicalEvent);

        // 4. Cập nhật chi tiết sự kiện y tế
        if (dto.getStudentId() == null || dto.getEventId() == null) {
            throw new IllegalArgumentException("StudentID và EventID không được để trống");
        }
        MedicalEventDetailId eventDetailId = new MedicalEventDetailId(dto.getStudentId(), dto.getEventId());
        MedicalEventDetail eventDetail = medicalEventDetailRepository.findById(eventDetailId).orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết sự kiện y tế với EventID: " + dto.getEventId() + " và StudentID: " + dto.getStudentId()));
        eventDetail.setEventID(medicalEvent);
        eventDetail.setStudentID(student);
        eventDetail.setNote(dto.getNote());
        eventDetail.setResult(dto.getResult());
        eventDetail.setProcessingStatus(dto.getProcessingStatus());
        medicalEventDetailRepository.save(eventDetail);

        // 5. Cập nhật loại sự kiện y tế nếu cần
        MedicalEventType eventType = medicalEventTypeRepository.findByTypeName(dto.getEventType())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại sự kiện: " + dto.getEventType()));
        MedicalEventTypeMapping typeMapping = medicalEventTypeMappingRepository.findByEvent_EventId(eventId)
                .orElse(new MedicalEventTypeMapping());
        typeMapping.setEvent(medicalEvent);
        typeMapping.setEventType(eventType);
        medicalEventTypeMappingRepository.save(typeMapping);



// 8. Cập nhật liên kết nhân viên y tế
        MedicalEventNurseMapping nurseMapping = medicalEventNurseMappingRepository.findByEvent_EventId(eventId)
                .orElse(new MedicalEventNurseMapping());
        nurseMapping.setNurseID(nurse);
        nurseMapping.setEventID(medicalEvent);
        medicalEventNurseMappingRepository.save(nurseMapping);

        // 9. Cập nhật thông báo cho phụ huynh nếu cần
        if (dto.getHasParentBeenInformed() != null && dto.getHasParentBeenInformed()) {
            MedicalEventNotification notification = medicalEventNotificationRepository.findByEvent_EventId(eventId)
                    .orElse(new MedicalEventNotification());
            notification.setParentID(parent);
            notification.setEventID(medicalEvent);
            notification.setTitle("Cập nhật sự kiện y tế");
            notification.setContent("Học sinh " + student.getFullName() + " có cập nhật sự kiện y tế: " + dto.getEventType());
            medicalEventNotificationRepository.save(notification);
        }

        // 10. Trả về DTO
        dto.setEventId(medicalEvent.getEventID());
        return dto;
    }
}
