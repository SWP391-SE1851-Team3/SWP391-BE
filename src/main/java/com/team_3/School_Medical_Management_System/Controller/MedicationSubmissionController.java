package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.ConfirmMedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.DTO.StudentMappingParent;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.ConfirmMedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.MedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.StudentServiceInterFace;
import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;
import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;
import com.team_3.School_Medical_Management_System.Model.Student;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medication-submission")
public class MedicationSubmissionController {

    @Autowired
    private MedicationSubmissionServiceInterface medicationSubmissionService;

    @Autowired
    private ConfirmMedicationSubmissionServiceInterface confirmService;

    @Autowired
    private StudentServiceInterFace studentService;

    @GetMapping("/children/{parentID}")
    public ResponseEntity<List<StudentMappingParent>> getChildrenByParentId(@PathVariable int parentID) {
        List<StudentMappingParent> children = studentService.getStudentsByParentID(parentID);
        return new ResponseEntity<>(children, HttpStatus.OK);
    }

    @PostMapping("/submit")
    public ResponseEntity<MedicationSubmission> submitMedication(@Valid @RequestBody MedicationSubmissionDTO medicationSubmissionDTO) {
        MedicationSubmission submission = medicationSubmissionService.submitMedication(medicationSubmissionDTO);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("/submissions/{parentId}")
    public ResponseEntity<List<MedicationSubmission>> getMedicationSubmissions(@PathVariable int parentId) {
        List<MedicationSubmission> submissions = medicationSubmissionService.getAllMedicationSubmissionsByParentId(parentId);
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }


    @PostMapping("/confirm-medication")
    public String confirmMedication(
            @RequestParam int medicationSubmissionId,
            @RequestParam int nurseId,
            @RequestParam ConfirmMedicationSubmission.confirmMedicationSubmissionStatus status,
            @RequestParam String evidence,
            RedirectAttributes redirectAttributes) {

        ConfirmMedicationSubmissionDTO confirmDTO = new ConfirmMedicationSubmissionDTO();
        confirmDTO.setMedicationSubmissionId(medicationSubmissionId);
        confirmDTO.setNurseId(nurseId);
        confirmDTO.setStatus(status);
        confirmDTO.setEvidence(evidence);
        confirmDTO.setConfirmedAt(LocalDateTime.now());

        confirmService.createConfirmation(confirmDTO);

        return "redirect:/medication-submission/medication-dashboard";
    }
}


