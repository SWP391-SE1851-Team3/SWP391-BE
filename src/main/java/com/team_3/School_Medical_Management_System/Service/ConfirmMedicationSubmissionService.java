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

    @Override
    public ConfirmMedicationSubmissionDTO createConfirmation(ConfirmMedicationSubmissionDTO confirmDTO) {
        ConfirmMedicationSubmission confirmation = convertToEntity(confirmDTO);

        // Update the MedicationSubmission status based on nurse confirmation
        Optional<ConfirmMedicationSubmission> confirmMedicationSubmissionOpt =
                confirmMedicationSubmissionInterFace.findByMedicationSubmissionId(confirmDTO.getMedicationSubmissionId());

        if (confirmMedicationSubmissionOpt.isPresent()) {
            ConfirmMedicationSubmission confirmMedicationSubmission = confirmMedicationSubmissionOpt.get();
            if (confirmDTO.getStatus().equals(ConfirmMedicationSubmission.STATUS_APPROVED)) {
                confirmMedicationSubmission.setStatus(ConfirmMedicationSubmission.STATUS_APPROVED);
            } else {
                confirmMedicationSubmission.setStatus(ConfirmMedicationSubmission.STATUS_REJECTED);
            }
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
    public ConfirmMedicationSubmissionDTO updateStatusAndNurse(int confirmId, String status, String reason, Integer nurseId) {
        Optional<ConfirmMedicationSubmission> confirmationOpt = confirmRepository.findById(confirmId);
        if (confirmationOpt.isPresent()) {
            ConfirmMedicationSubmission confirmation = confirmationOpt.get();
            confirmation.setStatus(status);
            if (reason != null) confirmation.setReason(reason);
            // Nếu status là ADMINISTERED và nurseId khác null thì set nurseId
            if (ConfirmMedicationSubmission.STATUS_ADMINISTERED.equalsIgnoreCase(status) && nurseId != null) {
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
