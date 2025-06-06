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

    public MedicalEventDTO createMedicalSudden(MedicalEventDTO dto, Integer nurseId) {
        // 1. Kiểm tra nhân viên y tế có tồn tại không/
        // Phải kiểm tra ID NV y tế đề phòng người k phải y tế vào chỉnh sửa sai thông tin và thêm thông tin sai
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
        event.setUsageMethod(dto.getUsageMethod());
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
        eventDetail.setEvent(event);
        eventDetail.setNote(dto.getNote());
        eventDetail.setResult(dto.getResult());
        eventDetail.setProcessingStatus(dto.getProcessingStatus());
        medicalEventDetailRepository.save(eventDetail);

MedicalEventNurseMapping nurseMapping = new MedicalEventNurseMapping();
nurseMapping.setEventID(event);

nurseMapping.setNurseID(nurse);
 medicalEventNurseMappingRepository.save(nurseMapping);


        //KIỂM TRA  VA LIÊN KẾT LOẠI SỰ KIỆN Y TẾ
        MedicalEventType eventType = medicalEventTypeRepository.findByTypeName(dto.getEventType())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại sự kiện: " + dto.getEventType()));
        MedicalEventTypeMapping typeMapping = new MedicalEventTypeMapping();
        typeMapping.setEventID(event);
        typeMapping.setEventType(eventType);
        medicalEventTypeMappingRepository.save(typeMapping);

        // 8. Gửi thông báo cho phụ huynh nếu cần
        if (dto.getHasParentBeenInformed() != null && dto.getHasParentBeenInformed()) {
            MedicalEventNotification notification = new MedicalEventNotification();
            notification.setParentID(parent);
            notification.setEventID(event);
            notification.setTitle("Thông báo sự kiện y tế đột suất");
            notification.setContent("Học sinh " + student.getFullName() + " gặp sự kiện y tế: " + dto.getEventType());
            medicalEventNotificationRepository.save(notification); // Lưu vào bảng MedicalEventNotification
        }

        // 9. Trả về DTO
        dto.setEventId(event.getEventID()); // Cập nhật eventId cho DTO
        return dto;
    }

    public MedicalEventDTO updateEventDTO(Integer eventId, MedicalEventDTO dto, Integer nurseId) {

        // Phải kiểm tra xem eventID có tồn tại không trước khi cập nhật để đảm bảo là có sự kiện đó mới cho update
        // 1. Kiểm tra nhân viên y tế có tồn tại không
        nurse = schoolNurseRepository.findById(nurseId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên y tế với ID: " + nurseId));
        parent = parentRepository.findById(dto.getParentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phụ huynh với ID: " + dto.getParentId()));
        // 2. Kiểm tra sự kiện y tế có tồn tại không
        MedicalEvent medicalEvent = medicalEventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sự kiện y tế với ID: " + dto.getEventId()));
        student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh với ID: " + dto.getStudentId()));
        // 3. Cập nhật thông tin sự kiện y tế
        medicalEvent.setUsageMethod(dto.getUsageMethod() != null ? dto.getUsageMethod() : "Y tá đã xử lí trước đó");
      // medicalEvent.setIsEmergency(dto.getIsEmergency() != null ? dto.getIsEmergency() : false);
        medicalEvent.setHasParentBeenInformed(dto.getHasParentBeenInformed() != null ? dto.getHasParentBeenInformed() : false);
        medicalEvent.setTemperature(dto.getTemperature());
        medicalEvent.setHeartRate(dto.getHeartRate());
        medicalEvent.setEventDateTime(dto.getEventDateTime());
        medicalEventRepository.save(medicalEvent);

        // 4. Cập nhật chi tiết sự kiện y tế

        MedicalEventDetailId eventDetailId = new MedicalEventDetailId(dto.getStudentId(), eventId);
        MedicalEventDetail eventDetail = medicalEventDetailRepository.findById(eventDetailId).orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết sự kiện y tế với EventID: " + eventId+ " và StudentID: " + dto.getStudentId()));
        eventDetail.setEvent(medicalEvent);
        eventDetail.setStudentID(student);
        eventDetail.setNote(dto.getNote());
        eventDetail.setResult(dto.getResult());
        eventDetail.setProcessingStatus(dto.getProcessingStatus());
        medicalEventDetailRepository.save(eventDetail);

//        // 5. Cập nhật loại sự kiện y tế nếu cần
//        MedicalEventType eventType = medicalEventTypeRepository.findByTypeName(dto.getEventType())
//                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại sự kiện: " + dto.getEventType()));
//        MedicalEventTypeMapping typeMapping = medicalEventTypeMappingRepository.findByeventID_eventID(eventId)
//                .orElse(new MedicalEventTypeMapping());
//        typeMapping.setEventID(medicalEvent);
//        typeMapping.setEventType(eventType);
//        medicalEventTypeMappingRepository.save(typeMapping);
//


//     Cập nhật liên kết nhân viên y tế
        MedicalEventNurseMappingId nurseMappingId = new MedicalEventNurseMappingId(nurse.getNurseID(), eventId);
        MedicalEventNurseMapping nurseMapping = medicalEventNurseMappingRepository.findById(nurseMappingId).orElseThrow();
//        MedicalEventNurseMapping nurseMapping = medicalEventNurseMappingRepository.findByeventID_eventID(eventId)
//                .orElse(new MedicalEventNurseMapping());
        nurseMapping.setNurseID(nurse);
        nurseMapping.setEventID(medicalEvent);
        medicalEventNurseMappingRepository.save(nurseMapping);

        // 9. Cập nhật thông báo cho phụ huynh nếu cần
        if (dto.getHasParentBeenInformed() != null && dto.getHasParentBeenInformed()) {

            MedicalEventNotificationId notificationId = new MedicalEventNotificationId(parent.getParentID(), eventId);
            MedicalEventNotification notification = medicalEventNotificationRepository.findById(notificationId)
                    .orElseThrow();
            notification.setParentID(parent);
            notification.setEventID(medicalEvent);
            notification.setTitle("Thông báo sự kiện y tế");
            notification.setContent("Học sinh " + student.getFullName() + " có cập nhật sự kiện y tế: " + dto.getEventType());
            medicalEventNotificationRepository.save(notification);
        }

        // 10. Trả về DTO
        dto.setEventId(medicalEvent.getEventID());
        return dto;
    }
}
