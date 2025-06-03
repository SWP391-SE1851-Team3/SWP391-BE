package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.ConfirmMedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.ConfirmMedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.MedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/nurse")
public class NurseMedicationController {

    @Autowired
    private MedicationSubmissionServiceInterface medicationSubmissionService;

    @Autowired
    private ConfirmMedicationSubmissionServiceInterface confirmService;

    @GetMapping("/medication-dashboard")
    public String medicationDashboard(Model model, @RequestParam(required = false) Integer nurseId) {
        if (nurseId == null) {
            // In a real app, get this from the session or authentication context
            nurseId = 1; // Default for testing
        }

        // Get pending medication submissions
        List<MedicationSubmission> pendingSubmissions = medicationSubmissionService.getAllSubmissionsByStatus(
                MedicationSubmission.SubmissionStatus.PENDING);
        model.addAttribute("pendingSubmissions", pendingSubmissions);

        // Get approved confirmations (where status=true but not yet administered)
        List<ConfirmMedicationSubmissionDTO> approvedConfirmations = confirmService.getAllConfirmations().stream()
                .filter(c -> c.isStatus() && !c.isReceivedMedicine())
                .collect(Collectors.toList());
        model.addAttribute("approvedConfirmations", approvedConfirmations);

        // Get administered confirmations
        List<ConfirmMedicationSubmissionDTO> administeredConfirmations = confirmService.getAllConfirmations().stream()
                .filter(c -> c.isStatus() && c.isReceivedMedicine())
                .collect(Collectors.toList());
        model.addAttribute("administeredConfirmations", administeredConfirmations);

        model.addAttribute("nurseId", nurseId);

        return "nurse-medication-confirmation";
    }

    @PostMapping("/confirm-medication")
    public String confirmMedication(
            @RequestParam int medicationSubmissionId,
            @RequestParam int nurseId,
            @RequestParam boolean status,
            @RequestParam String evidence,
            RedirectAttributes redirectAttributes) {

        ConfirmMedicationSubmissionDTO confirmDTO = new ConfirmMedicationSubmissionDTO();
        confirmDTO.setMedicationSubmissionId(medicationSubmissionId);
        confirmDTO.setNurseId(nurseId);
        confirmDTO.setStatus(status);
        confirmDTO.setEvidence(evidence);
        confirmDTO.setReceivedMedicine(false);
        confirmDTO.setConfirmedAt(LocalDateTime.now());

        confirmService.createConfirmation(confirmDTO);

        String message = status ? "Medication approved successfully" : "Medication rejected";
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addAttribute("nurseId", nurseId);

        return "redirect:/nurse/medication-dashboard";
    }

    @PostMapping("/medication-taken")
    public String medicationTaken(
            @RequestParam int confirmId,
            RedirectAttributes redirectAttributes) {

        ConfirmMedicationSubmissionDTO updatedConfirmation = confirmService.updateMedicationTaken(confirmId, true);

        if (updatedConfirmation != null) {
            redirectAttributes.addFlashAttribute("message", "Medication marked as administered successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update medication status");
        }

        return "redirect:/nurse/medication-dashboard";
    }
}
