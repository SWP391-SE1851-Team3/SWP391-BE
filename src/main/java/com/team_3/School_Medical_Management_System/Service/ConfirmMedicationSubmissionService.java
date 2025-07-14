package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.ConfirmMedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.ConfirmMedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.ConfirmMedicationSubmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConfirmMedicationSubmissionService implements ConfirmMedicationSubmissionServiceInterface {

    @Autowired
    private ConfirmMedicationSubmissionRepo confirmRepository;

    @Autowired
    private MedicationSubmissionInterFace medicationSubmissionInterFace;
    @Autowired
    private ConfirmMedicationSubmissionInterFace confirmMedicationSubmissionInterFace;

    @Autowired
    private EmailService emailService;

    @Autowired
    private StudentRepository studentRepository; // or whatever your actual repository interface is named

    @Autowired
    private SchoolNurseRepository schoolNurseRepository;
    @Autowired
    private NotificationsParentRepository notificationsParentRepository;
    @Override
    public ConfirmMedicationSubmissionDTO createConfirmation(ConfirmMedicationSubmissionDTO confirmDTO) {
        ConfirmMedicationSubmission confirmation = convertToEntity(confirmDTO);

        // Update the MedicationSubmission status based on nurse confirmation
        Optional<ConfirmMedicationSubmission> confirmMedicationSubmissionOpt =
                confirmMedicationSubmissionInterFace.findByMedicationSubmissionId(confirmDTO.getMedicationSubmissionId());

        if (confirmMedicationSubmissionOpt.isPresent()) {
            ConfirmMedicationSubmission confirmMedicationSubmission = confirmMedicationSubmissionOpt.get();
            // Allows any status value to be set directly from the DTO
            confirmMedicationSubmission.setStatus(confirmDTO.getStatus());
            confirmMedicationSubmissionInterFace.save(confirmMedicationSubmission);
        }

        ConfirmMedicationSubmission savedConfirmation = confirmRepository.save(confirmation);
        return convertToDTO(savedConfirmation);
    }

    @Override
    public ConfirmMedicationSubmissionDTO updateConfirmationStatus(int confirmId, String status) {
        Optional<ConfirmMedicationSubmission> confirmationOpt = confirmRepository.findById(confirmId);
        if (confirmationOpt.isPresent()) {
            ConfirmMedicationSubmission confirmation = confirmationOpt.get();
            confirmation.setStatus(status);

            // Update the MedicationSubmission status
            Optional<MedicationSubmission> medicationSubmissionOpt =
                    medicationSubmissionInterFace.findById(confirmation.getMedicationSubmissionId());

            if (medicationSubmissionOpt.isPresent()) {
                ConfirmMedicationSubmission confirmMedicationSubmission = confirmationOpt.get();
                confirmMedicationSubmission.setStatus(status);
                confirmMedicationSubmissionInterFace.save(confirmMedicationSubmission);
            }

            ConfirmMedicationSubmission savedConfirmation = confirmRepository.save(confirmation);
            return convertToDTO(savedConfirmation);
        }
        return null;
    }

    @Override
    public ConfirmMedicationSubmissionDTO updateConfirmationStatusWithReason(int confirmId,
                                                                             String status, String reason) {
        Optional<ConfirmMedicationSubmission> confirmationOpt = confirmRepository.findById(confirmId);
        if (confirmationOpt.isPresent()) {
            ConfirmMedicationSubmission confirmation = confirmationOpt.get();
            confirmation.setStatus(status);
            confirmation.setReason(reason);

            // Update the MedicationSubmission status
            Optional<MedicationSubmission> medicationSubmissionOpt =
                    medicationSubmissionInterFace.findById(confirmation.getMedicationSubmissionId());

            if (medicationSubmissionOpt.isPresent()) {
                ConfirmMedicationSubmission confirmMedicationSubmission = confirmationOpt.get();
                confirmMedicationSubmission.setStatus(status);
                confirmMedicationSubmission.setReason(reason);
                confirmMedicationSubmissionInterFace.save(confirmMedicationSubmission);
            }

            ConfirmMedicationSubmission savedConfirmation = confirmRepository.save(confirmation);
            return convertToDTO(savedConfirmation);
        }
        return null;
    }

    // Thêm hàm này để cập nhật status và nurseId khi cần
    public ConfirmMedicationSubmissionDTO updateStatusAndNurse(int confirmId, String status, String reason, Integer nurseId, String evidence) {
        Optional<ConfirmMedicationSubmission> confirmationOpt = confirmRepository.findById(confirmId);
        if (confirmationOpt.isPresent()) {
            ConfirmMedicationSubmission confirmation = confirmationOpt.get();
            confirmation.setStatus(status); // Set any status directly
            if (reason != null) confirmation.setReason(reason);

            // Set nurseId if provided, without requiring specific status
            if (nurseId != null) {
                confirmation.setNurseId(nurseId);
            }

            // Set evidence if provided
            if (evidence != null) {
                confirmation.setEvidence(evidence);
            }

            ConfirmMedicationSubmission savedConfirmation = confirmRepository.save(confirmation);
            if(confirmation.getStatus().equalsIgnoreCase("Administered  ")){
                // Lấy thông tin học sinh và y tá
                Optional<MedicationSubmission> medicationSubmissionOpt = medicationSubmissionInterFace.findById(confirmation.getMedicationSubmissionId());
                if(medicationSubmissionOpt.isPresent()){
                    MedicationSubmission medicationSubmission = medicationSubmissionOpt.get();
                    Optional<Student> studentOpt = studentRepository.findById(medicationSubmission.getStudentId());
                    if (studentOpt.isPresent()) {
                        Student student = studentOpt.get();
                        SchoolNurse nurse = null;
                        if(confirmation.getNurseId() != null){
                            Optional<SchoolNurse> nurseOpt = schoolNurseRepository.findById(confirmation.getNurseId());
                            if (nurseOpt.isPresent()) {
                                nurse = nurseOpt.get();
                            }
                        }
                        sendConsentFormNotification(student, nurse, confirmation);
                    }
                }
            }
            return convertToDTO(savedConfirmation);
        }
        return null;
    }

    private void sendConsentFormNotification(Student student, SchoolNurse nurse, ConfirmMedicationSubmission confirmation) {
        if (student.getParent() != null) {
            String title = "Thông báo đã cho học sinh uống thuốc - " + student.getFullName();
            String content = "Học sinh " + student.getFullName() +
                    " (Lớp: " + student.getClassName() + ")" +
                    " đã được cho uống thuốc bởi y tá " + nurse.getFullName() +
                    ". Thời gian: " + LocalDateTime.now().toString() +
                    (confirmation.getReason() != null ? ". Ghi chú: " + confirmation.getReason() : "");

            NotificationsParent notification = new NotificationsParent();
            notification.setParent(student.getParent());
            notification.setTitle(title);
            notification.setContent(content);
            notification.setCreateAt(LocalDateTime.now());
            notification.setStatus(false);
            notificationsParentRepository.save(notification);

            try {
                // Gửi email với thông tin người dùng và thời gian
                emailService.sendHtmlNotificationEmailForConfirmMedication(
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
    @Override
    public ConfirmMedicationSubmissionDTO getConfirmationById(int confirmId) {
        Optional<ConfirmMedicationSubmission> confirmationOpt = confirmRepository.findById(confirmId);
        return confirmationOpt.map(this::convertToDTO).orElse(null);
    }

    @Override
    public ConfirmMedicationSubmissionDTO getConfirmationBySubmissionId(int medicationSubmissionId) {
        Optional<ConfirmMedicationSubmission> confirmationOpt =
                confirmRepository.findByMedicationSubmissionId(medicationSubmissionId);
        return confirmationOpt.map(this::convertToDTO).orElse(null);
    }

    @Override
    public List<ConfirmMedicationSubmissionDTO> getConfirmationsByNurse(int nurseId) {
        List<ConfirmMedicationSubmission> confirmations = confirmRepository.findByNurseId(nurseId);
        return confirmations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConfirmMedicationSubmissionDTO> getAllConfirmations() {
        List<ConfirmMedicationSubmission> confirmations = confirmRepository.findAll();
        return confirmations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private ConfirmMedicationSubmission convertToEntity(ConfirmMedicationSubmissionDTO dto) {
        ConfirmMedicationSubmission entity = new ConfirmMedicationSubmission();
        entity.setConfirmId(dto.getConfirmId());
        entity.setMedicationSubmissionId(dto.getMedicationSubmissionId());
        entity.setStatus(dto.getStatus());
        entity.setNurseId(dto.getNurseId());
        entity.setReason(dto.getReason());
        entity.setEvidence(dto.getEvidence());
        return entity;
    }

    private ConfirmMedicationSubmissionDTO convertToDTO(ConfirmMedicationSubmission entity) {
        ConfirmMedicationSubmissionDTO dto = new ConfirmMedicationSubmissionDTO();
        dto.setConfirmId(entity.getConfirmId());
        dto.setMedicationSubmissionId(entity.getMedicationSubmissionId());
        dto.setStatus(entity.getStatus());
        dto.setNurseId(entity.getNurseId());
        dto.setReason(entity.getReason());
        dto.setEvidence(entity.getEvidence());
        return dto;
    }
}
