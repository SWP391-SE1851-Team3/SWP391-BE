package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.HealthConsultationDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthConsultationUpdateDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HealthConsultationService {

    @Autowired
    private HealthConsultationRepository healthConsultationRepository;

    @Autowired
    private StudentInterFace studentRepository;

    @Autowired
    private NotificationsParentRepository notificationsParentRepository;

    @Autowired
    private SchoolNurseService schoolNurseService;

    @Autowired
    private SchoolNurseInterFace schoolNurseRepository;

    @Autowired
    private EmailService emailService;

    // Helper method to get nurse name by ID
    public String getNurseNameById(Integer nurseId) {
        if (nurseId == null) {
            return null;
        }
        try {
            SchoolNurse nurse = schoolNurseRepository.GetSchoolNursesById(nurseId);
            return nurse != null ? nurse.getFullName() : null;
        } catch (Exception e) {
            return null;
        }
    }

    // Get all consultations
    public List<HealthConsultation> getAllConsultations() {
        return healthConsultationRepository.findAll();
    }

    // Get consultations by status (pending/completed)
    public List<HealthConsultation> getConsultationsByStatus(String status) {
        return healthConsultationRepository.findByStatus(status);
    }

    // Get consultations for a specific student
    public List<HealthConsultation> getConsultationsByStudent(int studentId) {
        return healthConsultationRepository.findByStudentID(studentId);
    }

    // Update consultation status
    public HealthConsultation updateConsultationStatus(int consultationId, String status, String notes, String location, Date consultDate, Integer updatedByNurseID) {
        Optional<HealthConsultation> optionalConsultation = healthConsultationRepository.findById(consultationId);

        if (optionalConsultation.isPresent()) {
            HealthConsultation consultation = optionalConsultation.get();
            consultation.setStatus(status);
            if (notes != null && !notes.isEmpty()) {
                consultation.setReason(notes);
            }
            if (location != null && !location.isEmpty()) {
                consultation.setLocation(location);
            }
            consultation.setConsultDate(consultDate);
            // Set update information
            consultation.setUpdate_at(new Date());
            consultation.setUpdatedByNurseID(updatedByNurseID);

            HealthConsultation updatedConsultation = healthConsultationRepository.save(consultation);
            if(status.equalsIgnoreCase("Đang chờ xử lý")) {
                // Notify parent about consultation completion
                notifyParentAboutConsultationInvitation(updatedConsultation);
            }
            return updatedConsultation;
        }

        return null;
    }

    // Notify parent about consultation invitation (make it public for external use)
    public void notifyParentAboutConsultationInvitation(HealthConsultation consultation) {
        // Get the student using studentID
        Optional<Student> studentOpt = studentRepository.findById(consultation.getStudentID());

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();

            if (student.getParent() != null) {
                NotificationsParent notification = new NotificationsParent();
                notification.setParent(student.getParent());
                notification.setTitle("Mời tư vấn y tế");
                String title = "Mời tư vấn y tế cho " + student.getFullName();
                String content = "Con em cần được tư vấn y tế vì lý do: " + consultation.getReason();
                if (consultation.getConsultDate() != null) {
                    content += "\nThời gian dự kiến: " + consultation.getConsultDate();
                }
                if (consultation.getLocation() != null && !consultation.getLocation().isEmpty()) {
                    content += "\nĐịa điểm: " + consultation.getLocation();
                }
                content += ".\n Vui lòng sắp xếp thời gian để tham gia buổi tư vấn với đội ngũ y tế của trường vào ngày này để đảm bảo sức khỏe của con em mình.";

                notification.setContent(content);
                notification.setCreateAt(LocalDateTime.now());
                notification.setStatus(false);

                notificationsParentRepository.save(notification);
                try {
                    // Gửi email mời tư vấn
                    emailService.sendHtmlNotificationEmailForHealthCheckConsultation(
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
    }

    // Convert entity to DTO with nurse names
    public HealthConsultationDTO convertToDTO(HealthConsultation consultation) {
        HealthConsultationDTO dto = new HealthConsultationDTO();
        dto.setConsultID(consultation.getConsultID());
        dto.setStudentID(consultation.getStudentID());

        // Get student information using studentID
        Optional<Student> studentOpt = studentRepository.findById(consultation.getStudentID());
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            dto.setStudentName(student.getFullName());
            dto.setClassName(student.getClassName());
        }
        dto.setConsultDate(consultation.getConsultDate());
        dto.setCheckID(consultation.getCheckID());
        dto.setStatus(consultation.getStatus());
        dto.setLocation(consultation.getLocation());
        dto.setReason(consultation.getReason());

        // Set nurse tracking fields
        dto.setCreatedByNurseID(consultation.getCreatedByNurseID());
        dto.setUpdatedByNurseID(consultation.getUpdatedByNurseID());
        dto.setCreate_at(consultation.getCreate_at());
        dto.setUpdate_at(consultation.getUpdate_at());

        // Get nurse names dynamically
        dto.setCreatedByNurseName(getNurseNameById(consultation.getCreatedByNurseID()));
        dto.setUpdatedByNurseName(getNurseNameById(consultation.getUpdatedByNurseID()));

        return dto;
    }

    public HealthConsultationUpdateDTO convertToUpdateDTO(HealthConsultation consultation) {
        HealthConsultationUpdateDTO dto = new HealthConsultationUpdateDTO();
        dto.setConsultID(consultation.getConsultID());
        dto.setStudentID(consultation.getStudentID());
        dto.setConsultDate(consultation.getConsultDate());
        Optional<Student> studentOpt = studentRepository.findById(consultation.getStudentID());
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            dto.setStudentName(student.getFullName());
        }
        dto.setCheckID(consultation.getCheckID());
        dto.setStatus(consultation.getStatus());
        dto.setLocation(consultation.getLocation());
        dto.setReason(consultation.getReason());
        dto.setUpdate_at(consultation.getUpdate_at());
        // Sử dụng getNurseNameById để lấy tên y tá từ ID
        dto.setUpdatedByNurseName(getNurseNameById(consultation.getUpdatedByNurseID()));
        dto.setUpdatedByNurseID(consultation.getUpdatedByNurseID());
        return dto;
    }
    // Create a new consultation
    public HealthConsultation createConsultation(HealthConsultationDTO dto) {
        Optional<Student> studentOpt = studentRepository.findById(dto.getStudentID());
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Student not found with ID: " + dto.getStudentID());
        }

        HealthConsultation consultation = new HealthConsultation();
        consultation.setStudentID(dto.getStudentID());
        consultation.setCheckID(dto.getCheckID());
        consultation.setStatus(dto.getStatus() != null ? dto.getStatus() : "pending");
        consultation.setReason(dto.getReason());
        consultation.setLocation(dto.getLocation());
        consultation.setConsultDate(dto.getConsultDate());
        // Set creation information
        consultation.setCreate_at(new Date());
        consultation.setCreatedByNurseID(dto.getCreatedByNurseID());

        // Also set update information if it's provided in the DTO
        if (dto.getUpdatedByNurseID() != null) {
            consultation.setUpdate_at(new Date());
            consultation.setUpdatedByNurseID(dto.getUpdatedByNurseID());
        }

        // Notify parent about the new consultation invitation
        notifyParentAboutConsultationInvitation(consultation);

        return healthConsultationRepository.save(consultation);

    }

    // Get a consultation by ID
    public Optional<HealthConsultation> getConsultationById(int consultationId) {
        return healthConsultationRepository.findById(consultationId);
    }

    public HealthConsultation save(HealthConsultation consultation) {
        return healthConsultationRepository.save(consultation);
    }
}
