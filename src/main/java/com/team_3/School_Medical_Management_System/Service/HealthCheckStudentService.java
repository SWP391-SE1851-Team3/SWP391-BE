package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.HealthCheck_StudentCreateDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthCheck_StudentDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthCheckStudentSimplifiedDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HealthCheckStudentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HealthCheckStudentRepository healthCheckStudentRepository;

    @Autowired
    private HealthConsentFormRepository healthConsentFormRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private HealthCheckScheduleRepository healthCheckScheduleRepository;

    @Autowired
    private HealthConsultationRepository healthConsultationRepository;

    @Autowired
    private NotificationsParentRepository notificationsParentRepository;

    @Autowired
    private SchoolNurseService schoolNurseService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HealthConsultationService healthConsultationService;

    // Create health check results using DTO with CheckID
    @Transactional
    public HealthCheck_Student createHealthCheckResults(HealthCheck_StudentDTO dto) {
        // Check HealthConsentForm by formID
        Optional<HealthConsentForm> consentOpt = healthConsentFormRepository.findById(dto.getFormID());
        if (!consentOpt.isPresent()) {
            throw new RuntimeException("HealthConsentForm not found with formID: " + dto.getFormID());
        }
        HealthConsentForm consentForm = consentOpt.get();
        if (!"đồng ý".equalsIgnoreCase(consentForm.getIsAgreed())) {
            throw new RuntimeException("Cannot create HealthCheck_Student: Consent form is not accepted.");
        }
        // Use studentID from consent form
        int studentId = consentForm.getStudentID();
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (!studentOpt.isPresent()) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }
        Student student = studentOpt.get();
        // Create HealthCheck_Student
        HealthCheck_Student healthCheckStudent = new HealthCheck_Student();
        healthCheckStudent.setStudentID(studentId);
        healthCheckStudent.setFormID(dto.getFormID());
        healthCheckStudent.setHealth_ScheduleID(dto.getHealth_ScheduleID());
        healthCheckStudent.setStatus("Chờ ghi nhận"); // Thêm set status
        // Set other properties, allow empty/null if not provided
        healthCheckStudent.setHeight(dto.getHeight());
        healthCheckStudent.setWeight(dto.getWeight());
        healthCheckStudent.setVisionLeft(dto.getVisionLeft());
        healthCheckStudent.setVisionRight(dto.getVisionRight());
        healthCheckStudent.setHearing(dto.getHearing());
        healthCheckStudent.setDentalCheck(dto.getDentalCheck());
        healthCheckStudent.setTemperature(dto.getTemperature());
        healthCheckStudent.setOverallResult(dto.getOverallResult());
        // Calculate BMI only if height and weight are valid
        float bmi = 0;
        if (dto.getHeight() > 0 && dto.getWeight() > 0) {
            float heightInMeters = dto.getHeight() / 100f;
            bmi = dto.getWeight() / (heightInMeters * heightInMeters);
        }
        healthCheckStudent.setBmi(bmi);
        // Set creation information
        healthCheckStudent.setCreate_at(new Date());
        healthCheckStudent.setCreatedByNurseID(dto.getCreatedByNurseID());
        healthCheckStudent.setUpdate_at(new Date());
        healthCheckStudent.setUpdatedByNurseID(dto.getUpdatedByNurseID());
        // Save HealthCheck_Student
        HealthCheck_Student savedResult = healthCheckStudentRepository.save(healthCheckStudent);
        // Check for abnormal results and create consultations if needed
        checkForAbnormalResults(savedResult); // Truyền object thay vì string
        return savedResult;
    }

    // Check for abnormal health check results and create consultation appointments
    private void checkForAbnormalResults(HealthCheck_Student healthCheckResult) {
        if (healthCheckResult == null || healthCheckResult.getStatus() == null) {
            return;
        }

        String status = healthCheckResult.getStatus().trim();
        if (status.equalsIgnoreCase("Cần tư vấn y tế")) {
            try {
                HealthConsultation consultation = new HealthConsultation();
                consultation.setStudentID(healthCheckResult.getStudentID());
                consultation.setCheckID(healthCheckResult.getCheckID());
                consultation.setStatus("Cần tư vấn y tế");
                consultation.setReason("Cần tư vấn y tế theo đánh giá của y tá");
                consultation.setCreate_at(new Date());
                consultation.setCreatedByNurseID(healthCheckResult.getCreatedByNurseID());

                // Save consultation và gửi thông báo mời tư vấn
                HealthConsultation savedConsultation = healthConsultationService.save(consultation);

                // Gửi thông báo mời tư vấn cho phụ huynh
                healthConsultationService.notifyParentAboutConsultationInvitation(savedConsultation);

            } catch (Exception e) {
                // Log error but don't break the flow
                System.err.println("Error creating consultation: " + e.getMessage());
            }
        }
    }


    // Send notification to parent about health check results
    private void sendHealthCheckResultNotification(HealthCheck_Student healthCheckResult) {
        // Get student by studentID from healthCheckResult
        Optional<Student> studentOpt = studentRepository.findById(healthCheckResult.getStudentID());
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            if (student.getParent() != null) {
                NotificationsParent notification = new NotificationsParent();
                notification.setParent(student.getParent());
                notification.setTitle("Kết quả kiểm tra sức khỏe");

                String tittle = "Kết quả kiểm tra sức khỏe của " + student.getFullName();
                StringBuilder content = new StringBuilder();
                content.append("Kết quả kiểm tra sức khỏe của ")
                        .append(student.getFullName())
                        .append(":\n")
                        .append("- Chiều cao: ").append(healthCheckResult.getHeight()).append(" cm\n")
                        .append("- Cân nặng: ").append(healthCheckResult.getWeight()).append(" kg\n")
                        .append("- BMI: ").append(String.format("%.1f", healthCheckResult.getBmi())).append("\n")
                        .append("- Thị lực (trái/phải): ").append(healthCheckResult.getVisionLeft()).append("/").append(healthCheckResult.getVisionRight()).append("\n")
                        .append("- Thính lực: ").append(healthCheckResult.getHearing()).append("\n")
                        .append("- Kiểm tra răng miệng: ").append(healthCheckResult.getDentalCheck()).append("\n")
                        .append("- Nhiệt độ: ").append(healthCheckResult.getTemperature()).append("°C");

                // Add overall result if available
                if (healthCheckResult.getOverallResult() != null && !healthCheckResult.getOverallResult().isEmpty()) {
                    content.append("\n- Kết quả tổng quan: ").append(healthCheckResult.getOverallResult());
                }

                notification.setContent(content.toString());
                notification.setCreateAt(LocalDateTime.now());
                notification.setStatus(false);
                notificationsParentRepository.save(notification);
                try {
                    // Gửi email với thông tin người dùng và thời gian
                    emailService.sendHtmlNotificationEmailForHealthCheckStudent(student.getParent(), tittle, content.toString(), notification.getNotificationId());
                    // emailService.testEmailConfig("ytruongtieuhoc@example.com");
                } catch (Exception e) {
                    throw new RuntimeException("Lỗi khi gửi email thông báo: " + e.getMessage(), e);
                }
            }
        }
    }


    // Get health check results for a student
    public List<HealthCheck_Student> getHealthCheckResultsByStudent(int studentId) {
        return healthCheckStudentRepository.findByStudent_StudentID(studentId);
    }


    // Update health check results
    public HealthCheck_Student updateHealthCheckResults(int id, HealthCheck_StudentDTO dto) {
        Optional<HealthCheck_Student> optionalResult = healthCheckStudentRepository.findById(id);

        if (optionalResult.isPresent()) {
            HealthCheck_Student existingResult = optionalResult.get();

            // Update fields
            existingResult.setStatus(dto.getStatus()); // Thêm update status
            existingResult.setHeight(dto.getHeight());
            existingResult.setWeight(dto.getWeight());
            existingResult.setVisionLeft(dto.getVisionLeft());
            existingResult.setVisionRight(dto.getVisionRight());
            existingResult.setHearing(dto.getHearing());
            existingResult.setDentalCheck(dto.getDentalCheck());
            existingResult.setTemperature(dto.getTemperature());
            existingResult.setOverallResult(dto.getOverallResult());

            // Recalculate BMI
            float heightInMeters = dto.getHeight() / 100f;
            float bmi = dto.getWeight() / (heightInMeters * heightInMeters);
            existingResult.setBmi(bmi);

            // Set update information
            if (existingResult.getCreatedByNurseID() == null) {
                existingResult.setCreatedByNurseID(dto.getCreatedByNurseID());
                existingResult.setCreate_at(new Date());
            } else {
                existingResult.setUpdatedByNurseID(dto.getUpdatedByNurseID());
                existingResult.setUpdate_at(new Date());
            }
            // Save updated result
            HealthCheck_Student updatedResult = healthCheckStudentRepository.save(existingResult);
            // Check for abnormal results again
            checkForAbnormalResults(updatedResult); // Truyền object thay vì string
            // Send updated notification
            if("Đã hoàn thành".equalsIgnoreCase(updatedResult.getStatus())) {
                sendHealthCheckResultNotification(updatedResult);
            }
            return updatedResult;
        }

        return null;
    }

    // Delete health check results
    public void deleteHealthCheckResults(int id) {
        healthCheckStudentRepository.deleteById(id);
    }

    public List<HealthCheck_Student> getAllHealthCheckResults() {
        return healthCheckStudentRepository.findAll();
    }

    // Get all health check results simplified
    public List<HealthCheckStudentSimplifiedDTO> getAllHealthCheckResultsSimplified() {
        return healthCheckStudentRepository.findAll().stream()
                .map(this::convertToSimplifiedDTO)
                .collect(Collectors.toList());
    }

    // Get simplified health check results for a student
    public List<HealthCheckStudentSimplifiedDTO> getSimplifiedHealthCheckResultsByStudent(int studentId) {
        return healthCheckStudentRepository.findByStudent_StudentID(studentId).stream()
                .map(this::convertToSimplifiedDTO)
                .collect(Collectors.toList());
    }

    public List<HealthCheckStudentSimplifiedDTO> getSimplifiedHealthCheckResultsBySchedule(int health_ScheduleID) {
        return healthCheckStudentRepository.findByHealth_ScheduleID(health_ScheduleID).stream()
                .map(this::convertToSimplifiedDTO)
                .collect(Collectors.toList());
    }

    // Public method to convert entity to simplified DTO (for controller use)
    public HealthCheckStudentSimplifiedDTO convertEntityToSimplifiedDTO(HealthCheck_Student entity) {
        return convertToSimplifiedDTO(entity);
    }

    // Helper method to convert HealthCheck_Student entity to simplified DTO
    private HealthCheckStudentSimplifiedDTO convertToSimplifiedDTO(HealthCheck_Student entity) {
        HealthCheckStudentSimplifiedDTO dto = new HealthCheckStudentSimplifiedDTO();

        // Basic health check info
        dto.setCheckID(entity.getCheckID());
        dto.setStudentID(entity.getStudentID());
        dto.setStatus(entity.getStatus()); // Thêm set status
        dto.setHeight(entity.getHeight());
        dto.setWeight(entity.getWeight());
        dto.setVisionLeft(entity.getVisionLeft());
        dto.setVisionRight(entity.getVisionRight());
        dto.setHearing(entity.getHearing());
        dto.setDentalCheck(entity.getDentalCheck());
        dto.setTemperature(entity.getTemperature());
        dto.setBmi(entity.getBmi());
        dto.setOverallResult(entity.getOverallResult());
        dto.setCreate_at(entity.getCreate_at());
        dto.setUpdate_at(entity.getUpdate_at());
        dto.setCreatedByNurseID(entity.getCreatedByNurseID());
        dto.setUpdatedByNurseID(entity.getUpdatedByNurseID());

        // Set nurse names - fetch from database using nurse IDs
        String createdByNurseName = null;
        if (entity.getCreatedByNurseID() != null) {
            SchoolNurse createdByNurse = schoolNurseService.GetSchoolNursesById(entity.getCreatedByNurseID());
            if (createdByNurse != null) {
                createdByNurseName = createdByNurse.getFullName();
            }
        }
        dto.setCreatedByNurseName(createdByNurseName);

        String updatedByNurseName = null;
        if (entity.getUpdatedByNurseID() != null) {
            SchoolNurse updatedByNurse = schoolNurseService.GetSchoolNursesById(entity.getUpdatedByNurseID());
            if (updatedByNurse != null) {
                updatedByNurseName = updatedByNurse.getFullName();
            }
        }
        dto.setUpdatedByNurseName(updatedByNurseName);

        // Set student info (flat fields) - fetch from database using studentID
        Optional<Student> studentOpt = studentRepository.findById(entity.getStudentID());
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            dto.setFullName(student.getFullName());
            dto.setClassName(student.getClassName());
        }

        return dto;
    }

    // Get health check result by ID
    public HealthCheck_Student getHealthCheckStudentById(int id) {
        Optional<HealthCheck_Student> result = healthCheckStudentRepository.findById(id);
        return result.orElse(null);
    }
}
