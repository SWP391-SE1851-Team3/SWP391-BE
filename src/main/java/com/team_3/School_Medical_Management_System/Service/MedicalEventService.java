package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.MedicalEventDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicalEventUpdateDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MedicalEventService {
    @Autowired
    private MedicalEvent_EventTypeRepo medicalEventType;


    @Autowired
    private MedicalEventTypeRepo medicalEventTypeRepo; // Repository cho MedicalEventType

    @Autowired
    private MedicalEventRepo medicalEventRepository; // Repository cho MedicalEvent

    @Autowired
    private ParentRepository parentRepository; // Repository cho Parent

    @Autowired
    private StudentRepository studentRepository; // Repository cho Student

    @Autowired
    private MedicalEventDetailsRepository medicalEventDetailsRepository;

    // mình thiếu API dựa vào ID học sinh lấy Thôgn tin của Cha
    // Thiếu API để thông tin tên sự kiên và trả về ID sự kiện
    //===============================================================================================
    public MedicalEventDTO createEmergencyEvent(MedicalEventDTO dto, int studentId, String note, String result, String processingStatus, Integer eventTypeId) {
        // Tạo mới một MedicalEvent
        MedicalEvent event = new MedicalEvent();
        event.setUsageMethod(dto.getUsageMethod());
        event.setIsEmergency(true); // Đặt là sự kiện khẩn cấp
        event.setHasParentBeenInformed(dto.isHasParentBeenInformed());
        event.setTemperature(dto.getTemperature());
        event.setHeartRate(dto.getHeartRate());
        event.setEventDateTime(dto.getEventDateTime());








        Optional<MedicalEventType> me = medicalEventTypeRepo.findById(eventTypeId);
        if (me.isEmpty()) {
            throw new RuntimeException("Loại sự kiện không tồn tại với ID: " + eventTypeId);
        }
        // Kiểm tra xem học sinh có tồn tại trong cơ sở dữ liệu không
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Học sinh không tồn tại với ID: " + studentId);
        }
        // Kiểm tra phụ huynh
        Optional<Parent> parent = parentRepository.findById(dto.getParentId());
        if (parent.isEmpty()) {
            throw new RuntimeException("Phụ huynh không tồn tại trong hệ thống.");
        }
        event.setParent(parent.get());

        // Lưu sự kiện
        MedicalEvent savedEvent = medicalEventRepository.save(event);


        MedicalEvent_EventType m = new MedicalEvent_EventType();
        m.setEventId(savedEvent.getEventID());
        m.setEventTypeId(eventTypeId);
        medicalEventType.save(m);
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
        r.setTpyeName(me.get().getTypeName());
        r.setStudentId(studentId);
        return r;
    }

    // Cập nhật sự kiện y tế
    @Transactional
    public MedicalEventUpdateDTO updateMedicalEvent(int eventId, MedicalEventUpdateDTO dto) {
        try {
            // Tìm sự kiện
            Optional<MedicalEvent> optionalEvent = medicalEventRepository.findById(eventId);
            if (optionalEvent.isEmpty()) {
                throw new RuntimeException("Sự kiện y tế không tồn tại với ID: " + eventId);
            }

            MedicalEvent event = optionalEvent.get();
            // Cập nhật thông tin cơ bản
            event.setUsageMethod(dto.getUsageMethod());
            event.setIsEmergency(dto.getIsEmergency());
            event.setHasParentBeenInformed(dto.getHasParentBeenInformed());
            event.setTemperature(dto.getTemperature());
            event.setHeartRate(dto.getHeartRate());
            event.setEventDateTime(dto.getEventDateTime());

            // Kiểm tra và cập nhật phụ huynh
            Optional<Parent> parent = parentRepository.findById(dto.getParentId());
            if (parent.isEmpty()) {
                throw new RuntimeException("Phụ huynh không tồn tại với ID: " + dto.getParentId());
            }
            event.setParent(parent.get());

            // Lưu sự kiện đã cập nhật
            MedicalEvent updatedEvent = medicalEventRepository.save(event);

            // Xử lý MedicalEventDetails
            Integer studentId = dto.getStudentId();
            if (studentId == null) {
                throw new RuntimeException("StudentID không được để trống");
            }

            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isEmpty()) {
                throw new RuntimeException("Học sinh không tồn tại với ID: " + studentId);
            }
            Student student = optionalStudent.get();

            // Tạo MedicalEventDetailsId
            MedicalEventDetailsId detailsId = new MedicalEventDetailsId(studentId, eventId);

            // Kiểm tra và xử lý MedicalEventDetails
            Optional<MedicalEventDetails> optionalDetails = medicalEventDetailsRepository.findById(detailsId);
            MedicalEventDetails details;
            if (optionalDetails.isPresent()) {
                details = optionalDetails.get();
            } else {
                // Nếu không tồn tại và bạn không muốn tạo mới, ném ngoại lệ
                throw new RuntimeException("MedicalEventDetails không tồn tại cho StudentID: " + studentId + " và EventID: " + eventId);
            }

            // Cập nhật thông tin MedicalEventDetails
            details.setNote(dto.getNote());
            details.setResult(dto.getResult());
            details.setProcessingStatus(dto.getProcessingStatus());

            // Lưu MedicalEventDetails
            medicalEventDetailsRepository.save(details);

            // Tạo DTO để trả về
            MedicalEventUpdateDTO result = new MedicalEventUpdateDTO();
            result.setEventId(updatedEvent.getEventID());
            result.setUsageMethod(updatedEvent.getUsageMethod());
            result.setIsEmergency(updatedEvent.getIsEmergency());
            result.setHasParentBeenInformed(updatedEvent.getHasParentBeenInformed());
            result.setTemperature(updatedEvent.getTemperature());
            result.setHeartRate(updatedEvent.getHeartRate());
            result.setEventDateTime(updatedEvent.getEventDateTime());
            result.setParentId(updatedEvent.getParent().getParentID());
            result.setStudentId(studentId);
            result.setNote(details.getNote());
            result.setResult(details.getResult());
            result.setProcessingStatus(details.getProcessingStatus());

            return result;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Lỗi ràng buộc dữ liệu: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật sự kiện y tế: " + e.getMessage(), e);
        }
    }


    // Lấy danh sách sự kiện y tế đột xuất của phụ huynh
    public List<MedicalEventDTO> getAllMedicalEventsByParent(int parentId) {
        // Bước 1: Kiểm tra xem phụ huynh có tồn tại trong cơ sở dữ liệu không
        boolean parentExists = parentRepository.existsById(parentId);
        if (!parentExists) {
            throw new RuntimeException("Không tìm thấy phụ huynh với ID: " + parentId);
        }
        List<MedicalEvent> medicalEvents = new ArrayList<>();
        // Bước 2: Tìm tất cả sự kiện y tế của phụ huynh dựa trên ParentID
        List<MedicalEvent> listEvents = medicalEventRepository.findAll();
        for (MedicalEvent l : listEvents) {
            if (l.getParent().getParentID() == parentId) {
                medicalEvents.add(l);
            }
        }


        // Bước 3: Tạo danh sách DTO để lưu trữ kết quả
        List<MedicalEventDTO> eventDTOs = new ArrayList<>();

        // Bước 4: Duyệt qua từng sự kiện và chuyển đổi thành DTO
        for (MedicalEvent event : medicalEvents) {
            MedicalEventDTO dto = new MedicalEventDTO();
            dto.setEventId(event.getEventID()); // Gán ID sự kiện
            dto.setUsageMethod(event.getUsageMethod()); // Gán phương pháp sử dụng
            dto.setEmergency(event.getIsEmergency()); // Gán trạng thái khẩn cấp
            dto.setHasParentBeenInformed(event.getHasParentBeenInformed()); // Gán trạng thái thông báo phụ huynh
            dto.setTemperature(event.getTemperature()); // Gán nhiệt độ
            dto.setHeartRate(event.getHeartRate()); // Gán nhịp tim
            dto.setEventDateTime(event.getEventDateTime()); // Gán thời gian sự kiện
            dto.setParentId(event.getParent().getParentID()); // Gán ID phụ huynh trực tiếp từ parentID

            // Thêm DTO vào danh sách
            eventDTOs.add(dto);
        }

        // Bước 5: Trả về danh sách DTO
        return eventDTOs;
    }

}



