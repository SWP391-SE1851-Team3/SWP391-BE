package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.MedicalEventDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicalEventUpdateDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    private NotificationsParentRepository notificationsParentRepository; // Repository cho NotificationsParen

    @Autowired
    private MedicalEventTypeRepo medicalEventTypeRepo; // Repository cho MedicalEventType

    @Autowired
    private MedicalEventRepo medicalEventRepository; // Repository cho MedicalEvent

    @Autowired
    private ParentRepository parentRepository; // Repository cho Parent
    @Autowired
    private StudentRepository studentRepository;
// Repository cho Student
    @Autowired
    private NotificationsMedicalEventDetailsRepository notificationsMedicalEventDetailsRepository;
    @Autowired
    private MedicalEventDetailsRepository medicalEventDetailsRepository;
    @Autowired
    private JavaMailSender mailSender;
@Autowired
private StudentRepository studentRepo;
    // mình thiếu API dựa vào ID học sinh lấy Thôgn tin của Cha
    // Thiếu API để thông tin tên sự kiên và trả về ID sự kiện
    //===============================================================================================
    public MedicalEventDTO createEmergencyEvent(MedicalEventDTO dto, int studentId, String note, String result, String processingStatus, Integer eventTypeId ) {
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

        if (savedEvent.getHasParentBeenInformed()) {
            String title = "Thông báo sự kiện y tế khẩn cấp tại trường >>>>>";
            String content = String.format(
                    "Kính gửi phụ huynh %s,\n\nCó sự kiện y tế khẩn cấp xảy ra vào %s tại trường học ...... " +
                            "Thông tin: Con anh/chị %s là: %s.Em đã bị %s tại trường học . Vui lòng liên hệ số điện thoại trường (\"19001818\") để biết thêm chi tiết.\n\nTrân trọng, Ban y tế trường học.",
                    parent.get().getFullName(),
                    savedEvent.getEventDateTime(),
                    parent.get().getFullName(),
                    student.get().getFullName(),
                    me.get().getTypeName()
            );

            NotificationsParent notification = new NotificationsParent();
            notification.setParent(parent.get());
            notification.setTitle(title);
            notification.setContent(content);
            notification.setCreateAt(savedEvent.getEventDateTime());
            notificationsParentRepository.save(notification);
            // Gửi email thông báo cho phụ huynh

            NotificationsMedicalEventDetails detailsNotification = new NotificationsMedicalEventDetails();
            detailsNotification.setParentId(savedEvent.getParent().getParentID());
            detailsNotification.setEventId(savedEvent.getEventID());
            detailsNotification.setTitle(title);
            detailsNotification.setContent(content);

            try {
                sendNotificationEmail(parent.get(), title, content, notification.getNotificationId());
            } catch (Exception e) {
                throw new RuntimeException("Lỗi khi gửi email thông báo: " + e.getMessage(), e);
            }

        }


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
      //  r.setStudent(sameClassName);
        return r;

    }

    private void sendNotificationEmail(Parent parent, String title, String content, Integer notificationId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(parent.getEmail());
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
        // Cập nhật trạng thái thông báo thành true sau khi gửi thành công
        NotificationsParent notification = notificationsParentRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + notificationId));
        // Kiểm tra xem notification có tồn tại không
        if (notification != null) {
            notification.setStatus(true);
            notificationsParentRepository.save(notification);
        }
    }

    public MedicalEventUpdateDTO updateMedicalEvent(int eventId, MedicalEventUpdateDTO dto,Integer eventTypeId ) {
        try {
            // Tìm sự kiện
            Optional<MedicalEvent> optionalEvent = medicalEventRepository.findById(eventId);
            if (optionalEvent.isEmpty()) {
                throw new RuntimeException("Sự kiện y tế không tồn tại với ID: " + eventId);
            }
            Optional<MedicalEventType> me = medicalEventTypeRepo.findById(eventTypeId);
            if (me.isEmpty()) {
                throw new RuntimeException("Loại sự kiện không tồn tại với ID: " + eventTypeId);
            }
            MedicalEvent_EventType m = new MedicalEvent_EventType();
            m.setEventId(optionalEvent.get().getEventID());
            m.setEventTypeId(eventTypeId);
            medicalEventType.save(m);


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


}



