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
        confirmation.setConfirmedAt(LocalDateTime.now());

        // Update the MedicationSubmission status based on nurse confirmation
        Optional<ConfirmMedicationSubmission> confirmMedicationSubmissionOpt =
                confirmMedicationSubmissionInterFace.findByMedicationSubmissionId(confirmDTO.getMedicationSubmissionId());

        if (confirmMedicationSubmissionOpt.isPresent()) {
            ConfirmMedicationSubmission confirmMedicationSubmission = confirmMedicationSubmissionOpt.get();
            if (confirmDTO.getStatus() == ConfirmMedicationSubmission.confirmMedicationSubmissionStatus.APPROVED) {
                confirmMedicationSubmission.setStatus(ConfirmMedicationSubmission.confirmMedicationSubmissionStatus.APPROVED);
            } else {
                confirmMedicationSubmission.setStatus(ConfirmMedicationSubmission.confirmMedicationSubmissionStatus.REJECTED);
            }
            confirmMedicationSubmissionInterFace.save(confirmMedicationSubmission);
        }

        ConfirmMedicationSubmission savedConfirmation = confirmRepository.save(confirmation);
        return convertToDTO(savedConfirmation);
    }

    @Override
    public ConfirmMedicationSubmissionDTO updateConfirmationStatus(int confirmId, boolean status) {
        Optional<ConfirmMedicationSubmission> confirmationOpt = confirmRepository.findById(confirmId);
        if (confirmationOpt.isPresent()) {
            ConfirmMedicationSubmission confirmation = confirmationOpt.get();
            confirmation.setConfirmedAt(LocalDateTime.now());

            // Update the MedicationSubmission status
            Optional<MedicationSubmission> medicationSubmissionOpt =
                medicationSubmissionInterFace.findById(confirmation.getMedicationSubmissionId());

            if (medicationSubmissionOpt.isPresent()) {
                ConfirmMedicationSubmission confirmMedicationSubmission = confirmationOpt.get();
                if (status) {
                    confirmMedicationSubmission.setStatus(ConfirmMedicationSubmission.confirmMedicationSubmissionStatus.APPROVED);
                } else {
                    confirmMedicationSubmission.setStatus(ConfirmMedicationSubmission.confirmMedicationSubmissionStatus.REJECTED);;
                }
                confirmMedicationSubmission.setConfirmedAt(LocalDateTime.now());
                confirmMedicationSubmissionInterFace.save(confirmMedicationSubmission);
            }

            ConfirmMedicationSubmission savedConfirmation = confirmRepository.save(confirmation);
            return convertToDTO(savedConfirmation);
        }
        return null;
    }

    @Override
    public ConfirmMedicationSubmissionDTO updateMedicationTaken(int confirmId, ConfirmMedicationSubmission.confirmMedicationSubmissionReceivedMedicine receivedMedicine) {
        Optional<ConfirmMedicationSubmission> confirmationOpt = confirmRepository.findById(confirmId);
        if (confirmationOpt.isPresent()) {
            ConfirmMedicationSubmission confirmation = confirmationOpt.get();
            confirmation.setReceivedMedicine(receivedMedicine);
            confirmation.setMedicationTakenAt(LocalDateTime.now());
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

        // Convert boolean status to enum
        if (dto.getStatus() == ConfirmMedicationSubmission.confirmMedicationSubmissionStatus.APPROVED) {
            entity.setStatus(ConfirmMedicationSubmission.confirmMedicationSubmissionStatus.APPROVED);
        } else {
            entity.setStatus(ConfirmMedicationSubmission.confirmMedicationSubmissionStatus.REJECTED);
        }

        entity.setNurseId(dto.getNurseId());
        entity.setEvidence(dto.getEvidence());

        // Convert boolean receivedMedicine to enum
        if (dto.getReceivedMedicine() == ConfirmMedicationSubmission.confirmMedicationSubmissionReceivedMedicine.YES) {
            entity.setReceivedMedicine(ConfirmMedicationSubmission.confirmMedicationSubmissionReceivedMedicine.YES);
        } else {
            entity.setReceivedMedicine(ConfirmMedicationSubmission.confirmMedicationSubmissionReceivedMedicine.NO);
        }

        entity.setConfirmedAt(dto.getConfirmedAt());
        entity.setMedicationTakenAt(dto.getMedicationTakenAt());
        return entity;
    }

    private ConfirmMedicationSubmissionDTO convertToDTO(ConfirmMedicationSubmission entity) {
        ConfirmMedicationSubmissionDTO dto = new ConfirmMedicationSubmissionDTO();
        dto.setConfirmId(entity.getConfirmId());
        dto.setMedicationSubmissionId(entity.getMedicationSubmissionId());

        // Set the status enum directly
        dto.setStatus(entity.getStatus());

        dto.setNurseId(entity.getNurseId());
        dto.setEvidence(entity.getEvidence());

        // Set the receivedMedicine enum directly
        dto.setReceivedMedicine(entity.getReceivedMedicine());

        dto.setConfirmedAt(entity.getConfirmedAt());
        dto.setMedicationTakenAt(entity.getMedicationTakenAt());
        return dto;
    }
}
