package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.MedicalEventDTO;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MedicalEventService {

    @Autowired
    private MedicalEventRepo medicalEventRepository; // Repository cho MedicalEvent

    @Autowired
    private ParentRepository parentRepository; // Repository cho Parent

    @Autowired
    private StudentRepository studentRepository; // Repository cho Student

    @Autowired
    private MedicalEventDetailsRepository medicalEventDetailsRepository;
    //===============================================================================================
    public MedicalEventDTO createEmergencyEvent(MedicalEventDTO dto, int studentId, String note, String result, String processingStatus) {
        // Tạo mới một MedicalEvent
        MedicalEvent event = new MedicalEvent();
        event.setUsageMethod(dto.getUsageMethod());
        event.setIsEmergency(true); // Đặt là sự kiện khẩn cấp
        event.setHasParentBeenInformed(dto.isHasParentBeenInformed());

       // event.setHasParentBeenInformed(dto.isHasParentBeenInformed());
        event.setTemperature(dto.getTemperature());
        event.setHeartRate(dto.getHeartRate());
        event.setEventDateTime(dto.getEventDateTime());

        // Kiểm tra phụ huynh
        Optional<Parent> parent = parentRepository.findById(dto.getParentId());
        if (parent.isEmpty()) {
            throw new RuntimeException("Phụ huynh không tồn tại");
        }
        event.setParent(parent.get());

        // Lưu sự kiện
        MedicalEvent savedEvent = medicalEventRepository.save(event);

        // Tạo chi tiết sự kiện
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isEmpty()) {
            throw new RuntimeException("Học sinh không tồn tại");
        }
        MedicalEventDetails details = new MedicalEventDetails();
        details.setStudent(student.get());
        details.setMedicalEvent(savedEvent);
        details.setNote(note);
        details.setResult(result);
        details.setProcessingStatus(processingStatus);
        medicalEventDetailsRepository.save(details);

        // Tạo DTO để trả về
        MedicalEventDTO r = new MedicalEventDTO();
        r.setEventId(savedEvent.getEventID());
        r.setUsageMethod(savedEvent.getUsageMethod());
        r.setEmergency(savedEvent.getIsEmergency());
        r.setHasParentBeenInformed(savedEvent.getHasParentBeenInformed());
        r.setTemperature(savedEvent.getTemperature());
        r.setHeartRate(savedEvent.getHeartRate());
        r.setEventDateTime(savedEvent.getEventDateTime());
        r.setParentId(savedEvent.getParent().getParentID());

        return r;
    }

    // Cập nhật sự kiện y tế
    public MedicalEventDTO updateMedicalEvent(int eventId, MedicalEventDTO dto) {
        // Tìm sự kiện
        Optional<MedicalEvent> optionalEvent = medicalEventRepository.findById(eventId);
        if (optionalEvent.isEmpty()) {
            throw new RuntimeException("Sự kiện y tế không tồn tại");
        }

        MedicalEvent event = optionalEvent.get();
        event.setUsageMethod(dto.getUsageMethod());
        event.setIsEmergency(dto.isEmergency());
        event.setHasParentBeenInformed(dto.isHasParentBeenInformed());
        event.setTemperature(dto.getTemperature());
        event.setHeartRate(dto.getHeartRate());
        event.setEventDateTime(dto.getEventDateTime());

        // Kiểm tra phụ huynh
        Optional<Parent> parent = parentRepository.findById(dto.getParentId());
        if (parent.isEmpty()) {
            throw new RuntimeException("Phụ huynh không tồn tại");
        }
        event.setParent(parent.get());

        // Lưu sự kiện đã cập nhật
        MedicalEvent updatedEvent = medicalEventRepository.save(event);

        // Tạo DTO để trả về
        MedicalEventDTO result = new MedicalEventDTO();
        result.setEventId(updatedEvent.getEventID());
        result.setUsageMethod(updatedEvent.getUsageMethod());
        result.setEmergency(updatedEvent.getIsEmergency());
        result.setHasParentBeenInformed(updatedEvent.getHasParentBeenInformed());
        result.setTemperature(updatedEvent.getTemperature());
        result.setHeartRate(updatedEvent.getHeartRate());
        result.setEventDateTime(updatedEvent.getEventDateTime());
        result.setParentId(updatedEvent.getParent().getParentID());

        return result;
    }

    // Lấy danh sách sự kiện y tế đột xuất của phụ huynh
    public List<MedicalEventDTO> getEmergencyEventsByParent(int parentId) {
        // Bước 1: Kiểm tra xem phụ huynh có tồn tại trong cơ sở dữ liệu không
        boolean parentExists = parentRepository.existsById(parentId);
        if (!parentExists) {
            throw new RuntimeException("Không tìm thấy phụ huynh với ID: " + parentId);
        }

        // Bước 2: Tìm tất cả sự kiện y tế đột xuất của phụ huynh
        // (chỉ lấy các sự kiện có isEmergency = true)
        List<MedicalEvent> emergencyEvents = medicalEventRepository.findByParentParentIDAndIsEmergencyTrue(parentId);

        // Bước 3: Tạo danh sách DTO để lưu trữ kết quả
        List<MedicalEventDTO> eventDTOs = new ArrayList<>();

        // Bước 4: Duyệt qua từng sự kiện và chuyển đổi thành DTO
        for (MedicalEvent event : emergencyEvents) {
            MedicalEventDTO dto = new MedicalEventDTO();
            dto.setEventId(event.getEventID()); // Gán ID sự kiện
            dto.setUsageMethod(event.getUsageMethod()); // Gán phương pháp sử dụng
            dto.setEmergency(event.getIsEmergency()); // Gán trạng thái khẩn cấp
            dto.setHasParentBeenInformed(event.getHasParentBeenInformed()); // Gán trạng thái thông báo phụ huynh
            dto.setTemperature(event.getTemperature()); // Gán nhiệt độ
            dto.setHeartRate(event.getHeartRate()); // Gán nhịp tim
            dto.setEventDateTime(event.getEventDateTime()); // Gán thời gian sự kiện
            dto.setParentId(event.getParent().getParentID()); // Gán ID phụ huynh

            // Thêm DTO vào danh sách
            eventDTOs.add(dto);
        }

        // Bước 5: Trả về danh sách DTO
        return eventDTOs;
    }


}


