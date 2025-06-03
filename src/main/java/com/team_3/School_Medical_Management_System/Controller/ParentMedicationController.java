package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.MedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.MedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.StudentServiceInterFace;
import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;
import com.team_3.School_Medical_Management_System.Model.Student;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parent/medication")
public class ParentMedicationController {

    @Autowired
    private MedicationSubmissionServiceInterface medicationSubmissionService;

    @Autowired
    private StudentServiceInterFace studentService;

    @GetMapping("/children/{parentID}")
    public ResponseEntity<List<Student>> getChildrenByParentId(@PathVariable int parentID) {
        List<Student> children = studentService.getStudentsByParentId(parentID);
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
}
