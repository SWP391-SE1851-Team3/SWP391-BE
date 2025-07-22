package com.team_3.School_Medical_Management_System.InterFaceSerivce;

import com.team_3.School_Medical_Management_System.DTO.HealthConsultationDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthConsultationUpdateDTO;
import com.team_3.School_Medical_Management_System.Model.HealthConsultation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HealthConsultationServiceInterface {

    // Helper method to get nurse name by ID
    String getNurseNameById(Integer nurseId);

    // Get all consultations
    List<HealthConsultation> getAllConsultations();

    // Get consultations by status (pending/completed)
    List<HealthConsultation> getConsultationsByStatus(String status);

    // Get consultations for a specific student
    List<HealthConsultation> getConsultationsByStudent(int studentId);

    // Update consultation status
    HealthConsultation updateConsultationStatus(int consultationId, String status, String notes, String location, Date consultDate, Integer updatedByNurseID);

    // Notify parent about consultation invitation
    void notifyParentAboutConsultationInvitation(HealthConsultation consultation);

    // Convert entity to DTO with nurse names
    HealthConsultationDTO convertToDTO(HealthConsultation consultation);

    // Convert entity to update DTO
    HealthConsultationUpdateDTO convertToUpdateDTO(HealthConsultation consultation);

    // Create a new consultation
    HealthConsultation createConsultation(HealthConsultationDTO dto);

    // Get a consultation by ID
    Optional<HealthConsultation> getConsultationById(int consultationId);

    // Save consultation
    HealthConsultation save(HealthConsultation consultation);
}
