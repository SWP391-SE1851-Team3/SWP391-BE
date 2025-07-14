package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.MedicalEventDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicalEventDetailsDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicalEventUpdateDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicalSupplyQuantityDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Model.MedicalSupply;
import com.team_3.School_Medical_Management_System.Repositories.MedicalEvent_NurseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MedicalEventService {

    private MedicalSupplyRepository medicalSupplyRepository; // Model cho MedicalSupply


    private MedicalEvent_NurseRepository medicalNurseRepo; // Repository cho MedicalEvent_Nurse

    private EmailService emailService;

    private MedicalEvent_EventTypeRepo medicalEventType;

    private SchoolNurseRepository schoolNurseRepository; // Repository cho SchoolNurse

    private NotificationsParentRepository notificationsParentRepository; // Repository cho NotificationsParen

    private MedicalEvent_NurseRepo medicalEventNurseRepository; // Repository cho MedicalEvent_Nurse

    private MedicalEventTypeRepo medicalEventTypeRepo; // Repository cho MedicalEventType


    private MedicalEventRepo medicalEventRepository; // Repository cho MedicalEvent


    private ParentRepository parentRepository; // Repository cho Parent

    private StudentRepository studentRepository;
    // Repository cho Student

    private NotificationsMedicalEventDetailsRepository notificationsMedicalEventDetailsRepository;

    private MedicalEventDetailsRepository medicalEventDetailsRepository;


    private MedicalEvent_EventTypeRepo medicalEventEventTypeRepository; // Repository cho MedicalEvent_EventType

    private MedicalEvent_NurseRepo medicalEventNurseRepo; // Repository cho MedicalEvent_Nurse


    @Autowired
    public MedicalEventService(MedicalSupplyRepository medicalSupplyRepository, MedicalEvent_NurseRepository medicalNurseRepo, EmailService emailService, MedicalEvent_EventTypeRepo medicalEventType, SchoolNurseRepository schoolNurseRepository, NotificationsParentRepository notificationsParentRepository, MedicalEvent_NurseRepo medicalEventNurseRepository, MedicalEventTypeRepo medicalEventTypeRepo, MedicalEventRepo medicalEventRepository, ParentRepository parentRepository, StudentRepository studentRepository, NotificationsMedicalEventDetailsRepository notificationsMedicalEventDetailsRepository, MedicalEventDetailsRepository medicalEventDetailsRepository, MedicalEvent_EventTypeRepo medicalEventEventTypeRepository, MedicalEvent_NurseRepo medicalEventNurseRepo) {
        this.medicalSupplyRepository = medicalSupplyRepository;
        this.medicalNurseRepo = medicalNurseRepo;
        this.emailService = emailService;
        this.medicalEventType = medicalEventType;
        this.schoolNurseRepository = schoolNurseRepository;
        this.notificationsParentRepository = notificationsParentRepository;
        this.medicalEventNurseRepository = medicalEventNurseRepository;
        this.medicalEventTypeRepo = medicalEventTypeRepo;
        this.medicalEventRepository = medicalEventRepository;
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
        this.notificationsMedicalEventDetailsRepository = notificationsMedicalEventDetailsRepository;
        this.medicalEventDetailsRepository = medicalEventDetailsRepository;
        this.medicalEventEventTypeRepository = medicalEventEventTypeRepository;
        this.medicalEventNurseRepo = medicalEventNurseRepo;
    }





    // mình thiếu API dựa vào ID học sinh lấy Thôgn tin của Cha
    // Thiếu API để thông tin tên sự kiên và trả về ID sự kiện
    //===============================================================================================
    @Transactional
    public MedicalEventDTO createEmergencyEvent(MedicalEventDTO dto) {
        // Tạo mới một MedicalEvent


        MedicalEvent event = new MedicalEvent();
        event.setUsageMethod(dto.getUsageMethod());
        event.setIsEmergency(true); // Đặt là sự kiện khẩn cấp
        event.setHasParentBeenInformed(dto.isHasParentBeenInformed());
        event.setTemperature(dto.getTemperature());
        event.setHeartRate(dto.getHeartRate());
        event.setEventDateTime(dto.getEventDateTime());


        Optional<MedicalEventType> me = medicalEventTypeRepo.findById(dto.getEventTypeId());
        if (me.isEmpty()) {
            throw new RuntimeException("Loại sự kiện không tồn tại với ID: " + dto.getEventTypeId());
        }

        // Kiểm tra xem học sinh có tồn tại trong cơ sở dữ liệu không
        Optional<Student> studentOptional = studentRepository.findById(dto.getStudentId());
        if (studentOptional.isEmpty()) {
            throw new RuntimeException("Học sinh không tồn tại với ID: " + dto.getStudentId());
        }
        // Kiểm tra phụ huynh
        Optional<Parent> parent = parentRepository.findById(dto.getParentID());
        if (parent.isEmpty()) {
            throw new RuntimeException("Phụ huynh không tồn tại trong hệ thống.");
        }
        event.setParent(parent.get());
        // Lưu sự kiện
        Optional<SchoolNurse> nurseOptional = schoolNurseRepository.findById(dto.getNurseId());
        if (nurseOptional.isEmpty()) {
            throw new RuntimeException("Y tá không tồn tại với ID: " + dto.getNurseId());
        }

        event.setCreatedByNurse(nurseOptional.get());
        event.setUpdatedByNurse(nurseOptional.get());


    for (MedicalSupplyQuantityDTO supplyDTO : dto.getMedicalSupplies()) {
            // Kiểm tra vật tư
            Optional<MedicalSupply> supplyOptional = medicalSupplyRepository.findById(supplyDTO.getMedicalSupplyId());
            if (supplyOptional.isEmpty()) {
                throw new EntityNotFoundException("Vật tư y tế không tồn tại với ID: " + supplyDTO.getMedicalSupplyId());
            }

            MedicalSupply supply = supplyOptional.get();

            // Kiểm tra số lượng
            if (supplyDTO.getQuantityUsed() <= 0) {
                throw new IllegalArgumentException("Số lượng sử dụng phải lớn hơn 0 cho vật tư: " + supplyDTO.getSupplyName());
            }
            if (supplyDTO.getQuantityUsed() > supply.getQuantityAvailable()) {
                throw new IllegalArgumentException("Không đủ " + supplyDTO.getSupplyName() + " trong kho");
            }

            // Cập nhật số lượng trong kho
            int qA = supply.getQuantityAvailable() - supplyDTO.getQuantityUsed();
            supply.setQuantityAvailable(qA);
            medicalSupplyRepository.save(supply);

            // Thêm vật tư vào sự kiện
            event.addMedicalSupply(supply, supplyDTO.getQuantityUsed());
        }



        // vif đầu tiên cũng là người y tá này luônn
        MedicalEvent savedEvent = medicalEventRepository.save(event);
        //========================================================================================

        MedicalEvent_Nurse medicalEventNurse = new MedicalEvent_Nurse();
        medicalEventNurse.setMedicalEvent(event);
        medicalEventNurse.setSchoolNurse(nurseOptional.get());
        medicalEventNurseRepository.save(medicalEventNurse);


        MedicalEvent_EventType m = new MedicalEvent_EventType();
        m.setEventId(savedEvent.getEventID());
        m.setEventTypeId(dto.getEventTypeId());
        medicalEventType.save(m);
        // Tạo chi tiết sự kiện

        MedicalEventDetails details = new MedicalEventDetails();
        details.setStudent(studentOptional.get());
        details.setMedicalEvent(savedEvent);
        details.setNote(dto.getNote());
        details.setResult(dto.getResult());
        details.setProcessingStatus(dto.getProcessingStatus());
        details.setCreatedByNurse(nurseOptional.get());
        details.setUpdatedByNurse(nurseOptional.get());  /// y tas ddầu cũng là người update

        medicalEventDetailsRepository.save(details);

        if (savedEvent.getHasParentBeenInformed()) {

            // String currentUser = "Y tá trường cấp 1 ...."; // Thay thế bằng tên người dùng hiện tại
            String title = "Thông báo sự kiện y tế khẩn cấp tại trường";
            String content = String.format(
                    "Kính gửi phụ huynh %s,\n\n" +
                            "Có sự kiện y tế khẩn cấp xảy ra vào %s tại trường học. " +
                            "Thông tin: Con anh/chị %s là: %s. Em đã bị %s tại trường học. " +
                            "Vui lòng liên h�� số điện thoại trường (\"19001818\") để biết thêm chi tiết.",
                    parent.get().getFullName(),
                    savedEvent.getEventDateTime(),
                    parent.get().getFullName(),
                    studentOptional.get().getFullName(),
                    me.get().getTypeName()
            );

            NotificationsParent notification = new NotificationsParent();
            notification.setParent(parent.get());
            notification.setTitle(title);
            notification.setContent(content);
            notification.setCreateAt(savedEvent.getEventDateTime());

            notificationsParentRepository.save(notification);
            try {
                // Gửi email với thông tin người dùng và thời gian
                emailService.sendHtmlNotificationEmailMedicalEvent(parent.get(), title, content, notification.getNotificationId(), event.getCreatedByNurse().getFullName());

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
        r.setParentID(savedEvent.getParent().getParentID());
        r.setTypeName(me.get().getTypeName());
        r.setStudentId(dto.getStudentId());
        r.setNote(details.getNote());
        r.setNurseId(nurseOptional.get().getNurseID());
        r.setNurseName(nurseOptional.get().getFullName());
        r.setUpdatedByNurseId(nurseOptional.get().getNurseID());
        r.setUpdatedByNurseName(nurseOptional.get().getFullName());
        r.setResult(details.getResult());
        r.setProcessingStatus(details.getProcessingStatus());
        r.setEventTypeId(dto.getEventTypeId());

        List<MedicalSupplyQuantityDTO> medicalSupplies = new ArrayList<>();
        for (MedicalEventMedicalSupply link : savedEvent.getMedicalEventMedicalSupplies()) {
            MedicalSupplyQuantityDTO d = new MedicalSupplyQuantityDTO(
                    link.getMedicalSupply().getMedicalSupplyId(),
                    link.getMedicalSupply().getSupplyName(),
                    link.getMedicalSupply().getUnit(),
                    link.getQuantityUsed()
            );
            medicalSupplies.add(d);
        }
        r.setMedicalSupplies(medicalSupplies);
        return r;

    }

    @Transactional
    public List<MedicalSupplyQuantityDTO> getMedicalSuppliesForEvent(Integer eventId) {
        Optional<MedicalEvent> event = medicalEventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new EntityNotFoundException("Sự kiện y tế không tồn tại với ID: " + eventId);
        }

        List<MedicalSupplyQuantityDTO> result = new ArrayList<>();

        for (MedicalEventMedicalSupply link : event.get().getMedicalEventMedicalSupplies()) {
            MedicalSupplyQuantityDTO dto = new MedicalSupplyQuantityDTO(
                    link.getMedicalSupply().getMedicalSupplyId(),
                    link.getMedicalSupply().getSupplyName(),
                    link.getMedicalSupply().getUnit(),
                    link.getQuantityUsed()
            );
            result.add(dto);
        }

        return result;
    }

    @Transactional
    public MedicalEventUpdateDTO updateMedicalEvent(int eventId, MedicalEventUpdateDTO dto, Integer eventTypeId) {
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

            Optional<SchoolNurse> nurseOptional = schoolNurseRepository.findById(dto.getNurseId());
            if (nurseOptional.isEmpty()) {
                throw new RuntimeException("Y tá không tồn tại với ID: " + dto.getNurseId());
            }


            MedicalEvent event = optionalEvent.get();
            // Cập nhật thông tin cơ bản
            event.setUsageMethod(dto.getUsageMethod());
            event.setIsEmergency(dto.getIsEmergency());
            event.setHasParentBeenInformed(dto.getHasParentBeenInformed());
            event.setTemperature(dto.getTemperature());
            event.setHeartRate(dto.getHeartRate());
            event.setEventDateTime(dto.getEventDateTime());
            event.setParent(event.getParent());
            event.setUpdatedByNurse(nurseOptional.get()); // Cập nhật người y tá đã cập nhật sự kiện


           // Khôi phục số lượng v���t tư cũ

//            for (MedicalEventMedicalSupply oldLink : event.getMedicalEventMedicalSupplies()) {
//
//                MedicalSupply oldSupply = oldLink.getMedicalSupply();
//                int sum = oldSupply.getQuantityAvailable() + oldLink.getQuantityUsed();
//                oldSupply.setQuantityAvailable(sum);
//                medicalSupplyRepository.save(oldSupply);
//            }
            medicalEventRepository.deleteMedicalEventMedicalSuppliesByEventId(eventId);

             event.getMedicalEventMedicalSupplies().clear();



            // Bước 10: Cập nhật danh sách vật tư mới
            for (MedicalSupplyQuantityDTO supplyDTO : dto.getMedicalSupplies()) {
                Optional<MedicalSupply> supplyOptional = medicalSupplyRepository.findById(supplyDTO.getMedicalSupplyId());
                if (supplyOptional.isEmpty()) {
                    throw new EntityNotFoundException("Vật tư y tế không tồn tại với ID: " + supplyDTO.getMedicalSupplyId());
                }

                MedicalSupply supply = supplyOptional.get();

                if (supplyDTO.getQuantityUsed() <= 0) {
                    throw new IllegalArgumentException("Số lượng sử dụng phải lớn hơn 0 cho vật tư: " + supplyDTO.getSupplyName());
                }
                if (supplyDTO.getQuantityUsed() > supply.getQuantityAvailable()) {
                    throw new IllegalArgumentException("Không đủ " + supplyDTO.getSupplyName() + " trong kho");
                }
                int newQuantity = supply.getQuantityAvailable() - supplyDTO.getQuantityUsed();
                supply.setQuantityAvailable(newQuantity);
                medicalSupplyRepository.save(supply);

                event.addMedicalSupply(supply, supplyDTO.getQuantityUsed());
            }


            // Lưu sự kiện đã cập nhật
            MedicalEvent updatedEvent = medicalEventRepository.save(event);

            medicalEventType.deleteByMedicalEvent_EventID(eventId);
            MedicalEvent_EventType m = new MedicalEvent_EventType();
            m.setEventId(optionalEvent.get().getEventID());
            m.setEventTypeId(eventTypeId);
            medicalEventType.save(m);


            // Xử lý MedicalEventDetails
            Integer studentId = dto.getStudentId();
            if (studentId == null) {
                throw new RuntimeException("StudentID không được để trống");
            }

            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isEmpty()) {
                throw new RuntimeException("Học sinh không tồn tại với ID: " + studentId);
            }


            // Tạo MedicalEventDetailsId
            MedicalEventDetailsId detailsId = new MedicalEventDetailsId(studentId, eventId);

            // Kiểm tra và xử lý MedicalEventDetails
            Optional<MedicalEventDetails> optionalDetails = medicalEventDetailsRepository.findById(detailsId);
            MedicalEventDetails details;
            if (optionalDetails.isPresent()) {
                details = optionalDetails.get();
            } else {
                throw new RuntimeException("MedicalEventDetails không tồn tại cho StudentID: " + studentId + " và EventID: " + eventId);
            }

            // Cập nhật thông tin MedicalEventDetails
            details.setNote(dto.getNote());
            details.setResult(dto.getResult());
            details.setProcessingStatus(dto.getProcessingStatus());
            details.setUpdatedByNurse(nurseOptional.get()); // Cập nhật người y tá đã cập nhật
            // Lưu MedicalEventDetails
            medicalEventDetailsRepository.save(details);


            MedicalEvent_Nurse existingNurseRelation = medicalNurseRepo.findByMedicalEvent_EventIDAndSchoolNurse_NurseID(eventId, dto.getNurseId());


            if (existingNurseRelation == null) {
                MedicalEvent_Nurse medicalEventNurse = new MedicalEvent_Nurse();
                medicalEventNurse.setMedicalEvent(updatedEvent);
                medicalEventNurse.setSchoolNurse(nurseOptional.get());
                medicalEventNurseRepository.save(medicalEventNurse);
            }


            MedicalEventUpdateDTO result = new MedicalEventUpdateDTO();
            result.setUsageMethod(updatedEvent.getUsageMethod());
            result.setIsEmergency(updatedEvent.getIsEmergency());
            result.setHasParentBeenInformed(updatedEvent.getHasParentBeenInformed());
            result.setTemperature(updatedEvent.getTemperature());
            result.setHeartRate(updatedEvent.getHeartRate());
            result.setEventDateTime(updatedEvent.getEventDateTime());
            result.setStudentId(studentId);
            result.setNote(details.getNote());
            result.setResult(details.getResult());
            result.setProcessingStatus(details.getProcessingStatus());
            result.setNurseId(nurseOptional.get().getNurseID());
            result.setNurseName(nurseOptional.get().getFullName());

            List<MedicalSupplyQuantityDTO> medicalSupplies = new ArrayList<>();
            for (MedicalEventMedicalSupply link : updatedEvent.getMedicalEventMedicalSupplies()) {
                MedicalSupplyQuantityDTO d = new MedicalSupplyQuantityDTO(
                        link.getMedicalSupply().getMedicalSupplyId(),
                        link.getMedicalSupply().getSupplyName(),
                        link.getMedicalSupply().getUnit(),
                        link.getQuantityUsed()
                );
                medicalSupplies.add(d);
            }
            result.setMedicalSupplies(medicalSupplies);

            return result;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Lỗi ràng buộc dữ liệu: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật sự kiện y tế: " + e.getMessage(), e);
        }
    }

    @Transactional
    public MedicalEventDetailsDTO getMedicalEventDetails(Integer eventId) {
        // Tìm MedicalEvent với EventTypes
        MedicalEvent medicalEvent = medicalEventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy MedicalEvent với ID: " + eventId));

        // Tìm MedicalEventDetails theo EventID
        MedicalEventDetails medicalEventDetails = medicalEventDetailsRepository.findByMedicalEvent_EventID(eventId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy MedicalEventDetails cho EventID: " + eventId));

        // Tìm danh sách MedicalEvent_EventType theo EventID
        List<MedicalEvent_EventType> eventTypeRelations = medicalEventEventTypeRepository.findByMedicalEvent_EventID(eventId);

        // Tạo DTO phản hồi
        MedicalEventDetailsDTO dto = new MedicalEventDetailsDTO();
        dto.setEventId(medicalEvent.getEventID());
        dto.setUsageMethod(medicalEvent.getUsageMethod());
        dto.setIsEmergency(medicalEvent.getIsEmergency());
        dto.setHasParentBeenInformed(medicalEvent.getHasParentBeenInformed());
        dto.setTemperature(medicalEvent.getTemperature());
        dto.setHeartRate(medicalEvent.getHeartRate());
        dto.setEventDateTime(medicalEvent.getEventDateTime());
        dto.setNote(medicalEventDetails.getNote());
        dto.setResult(medicalEventDetails.getResult());
        dto.setProcessingStatus(medicalEventDetails.getProcessingStatus());

        // Ánh xạ Student
        Student student = medicalEventDetails.getStudent();
        dto.setStudentId(student.getStudentID());
        dto.setFullName(student.getFullName());
        dto.setGender(student.getGender());
        dto.setClassName(student.getClassName());

        // Lấy danh sách EventTypeName bằng vòng lặp for
        List<String> eventTypeNames = new ArrayList<>();
        for (MedicalEvent_EventType eventTypeRelation : eventTypeRelations) {
            MedicalEventType eventType = eventTypeRelation.getEventType();
            if (eventType != null) {
                eventTypeNames.add(eventType.getTypeName());
                break; // Thoát vòng lặp sau khi lấy EventTypeName đầu tiên
            }
        }
        dto.setEventTypeNames(eventTypeNames);

        if (medicalEvent.getCreatedByNurse() != null) {
            dto.setCreatedByNurseId(medicalEvent.getCreatedByNurse().getNurseID());
            dto.setCreatedByNurseName(medicalEvent.getCreatedByNurse().getFullName());
        }
        if (medicalEvent.getUpdatedByNurse() != null) {
            dto.setUpdatedByNurseId(medicalEvent.getUpdatedByNurse().getNurseID());
            dto.setUpdatedByNurseName(medicalEvent.getUpdatedByNurse().getFullName());
        }
        dto.setListMedicalSupplies(getMedicalSuppliesForEvent(eventId));
        return dto;
    }

    @Transactional
    public void deleteMedicalEvent(Integer eventId) {
        // Kiểm tra xem sự kiện có tồn tại không
        MedicalEvent medicalEvent = medicalEventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sự kiện y tế với ID: " + eventId));

        // Xóa NotificationsMedicalEventDetails liên quan
        Notifications_MedicalEventDetailsId notificationsId = new Notifications_MedicalEventDetailsId();
        notificationsId.setEventId(eventId);
        notificationsId.setParentId(medicalEvent.getParent().getParentID());

        notificationsMedicalEventDetailsRepository.deleteById(notificationsId);
        medicalEventNurseRepo.deleteByMedicalEvent_EventID(eventId);
        medicalEventType.deleteByMedicalEvent_EventID(eventId);
        // Xóa MedicalEventDetails liên quan
        medicalEventDetailsRepository.deleteByMedicalEvent_EventID(eventId);

        // Xóa MedicalEvent_EventType liên quan
        //medicalEventType.deleteByMedicalEvent_EventID(eventId);

        // Xóa MedicalEvent
        medicalEventRepository.delete(medicalEvent);

    }
}

