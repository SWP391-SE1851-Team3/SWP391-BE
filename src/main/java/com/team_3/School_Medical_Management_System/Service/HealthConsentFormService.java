package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.ConsentFormRequestDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthCheck_StudentDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthConsentFormDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HealthConsentFormService {

    @Autowired
    private HealthConsentFormRepository healthConsentFormRepository;

    @Autowired
    private StudentInterFace studentRepository;

    @Autowired
    private HealthCheckScheduleRepository healthCheckScheduleRepository;

    @Autowired
    private NotificationsParentRepository notificationsParentRepository;

    @Autowired
    private HealthCheckStudentService healthCheckStudentService;
    @Autowired
    private EmailService emailService;

    // Update consent form with parent's decision
    public HealthConsentForm updateConsentForm(int formId, String isAgreed, String notes) {
        Optional<HealthConsentForm> optionalForm = healthConsentFormRepository.findById(formId);

        if (optionalForm.isPresent()) {
            HealthConsentForm form = optionalForm.get();
            String oldIsAgreed = form.getIsAgreed();
            form.setIsAgreed(isAgreed);
            form.setNotes(notes);
            HealthConsentForm savedForm = healthConsentFormRepository.save(form);
            // If isAgreed changed to "accepted" and was not previously "đồng ý", create HealthCheck_Student
            if (!"đồng ý".equalsIgnoreCase(oldIsAgreed) && "đồng ý".equalsIgnoreCase(isAgreed)) {
                HealthCheck_StudentDTO dto = new HealthCheck_StudentDTO();
                dto.setFormID(form.getFormID());
                dto.setHealth_ScheduleID(form.getHealthScheduleID());
                dto.setStudentID(form.getStudentID());
                dto.setStatus("Đang chờ");
                // You can set other default values for dto here if needed
                try {
                    healthCheckStudentService.createHealthCheckResults(dto);
                } catch (Exception e) {
                    // Optionally log or handle exception
                }
            }
            return savedForm;
        }

        return null;
    }

    // Get all consent forms for a specific health check schedule
    public List<HealthConsentForm> getConsentFormsBySchedule(int scheduleId) {
        return healthConsentFormRepository.findByHealthScheduleID(scheduleId);
    }

    // Get all agreed consent forms for a specific health check schedule
    public List<HealthConsentForm> getAgreedConsentFormsBySchedule(int scheduleId) {
        return healthConsentFormRepository.findByHealthScheduleIDAndIsAgreed(scheduleId, "Approved");
    }

    // Get all consent forms by parentId
    public List<HealthConsentForm> getConsentFormsByParentId(Integer parentId) {
        List<Student> students = studentRepository.getStudentsByParentID(parentId);
        return students.stream()
                .flatMap(student -> healthConsentFormRepository.findByStudentID(student.getStudentID()).stream())
                .collect(Collectors.toList());
    }

    // Get all consent forms
    public List<HealthConsentForm> getAllConsentForms() {
        return healthConsentFormRepository.findAll();
    }

    // Convert Entity to DTO
    public HealthConsentFormDTO convertToDTO(HealthConsentForm form) {
        HealthConsentFormDTO dto = new HealthConsentFormDTO();
        dto.setFormID(form.getFormID());
        dto.setStudentID(form.getStudentID()); // form.getStudent() trả về int studentID
        dto.setHealthScheduleID(form.getHealthScheduleID()); // form.getHealthCheckSchedule() trả về int healthScheduleID
        dto.setIsAgreed(form.getIsAgreed());
        dto.setNotes(form.getNotes());
        dto.setSend_date(form.getSend_date());
        dto.setExpire_date(form.getExpire_date());

        // Fetch student name and class name from database using studentID
        try {
            Optional<Student> studentOpt = studentRepository.findById(form.getStudentID());
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                dto.setStudentName(student.getFullName());
                dto.setClassName(student.getClassName());
                dto.setParentID(form.getParentID());
            }
        } catch (Exception e) {
            // Log error but continue processing
            System.err.println("Error fetching student info for ID " + form.getStudentID() + ": " + e.getMessage());
        }

        // Fetch health schedule name from database using healthScheduleID
        try {
            Optional<HealthCheck_Schedule> scheduleOpt = healthCheckScheduleRepository.findById(form.getHealthScheduleID());
            if (scheduleOpt.isPresent()) {
                HealthCheck_Schedule schedule = scheduleOpt.get();
                dto.setHealthScheduleName(schedule.getName());
            }
        } catch (Exception e) {
            // Log error but continue processing
            System.err.println("Error fetching schedule info for ID " + form.getHealthScheduleID() + ": " + e.getMessage());
        }

        return dto;
    }

    public void createConsentFormsForMultipleClasses(ConsentFormRequestDTO request) {
        try {
            List<HealthConsentForm> consentForms = new ArrayList<>();
            List<String> skippedClasses = new ArrayList<>();
            HealthCheck_Schedule schedule = healthCheckScheduleRepository.findById(request.getHealthScheduleId()).orElseThrow(() -> new RuntimeException("HealthCheck_Schedule not found with id: " + request.getHealthScheduleId()));

            // Lặp qua danh sách các lớp
            for (String className : request.getClassName()) {
                // Kiểm tra xem lớp này đã có form cho schedule này chưa
                List<HealthConsentForm> existingForms = healthConsentFormRepository.findByClassNameAndHealthScheduleID(className, request.getHealthScheduleId());

                if (!existingForms.isEmpty()) {
                    // Nếu đã có form cho lớp này với schedule này, bỏ qua
                    skippedClasses.add(className);
                    continue;
                }

                // Lấy danh sách học sinh theo tên lớp
                List<Student> students = studentRepository.findByClassName(className);

                // Tạo consent form cho từng học sinh
                for (Student student : students) {
                    // Create HealthConsentForm
                    HealthConsentForm consentForm = new HealthConsentForm();
                    consentForm.setStudentID(student.getStudentID());
                    consentForm.setParentID(student.getParent().getParentID());
                    consentForm.setHealthScheduleID(schedule.getHealth_ScheduleID()); // Set scheduleID thay vì Schedule object
                    consentForm.setSend_date(new Date());
                    consentForm.setExpire_date(request.getExpireDate());
                    consentForm.setIsAgreed("Chờ phản hồi"); // Set mặc định là "Chờ phản hồi"
                    consentForm.setNotes(request.getNotes());
                    consentForm.setCreatedByNurseID(request.getCreatedByNurseId());
                    consentForm.setUpdatedByNurseID(request.getUpdatedByNurseID());

                    // Thêm consentForm vào list
                    consentForms.add(consentForm);

                    // Create notification for parent
                    if (student.getParent() != null) {
                        String tittle = "Yêu cầu đồng ý kiểm tra sức khỏe cho học sinh " + student.getFullName();
                        String content = "Một đợt khám sức khỏe mới đã được lên lịch cho học sinh " + student.getFullName() +
                                ". Vui lòng xem và xác nhận đồng ý cho con em của bạn.";
                        NotificationsParent notification = new NotificationsParent();
                        notification.setParent(student.getParent());
                        notification.setTitle(tittle);
                        notification.setContent(content);
                        notification.setCreateAt(LocalDateTime.now());
                        notification.setStatus(false);
                        notificationsParentRepository.save(notification);
                        try {
                            // Gửi email với thông tin người dùng và thời gian
                            emailService.sendHtmlNotificationEmailForHealthCheck(student.getParent(), tittle, content, notification.getNotificationId());
                            // emailService.testEmailConfig("ytruongtieuhoc@example.com");
                        } catch (Exception e) {
                            throw new RuntimeException("Lỗi khi gửi email thông báo: " + e.getMessage(), e);
                        }
                    }
                }
            }

            // Lưu tất cả cùng một lúc
            if (!consentForms.isEmpty()) {
                healthConsentFormRepository.saveAll(consentForms);
            }

            // Thông báo về các lớp bị bỏ qua
            if (!skippedClasses.isEmpty()) {
                String message = "Các lớp sau đã có form cho lịch khám này và được bỏ qua: " + String.join(", ", skippedClasses);
                throw new RuntimeException(message);
            }

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo consent forms cho nhiều lớp: " + e.getMessage());
        }
    }


    public List<HealthConsentForm> getConsentFormsByClass(String className, int scheduleId) {
        return healthConsentFormRepository.findByClassNameAndHealthScheduleID(className, scheduleId);
    }

    public List<HealthConsentForm> getConsentFormsByClassAndSchedule(String className, Integer scheduleId) {
        return healthConsentFormRepository.findByClassNameAndHealthScheduleID(className, scheduleId);
    }

    public List<HealthConsentForm> getAcceptedConsentForms(Integer scheduleId) {
        return healthConsentFormRepository.findByHealthScheduleIDAndIsAgreed(scheduleId, "true");
    }

    public List<HealthConsentForm> getRejectedConsentForms(Integer scheduleId) {
        return healthConsentFormRepository.findByHealthScheduleIDAndIsAgreed(scheduleId, "false");
    }


}
