package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.MedicalEventDTO;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
private ParentRepo parentRepo;
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

    // Tìm MedicalEventDetail theo EventID
    public MedicalEventDetail findMedicalEventDetailByEventId(Integer eventId) {
        List<MedicalEventDetail> allDetails = medicalEventDetailRepository.findAll();
        for (MedicalEventDetail detail : allDetails) {
            if (detail.getEvent().getEventID().equals(eventId)) {
                return detail;
            }
        }
        throw new RuntimeException("Không tìm thấy chi tiết sự kiện y tế với EventID: " + eventId);
    }

    // Tìm danh sách MedicalEvent theo ParentID
    public List<MedicalEvent> findMedicalEventsByParentId(Integer parentId) {
        List<MedicalEvent> allEvents = medicalEventRepository.findAll();
        List<MedicalEvent> result = new ArrayList<>();
        for (MedicalEvent event : allEvents) {
            if (event.getParent().getParentID() == parentId) {
                result.add(event);
            }
        }
        return result;
    }

    // Tìm MedicalEvent theo ID
    public MedicalEvent findMedicalEventById(Integer id) {
        return medicalEventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sự kiện y tế với ID: " + id));
    }

    // Tìm MedicalEventTypeMapping theo EventID
    public MedicalEventTypeMapping findMedicalEventTypeMappingByEventId(Integer eventId) {
        List<MedicalEventTypeMapping> allMappings = medicalEventTypeMappingRepository.findAll();
        for (MedicalEventTypeMapping mapping : allMappings) {
            if (mapping.getEventID().getEventID().equals(eventId)) {
                return mapping;
            }
        }
        throw new RuntimeException("Không tìm thấy ánh xạ loại sự kiện y tế với EventID: " + eventId);
    }

    // Tìm Parent theo ParentID
    public Parent findParentById(Integer parentId) {
        return parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phụ huynh với ID: " + parentId));
    }

    // Lấy danh sách MedicalEventDTO theo ParentID
    public List<MedicalEventDTO> getMedicalEventsByParentId(Integer parentId) {
        // Bước 1: Kiểm tra đầu vào
        if (parentId == null) {
            throw new IllegalArgumentException("ParentID không được để trống");
        }   

        // Bước 2: Kiểm tra phụ huynh
        Parent parent = findParentById(parentId);

        // Bước 3: Tìm tất cả các sự kiện y tế của phụ huynh theo ID
        List<MedicalEvent> events = findMedicalEventsByParentId(parentId);

        // Bước 4: Chuyển thành danh sách DTO để trả về cho FE
        List<MedicalEventDTO> result = new ArrayList<>();
        for (MedicalEvent event : events) {
            MedicalEventDTO dto = new MedicalEventDTO();
            dto.setEventId(event.getEventID());
            dto.setParentId(event.getParent().getParentID());
            dto.setUsageMethod(event.getUsageMethod());
            dto.setIsEmergency(event.getIsEmergency());
            dto.setHasParentBeenInformed(event.getHasParentBeenInformed());
            dto.setTemperature(event.getTemperature());
            dto.setHeartRate(event.getHeartRate());
            dto.setEventDateTime(event.getEventDateTime());

            // Bước 4.1: Tìm chi tiết sự kiện y tế
            try {
                MedicalEventDetail detail = findMedicalEventDetailByEventId(event.getEventID());
                dto.setStudentId(detail.getStudentID().getStudentID());
                dto.setNote(detail.getNote());
                dto.setResult(detail.getResult());
                dto.setProcessingStatus(detail.getProcessingStatus());
            } catch (RuntimeException e) {
                dto.setStudentId(null);
                dto.setNote(null);
                dto.setResult(null);
                dto.setProcessingStatus(null);
            }

            // Bước 4.2: Lấy loại sự kiện y tế
            try {
                MedicalEventTypeMapping typeMapping = findMedicalEventTypeMappingByEventId(event.getEventID());
                dto.setEventType(typeMapping.getEventType().getTypeName());
            } catch (RuntimeException e) {
                dto.setEventType(null);
            }

            result.add(dto);
        }

        // Bước 5: Trả về danh sách
        return result;
    }

    // Các phương thức khác (createMedicalEvent, updateMedicalEvent, ...)
}

