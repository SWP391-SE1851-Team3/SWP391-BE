package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.StudentHealthProfileDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.StudentHealthProfileServiceInterFace;
import com.team_3.School_Medical_Management_System.Model.StudentHealthProfile;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/StudentHealthProfiles")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentHealthProfileController {


    private StudentHealthProfileServiceInterFace studentHealthProfileServiceInterFace;

    @Autowired
    public StudentHealthProfileController(StudentHealthProfileServiceInterFace studentHealthProfileServiceInterFace) {
        this.studentHealthProfileServiceInterFace = studentHealthProfileServiceInterFace;
    }

    @GetMapping
    public List<StudentHealthProfile> getAllStudentHealthProfiles() {
        return studentHealthProfileServiceInterFace.getAllHealthProfiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentHealthProfile> getStudentHealthProfile(@PathVariable int id) {
        StudentHealthProfile profile = studentHealthProfileServiceInterFace.getHealthProfileByStudentId(id);
        if (profile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về lỗi 404 nếu không tìm thấy
        }
        return ResponseEntity.ok(profile);
    }


    @PostMapping
    public ResponseEntity<StudentHealthProfile> addStudentHealthProfile(@Valid @RequestBody StudentHealthProfileDTO dto) {
        StudentHealthProfile profile = studentHealthProfileServiceInterFace.AddHealthProfile(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(profile);
    }

    @PostMapping("/profile-studentname/{studentName}")
    public StudentHealthProfile getStudentHealthProfileByStudentName(@Valid @PathVariable String studentName) {
        return studentHealthProfileServiceInterFace.getHealthProfileByStudentName(studentName);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudentHealthProfile(@PathVariable int id) {
        var student = studentHealthProfileServiceInterFace.getHealthProfileByStudentId(id);
        if(student != null) {
            studentHealthProfileServiceInterFace.deleteHealthProfile(id);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit_profile_studentname")
    public ResponseEntity<StudentHealthProfile> editStudentHealthProfile(@RequestBody StudentHealthProfileDTO dto) {
        studentHealthProfileServiceInterFace.updateHealthProfile(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/byStudentId/{studentId}")
    public ResponseEntity<StudentHealthProfile> getByStudentId(@PathVariable int studentId) {
        var profile = studentHealthProfileServiceInterFace.getStudentHealthProfileByStudentId(studentId);
        if(profile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else {
            return ResponseEntity.ok(profile);
        }
    }







}
