package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.ConfirmMedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.ConfirmMedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;
import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ConfirmMedicationSubmissionInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicationSubmissionInterFace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConfirmMedicationSubmissionService implements ConfirmMedicationSubmissionServiceInterface {

    @Autowired
    private ConfirmMedicationSubmissionInterFace confirmRepository;

    @Autowired
    private MedicationSubmissionInterFace medicationSubmissionInterFace;

    @Override
    public ConfirmMedicationSubmissionDTO createConfirmation(ConfirmMedicationSubmissionDTO confirmDTO) {
        ConfirmMedicationSubmission confirmation = convertToEntity(confirmDTO);
        confirmation.setConfirmedAt(LocalDateTime.now());

        // Update the MedicationSubmission status based on nurse confirmation
        Optional<MedicationSubmission> medicationSubmissionOpt =
            medicationSubmissionInterFace.findById(confirmDTO.getMedicationSubmissionId());

        if (medicationSubmissionOpt.isPresent()) {
            MedicationSubmission medicationSubmission = medicationSubmissionOpt.get();
            if (confirmDTO.isStatus()) {
                medicationSubmission.setStatus(MedicationSubmission.SubmissionStatus.APPROVED);
                medicationSubmission.setProcessedDate(LocalDateTime.now());
            } else {
                medicationSubmission.setStatus(MedicationSubmission.SubmissionStatus.REJECTED);
                medicationSubmission.setProcessedDate(LocalDateTime.now());
            }
            medicationSubmissionInterFace.save(medicationSubmission);
        }

        ConfirmMedicationSubmission savedConfirmation = confirmRepository.save(confirmation);
        return convertToDTO(savedConfirmation);
    }

    @Override
    public ConfirmMedicationSubmissionDTO updateConfirmationStatus(int confirmId, boolean status) {
        Optional<ConfirmMedicationSubmission> confirmationOpt = confirmRepository.findById(confirmId);
        if (confirmationOpt.isPresent()) {
            ConfirmMedicationSubmission confirmation = confirmationOpt.get();
            confirmation.setStatus(status);
            confirmation.setConfirmedAt(LocalDateTime.now());

            // Update the MedicationSubmission status
            Optional<MedicationSubmission> medicationSubmissionOpt =
                medicationSubmissionInterFace.findById(confirmation.getMedicationSubmissionId());

            if (medicationSubmissionOpt.isPresent()) {
                MedicationSubmission medicationSubmission = medicationSubmissionOpt.get();
                if (status) {
                    medicationSubmission.setStatus(MedicationSubmission.SubmissionStatus.APPROVED);
                } else {
                    medicationSubmission.setStatus(MedicationSubmission.SubmissionStatus.REJECTED);
                }
                medicationSubmission.setProcessedDate(LocalDateTime.now());
                medicationSubmissionInterFace.save(medicationSubmission);
            }

            ConfirmMedicationSubmission savedConfirmation = confirmRepository.save(confirmation);
            return convertToDTO(savedConfirmation);
        }
        return null;
    }

    @Override
    public ConfirmMedicationSubmissionDTO updateMedicationTaken(int confirmId, boolean receivedMedicine) {
        Optional<ConfirmMedicationSubmission> confirmationOpt = confirmRepository.findById(confirmId);
        if (confirmationOpt.isPresent()) {
            ConfirmMedicationSubmission confirmation = confirmationOpt.get();
            confirmation.setReceivedMedicine(receivedMedicine);
            confirmation.setMedicationTakenAt(LocalDateTime.now());

            // Update the MedicationSubmission status if medication was taken
            if (receivedMedicine) {
                Optional<MedicationSubmission> medicationSubmissionOpt =
                    medicationSubmissionInterFace.findById(confirmation.getMedicationSubmissionId());

                if (medicationSubmissionOpt.isPresent()) {
                    MedicationSubmission medicationSubmission = medicationSubmissionOpt.get();
                    medicationSubmission.setStatus(MedicationSubmission.SubmissionStatus.ADMINISTERED);
                    medicationSubmission.setAdministeredDate(LocalDateTime.now());
                    medicationSubmissionInterFace.save(medicationSubmission);
                }
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
        entity.setStatus(dto.isStatus());
        entity.setNurseId(dto.getNurseId());
        entity.setEvidence(dto.getEvidence());
        entity.setReceivedMedicine(dto.isReceivedMedicine());
        entity.setConfirmedAt(dto.getConfirmedAt());
        entity.setMedicationTakenAt(dto.getMedicationTakenAt());
        return entity;
    }

    private ConfirmMedicationSubmissionDTO convertToDTO(ConfirmMedicationSubmission entity) {
        ConfirmMedicationSubmissionDTO dto = new ConfirmMedicationSubmissionDTO();
        dto.setConfirmId(entity.getConfirmId());
        dto.setMedicationSubmissionId(entity.getMedicationSubmissionId());
        dto.setStatus(entity.isStatus());
        dto.setNurseId(entity.getNurseId());
        dto.setEvidence(entity.getEvidence());
        dto.setReceivedMedicine(entity.isReceivedMedicine());
        dto.setConfirmedAt(entity.getConfirmedAt());
        dto.setMedicationTakenAt(entity.getMedicationTakenAt());
        return dto;
    }
}
