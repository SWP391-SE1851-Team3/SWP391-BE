package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.HealthConsultationDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.HealthConsultationRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.NotificationsParentRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.StudentInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.StudentRepository;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HealthConsultationService {

    @Autowired
    private HealthConsultationRepository healthConsultationRepository;

    @Autowired
    private StudentInterFace studentRepository;

    @Autowired
    private NotificationsParentRepository notificationsParentRepository;

    // Get all consultations
    public List<HealthConsultation> getAllConsultations() {
        return healthConsultationRepository.findAll();
    }

    // Get consultations by status (pending/completed)
    public List<HealthConsultation> getConsultationsByStatus(boolean status) {
        return healthConsultationRepository.findByStatus(status);
    }

    // Get consultations for a specific student
    public List<HealthConsultation> getConsultationsByStudent(int studentId) {
        Optional<Student> student = studentRepository.findById(studentId);

        if (student.isPresent()) {
            return healthConsultationRepository.findByStudent(student.get());
        }

        return List.of();
    }

    // Update consultation status
    public HealthConsultation updateConsultationStatus(int consultationId, boolean status, String notes) {
        Optional<HealthConsultation> optionalConsultation = healthConsultationRepository.findById(consultationId);

        if (optionalConsultation.isPresent()) {
            HealthConsultation consultation = optionalConsultation.get();
            consultation.setStatus(status);

            if (notes != null && !notes.isEmpty()) {
                consultation.setNotes(notes);
            }

            HealthConsultation updatedConsultation = healthConsultationRepository.save(consultation);

            // If consultation is completed, notify the parent
            if (status) {
                notifyParentAboutCompletedConsultation(updatedConsultation);
            }

            return updatedConsultation;
        }

        return null;
    }

    // Notify parent about completed consultation
    private void notifyParentAboutCompletedConsultation(HealthConsultation consultation) {
        if (consultation.getStudent().getParent() != null) {
            NotificationsParent notification = new NotificationsParent();
            notification.setParent(consultation.getStudent().getParent());
            notification.setTitle("Tư vấn y tế đã hoàn thành");

            StringBuilder content = new StringBuilder();
            content.append("Buổi tư vấn y tế cho ")
                    .append(consultation.getStudent().getFullName())
                    .append(" đã hoàn thành.\n")
                    .append("- Vấn đề: ").append(consultation.getIssue()).append("\n");

            if (consultation.getRecommendation() != null && !consultation.getRecommendation().isEmpty()) {
                content.append("- Khuyến nghị: ").append(consultation.getRecommendation()).append("\n");
            }

            if (consultation.getNotes() != null && !consultation.getNotes().isEmpty()) {
                content.append("- Ghi chú: ").append(consultation.getNotes());
            }

            notification.setContent(content.toString());
            notification.setCreateAt(LocalDateTime.now());
            notification.setStatus(false);


            notificationsParentRepository.save(notification);
        }
    }

    // Convert entity to DTO
    public HealthConsultationDTO convertToDTO(HealthConsultation consultation) {
        HealthConsultationDTO dto = new HealthConsultationDTO();
        dto.setConsultationID(consultation.getConsultationID());
        dto.setStudentID(consultation.getStudent().getStudentID());
        dto.setStudentName(consultation.getStudent().getFullName());

        if (consultation.getHealthCheckStudent() != null) {
            dto.setCheckID(consultation.getHealthCheckStudent().getCheckID());
        }

        dto.setIssue(consultation.getIssue());
        dto.setRecommendation(consultation.getRecommendation());
        dto.setScheduledDate(consultation.getScheduledDate());
        dto.setStatus(consultation.isStatus());
        dto.setNotes(consultation.getNotes());

        return dto;
    }
}
