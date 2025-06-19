package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.ConfirmMedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.ConfirmMedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ConfirmMedicationSubmissionInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicationSubmissionInterFace;
import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;
import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;
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
    private com.team_3.School_Medical_Management_System.InterfaceRepo.StudentRepository studentRepository;

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

            ConfirmMedicationSubmission savedConfirmation = confirmRepository.save(confirmation);
            return convertToDTO(savedConfirmation);
        }
        return null;
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

    @Override
    public List<ConfirmMedicationSubmissionDTO> getConfirmationsByStudentName(String studentName) {
        // Tìm tất cả học sinh có tên giống hoặc gần giống
        List<Integer> studentIds = studentRepository.findByFullNameContainingIgnoreCase(studentName)
            .stream().map(s -> s.getStudentId()).collect(Collectors.toList());
        if (studentIds.isEmpty()) return List.of();
        // Tìm tất cả MedicationSubmission liên quan đến các studentId này
        List<Integer> submissionIds = medicationSubmissionInterFace.findByStudentIdIn(studentIds)
            .stream().map(ms -> ms.getMedicationSubmissionId()).collect(Collectors.toList());
        if (submissionIds.isEmpty()) return List.of();
        // Tìm tất cả ConfirmMedicationSubmission liên quan
        List<ConfirmMedicationSubmission> confirmations = confirmRepository.findByMedicationSubmissionIdIn(submissionIds);
        return confirmations.stream().map(this::convertToDTO).collect(Collectors.toList());
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

