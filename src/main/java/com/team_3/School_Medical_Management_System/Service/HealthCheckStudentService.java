package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.HealthCheck_StudentDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HealthCheckStudentService {

    @Autowired
    private HealthCheckStudentRepository healthCheckStudentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private HealthCheckScheduleRepository healthCheckScheduleRepository;

    @Autowired
    private HealthConsultationRepository healthConsultationRepository;

    @Autowired
    private NotificationsParentRepository notificationsParentRepository;

    // Record health check results for a student
    public HealthCheck_Student recordHealthCheckResults(HealthCheck_StudentDTO dto) {
        HealthCheck_Student healthCheckStudent = new HealthCheck_Student();
        healthCheckStudent.setStudentID(dto.getStudentID());
        healthCheckStudent.setHeight(dto.getHeight());
        healthCheckStudent.setWeight(dto.getWeight());
        healthCheckStudent.setVisionLeft(dto.getVisionLeft());
        healthCheckStudent.setVisionRight(dto.getVisionRight());
        healthCheckStudent.setHearing(dto.getHearing());
        healthCheckStudent.setDentalCheck(dto.getDentalCheck());
        healthCheckStudent.setTemperature(dto.getTemperature());

        // Calculate BMI: weight / (height/100)²
        float heightInMeters = dto.getHeight() / 100f;
        float bmi = dto.getWeight() / (heightInMeters * heightInMeters);
        healthCheckStudent.setBmi(bmi);

        // Link to health check schedule
        Optional<HealthCheck_Schedule> schedule = healthCheckScheduleRepository.findById(dto.getCheckID());
        if (schedule.isPresent()) {
            healthCheckStudent.setHealthCheckSchedule(schedule.get());
        }

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

        // Check temperature (above 37.5°C)
        if (healthCheckResult.getTemperature() > 37.5) {
            hasAbnormalResults = true;
            issues.append("Nhiệt độ cao (").append(healthCheckResult.getTemperature()).append("°C). ");
        }

        // Create consultation appointment if abnormal results detected
        if (hasAbnormalResults) {
            Optional<Student> student = studentRepository.findById(healthCheckResult.getStudentID());
            if (student.isPresent()) {
                HealthConsultation consultation = new HealthConsultation();
                consultation.setStudent(student.get());
                consultation.setHealthCheckStudent(healthCheckResult);
                consultation.setIssue(issues.toString());
                consultation.setRecommendation("Cần tư vấn y tế về các vấn đề phát hiện");
                // Schedule consultation for 1 week after health check
                Date scheduledDate = new Date();
                scheduledDate.setTime(scheduledDate.getTime() + 7 * 24 * 60 * 60 * 1000);
                consultation.setScheduledDate(scheduledDate);
                consultation.setStatus(false); // Pending consultation

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
        Optional<Student> student = studentRepository.findById(healthCheckResult.getStudentID());

        if (student.isPresent() && student.get().getParent() != null) {
            NotificationsParent notification = new NotificationsParent();
            notification.setParent(student.get().getParent());
            notification.setTitle("Kết quả kiểm tra sức khỏe");

            StringBuilder content = new StringBuilder();
            content.append("Kết quả kiểm tra sức khỏe của ")
                    .append(student.get().getFullName())
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
                    .append(":\n")
                    .append("- Vấn đề: ").append(consultation.getIssue()).append("\n")
                    .append("- Thời gian hẹn tư vấn: ").append(consultation.getScheduledDate());

            notification.setContent(content.toString());
            notification.setCreateAt(LocalDateTime.now());
            notification.setStatus(false);
            notificationsParentRepository.save(notification);
        }
    }

    // Get health check results for a student
    public List<HealthCheck_Student> getHealthCheckResultsByStudent(int studentId) {
        return healthCheckStudentRepository.findByStudentID(studentId);
    }

    // Get health check results for a schedule
    public List<HealthCheck_Student> getHealthCheckResultsBySchedule(int scheduleId) {
        Optional<HealthCheck_Schedule> schedule = healthCheckScheduleRepository.findById(scheduleId);

        if (schedule.isPresent()) {
            return healthCheckStudentRepository.findByHealthCheckSchedule(schedule.get());
        }

        return List.of();
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
