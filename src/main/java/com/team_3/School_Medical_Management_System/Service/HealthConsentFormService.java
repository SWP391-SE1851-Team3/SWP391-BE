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
    @Autowired
    private SchoolNurseRepository schoolNurseRepository;


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

    // Send notification to parent about health check consent form
    private void sendConsentFormNotification(Student student, HealthCheck_Schedule schedule) {
        if (student.getParent() != null) {
            String title = "Yêu cầu đồng ý kiểm tra sức khỏe cho học sinh " + student.getFullName();
            String content = "Một đợt khám sức khỏe mới '" + schedule.getName() +
                    "' đã được lên lịch cho học sinh " + student.getFullName() +
                    ". Vui lòng xem và xác nhận đồng ý cho con em của bạn.";

            NotificationsParent notification = new NotificationsParent();
            notification.setParent(student.getParent());
            notification.setTitle(title);
            notification.setContent(content);
            notification.setCreateAt(LocalDateTime.now());
            notification.setStatus(false);
            notificationsParentRepository.save(notification);

            try {
                // Gửi email với thông tin người dùng và thời gian
                emailService.sendHtmlNotificationEmailForHealthCheck(
                    student.getParent(),
                    title,
                    content,
                    notification.getNotificationId()
                );
            } catch (Exception e) {
                throw new RuntimeException("Lỗi khi gửi email thông báo: " + e.getMessage(), e);
            }
        }
    }

    public void createConsentFormsForMultipleClasses(ConsentFormRequestDTO request) {
        List<HealthConsentForm> consentForms = new ArrayList<>();
        List<String> skippedClasses = new ArrayList<>();
        List<String> invalidClasses = new ArrayList<>();


        HealthCheck_Schedule schedule = healthCheckScheduleRepository.findById(request.getHealthScheduleId())
                .orElseThrow(() -> new RuntimeException("HealthCheck_Schedule not found with id: " + request.getHealthScheduleId()));

        // Validate nurseID if provided
        if (request.getCreatedByNurseId() != null &&
                !schoolNurseRepository.existsById(request.getCreatedByNurseId())) {
            throw new RuntimeException("Nurse with ID " + request.getCreatedByNurseId() + " does not exist");
        }

        if (request.getUpdatedByNurseID() != null &&
                !schoolNurseRepository.existsById(request.getUpdatedByNurseID())) {
            throw new RuntimeException("Nurse with ID " + request.getUpdatedByNurseID() + " does not exist");
        }

        // Lặp qua danh sách các lớp
        for (String className : request.getClassName()) {
            // Lấy danh sách học sinh theo tên lớp
            List<Student> students = studentRepository.findByClassName(className);


            // Kiểm tra xem lớp có tồn tại không (có học sinh không)
            if (students.isEmpty()) {
                invalidClasses.add(className);
                continue;
            }

            // Kiểm tra xem lớp này đã có form cho schedule này chưa
            List<HealthConsentForm> existingForms = healthConsentFormRepository.findByClassNameAndHealthScheduleID(className, request.getHealthScheduleId());

            if (!existingForms.isEmpty()) {
                // Nếu đã có form cho lớp này với schedule này, bỏ qua
                skippedClasses.add(className);
                continue;
            }

            // Tạo consent form cho từng học sinh
            for (Student student : students) {
                // Create HealthConsentForm
                HealthConsentForm consentForm = new HealthConsentForm();
                consentForm.setStudentID(student.getStudentID());
                consentForm.setParentID(student.getParent().getParentID());
                consentForm.setHealthScheduleID(schedule.getHealth_ScheduleID());
                consentForm.setSend_date(new Date());
                consentForm.setExpire_date(request.getExpireDate());
                consentForm.setIsAgreed("Chờ phản hồi");
                consentForm.setNotes(request.getNotes());

                // Chỉ set nurseID nếu hợp lệ, nếu không để null
                consentForm.setCreatedByNurseID(request.getCreatedByNurseId());
                consentForm.setUpdatedByNurseID(request.getUpdatedByNurseID());

                // Thêm consentForm vào list
                consentForms.add(consentForm);

                // Send notification and email to parent
                sendConsentFormNotification(student, schedule);
            }
        }

        // Lưu tất cả cùng một lúc
        if (!consentForms.isEmpty()) {
            healthConsentFormRepository.saveAll(consentForms);
        }

        // Tạo thông báo lỗi tổng hợp
        List<String> errorMessages = new ArrayList<>();

        if (!invalidClasses.isEmpty()) {
            errorMessages.add("Lớp " + String.join(", ", invalidClasses) + " không tồn tại hoặc không có học sinh nào");
        }

        if (!skippedClasses.isEmpty()) {
            String scheduleName = schedule.getName();
            errorMessages.add("Lớp " + String.join(", ", skippedClasses) + " đã tồn tại phiếu xác nhận của đợt " + scheduleName);
        }

        // Nếu có lỗi, throw exception
        if (!errorMessages.isEmpty()) {
            throw new RuntimeException(String.join(". ", errorMessages));
        }

        // Nếu không có consent form nào được tạo
        if (consentForms.isEmpty()) {
            throw new RuntimeException("Không có phiếu đồng ý nào được tạo. Vui lòng kiểm tra lại thông tin các lớp.");
        }
    }


    public List<HealthConsentForm> getConsentFormsByClass(String className, int scheduleId) {
        return healthConsentFormRepository.findByClassNameAndHealthScheduleID(className, scheduleId);
    }

    public List<HealthConsentForm> getConsentFormsByClassAndSchedule(String className, Integer scheduleId) {
        return healthConsentFormRepository.findByClassNameAndHealthScheduleID(className, scheduleId);
    }

    public List<HealthConsentForm> getAcceptedConsentForms(Integer scheduleId) {
        return healthConsentFormRepository.findByHealthScheduleIDAndIsAgreed(scheduleId, "Đồng ý");
    }

    public List<HealthConsentForm> getRejectedConsentForms(Integer scheduleId) {
        return healthConsentFormRepository.findByHealthScheduleIDAndIsAgreed(scheduleId, "Từ chối");
    }


}
