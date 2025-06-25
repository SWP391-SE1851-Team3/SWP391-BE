package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.HealthCheck_StudentCreateDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthCheck_StudentDTO;
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

@Service
public class HealthCheckStudentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HealthCheckStudentRepository healthCheckStudentRepository;

    @Autowired
    private HealthCheckRepository healthCheckRepository;

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

    // Create health check results using DTO without CheckID
    @Transactional
    public HealthCheck_Student createHealthCheckResults(HealthCheck_StudentCreateDTO dto) {
        // Fetch the Student object first
        Optional<Student> studentOpt = studentRepository.findById(dto.getStudentID());
        if (!studentOpt.isPresent()) {
            throw new RuntimeException("Student not found with ID: " + dto.getStudentID());
        }
        Student student = studentOpt.get();

        // Find all consent forms for this student
        List<HealthConsentForm> consentForms = healthConsentFormRepository.findByStudent(student);
        if (consentForms.isEmpty()) {
            throw new RuntimeException("No health consent form found for student ID: " + dto.getStudentID());
        }

        // Find the consent form with the highest health_ScheduleID
        HealthConsentForm consentForm = consentForms.stream()
                .sorted((form1, form2) -> {
                    // Compare by health_ScheduleID in descending order (highest first)
                    if (form1.getHealthCheckSchedule() != null && form2.getHealthCheckSchedule() != null) {
                        return Integer.compare(
                            form2.getHealthCheckSchedule().getHealth_ScheduleID(),
                            form1.getHealthCheckSchedule().getHealth_ScheduleID()
                        );
                    } else if (form1.getHealthCheckSchedule() != null) {
                        return -1; // form1 has schedule, form2 doesn't
                    } else if (form2.getHealthCheckSchedule() != null) {
                        return 1;  // form2 has schedule, form1 doesn't
                    } else {
                        return 0;  // neither has schedule
                    }
                })
                .findFirst()
                .orElse(consentForms.get(0)); // Fallback to first one if sorting fails

        // Check if a HealthCheck record already exists for this formID
        HealthCheck healthCheck = healthCheckRepository.findByFormID(consentForm.getFormID());

        if (healthCheck == null) {
            // Create a new HealthCheck record only if it doesn't exist
            healthCheck = new HealthCheck();
            healthCheck.setFormID(consentForm.getFormID());
            // Save to generate the CheckID
            healthCheck = healthCheckRepository.save(healthCheck);
        }

        // Check if a HealthCheck_Student record already exists with this checkID
        Optional<HealthCheck_Student> existingHealthCheckStudent =
            healthCheckStudentRepository.findById(healthCheck.getCheckID());

        if (existingHealthCheckStudent.isPresent()) {
            throw new RuntimeException("A health check record already exists for this form ID: " +
                consentForm.getFormID() + " with checkID: " + healthCheck.getCheckID());
        }

        // Create HealthCheck_Student with the checkID from HealthCheck
        HealthCheck_Student healthCheckStudent = new HealthCheck_Student();
        healthCheckStudent.setCheckID(healthCheck.getCheckID());
        healthCheckStudent.setStudent(student);

        // Set other properties
        healthCheckStudent.setHeight(dto.getHeight());
        healthCheckStudent.setWeight(dto.getWeight());
        healthCheckStudent.setVisionLeft(dto.getVisionLeft());
        healthCheckStudent.setVisionRight(dto.getVisionRight());
        healthCheckStudent.setHearing(dto.getHearing());
        healthCheckStudent.setDentalCheck(dto.getDentalCheck());
        healthCheckStudent.setTemperature(dto.getTemperature());

        // Calculate BMI
        float heightInMeters = dto.getHeight() / 100f;
        float bmi = dto.getWeight() / (heightInMeters * heightInMeters);
        healthCheckStudent.setBmi(bmi);

        // Save HealthCheck_Student
        HealthCheck_Student savedResult = healthCheckStudentRepository.save(healthCheckStudent);

        // Check for abnormal results and create consultations if needed
        checkForAbnormalResults(savedResult);

        // Send notification to parent about health check results
        sendHealthCheckResultNotification(savedResult);

        return savedResult;
    }

    // Check for abnormal health check results and create consultation appointments
    private void checkForAbnormalResults(HealthCheck_Student healthCheckResult) {
        boolean hasAbnormalResults = false;
        StringBuilder issues = new StringBuilder();

        // Check vision (less than 8/10)
        float visionLeftValue = parseVisionValue(healthCheckResult.getVisionLeft());
        float visionRightValue = parseVisionValue(healthCheckResult.getVisionRight());

        if (visionLeftValue < 0.8 || visionRightValue < 0.8) {
            hasAbnormalResults = true;
            issues.append("Thị lực dưới 8/10. ");
        }

        // Check dental issues
        if (healthCheckResult.getDentalCheck() != null &&
                !healthCheckResult.getDentalCheck().equalsIgnoreCase("Normal") &&
                !healthCheckResult.getDentalCheck().equalsIgnoreCase("Bình thường")) {
            hasAbnormalResults = true;
            issues.append("Vấn đề răng miệng: ").append(healthCheckResult.getDentalCheck()).append(". ");
        }

        // Check BMI (outside normal range 18.5-24.9)
        if (healthCheckResult.getBmi() < 18.5 || healthCheckResult.getBmi() > 24.9) {
            hasAbnormalResults = true;
            if (healthCheckResult.getBmi() < 18.5) {
                issues.append("BMI thấp (").append(String.format("%.1f", healthCheckResult.getBmi())).append(") - Thiếu cân. ");
            } else {
                issues.append("BMI cao (").append(String.format("%.1f", healthCheckResult.getBmi())).append(") - Thừa cân. ");
            }
        }


        // Create consultation appointment if abnormal results detected
        if (hasAbnormalResults) {
            Student student = healthCheckResult.getStudent();
            if (student != null) {
                HealthConsultation consultation = new HealthConsultation();
                consultation.setStudent(student);
                consultation.setHealthCheckStudent(healthCheckResult);
                // Schedule consultation for 1 week after health check
                Date scheduledDate = new Date();
                scheduledDate.setTime(scheduledDate.getTime() + 7 * 24 * 60 * 60 * 1000);
                consultation.setStatus("pending"); // Changed from boolean false to String "pending"

                HealthConsultation savedConsultation = healthConsultationRepository.save(consultation);

                // Send notification to parent about consultation appointment
                sendConsultationNotification(savedConsultation);
            }
        }
    }

    // Parse vision value from string format (e.g., "8/10" -> 0.8)
    private float parseVisionValue(String visionStr) {
        if (visionStr == null || visionStr.isEmpty()) {
            return 0;
        }

        try {
            if (visionStr.contains("/")) {
                String[] parts = visionStr.split("/");
                return Float.parseFloat(parts[0]) / Float.parseFloat(parts[1]);
            } else {
                return Float.parseFloat(visionStr);
            }
        } catch (Exception e) {
            return 0;
        }
    }

    // Send notification to parent about health check results
    private void sendHealthCheckResultNotification(HealthCheck_Student healthCheckResult) {
        Student student = healthCheckResult.getStudent();
        if (student != null && student.getParent() != null) {
            NotificationsParent notification = new NotificationsParent();
            notification.setParent(student.getParent());
            notification.setTitle("Kết quả kiểm tra sức khỏe");

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

            notification.setContent(content.toString());
            notification.setCreateAt(LocalDateTime.now());
            notification.setStatus(false);
            notificationsParentRepository.save(notification);
        }
    }

    // Send notification to parent about consultation appointment
    private void sendConsultationNotification(HealthConsultation consultation) {
        if (consultation.getStudent().getParent() != null) {
            NotificationsParent notification = new NotificationsParent();
            notification.setParent(consultation.getStudent().getParent());
            notification.setTitle("Lịch hẹn tư vấn y tế");

            StringBuilder content = new StringBuilder();
            content.append("Phát hiện vấn đề sức khỏe cần tư vấn cho ")
                    .append(consultation.getStudent().getFullName())
                    .append(":\n");

            notification.setContent(content.toString());
            notification.setCreateAt(LocalDateTime.now());
            notification.setStatus(false);
            notificationsParentRepository.save(notification);
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
            existingResult.setHeight(dto.getHeight());
            existingResult.setWeight(dto.getWeight());
            existingResult.setVisionLeft(dto.getVisionLeft());
            existingResult.setVisionRight(dto.getVisionRight());
            existingResult.setHearing(dto.getHearing());
            existingResult.setDentalCheck(dto.getDentalCheck());
            existingResult.setTemperature(dto.getTemperature());

            // Recalculate BMI
            float heightInMeters = dto.getHeight() / 100f;
            float bmi = dto.getWeight() / (heightInMeters * heightInMeters);
            existingResult.setBmi(bmi);

            // Save updated result
            HealthCheck_Student updatedResult = healthCheckStudentRepository.save(existingResult);

            // Check for abnormal results again
            checkForAbnormalResults(updatedResult);

            // Send updated notification
            sendHealthCheckResultNotification(updatedResult);

            return updatedResult;
        }

        return null;
    }

    // Delete health check results
    public void deleteHealthCheckResults(int id) {
        healthCheckStudentRepository.deleteById(id);
    }
}
