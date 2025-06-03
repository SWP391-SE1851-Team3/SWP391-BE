package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.ChangePasswordRequest;
import com.team_3.School_Medical_Management_System.DTO.LoginResponse;
import com.team_3.School_Medical_Management_System.DTO.SchoolNurseDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.SchoolNurseServiceInterFace;
import com.team_3.School_Medical_Management_System.DTO.NurseLoginResponseDTO;
import com.team_3.School_Medical_Management_System.Model.SchoolNurse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/SchoolNurses")
@CrossOrigin(origins = "http://localhost:5173")

public class SchoolNurseController {
    private SchoolNurseServiceInterFace schoolNurseService;

    @Autowired
    public SchoolNurseController(SchoolNurseServiceInterFace schoolNurseService) {
        this.schoolNurseService = schoolNurseService;
    }

    @GetMapping
    public List<SchoolNurse> getAllSchoolNurses() {
        return schoolNurseService.getSchoolNurses();
    }

    @GetMapping("/{fullName}")
    public ResponseEntity<SchoolNurse> getSchoolNurse(@PathVariable("fullName") String FullName) {
        var p = schoolNurseService.getSchoolNursesByName(FullName);
        if (p == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(p);
        }
    }

    @PostMapping
    public void addSchoolNurse(@Valid @RequestBody SchoolNurse schoolNurse) {
        schoolNurseService.AddNewSchoolNurses(schoolNurse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolNurse> updateSchoolNurse(@PathVariable("id") int id, SchoolNurseDTO schoolNurseDTO) {
        var p = schoolNurseService.GetSchoolNursesById(id);
        if (p == null) {
            throw new RuntimeException("SchoolNurse not found");
        }
        p.setPhone(schoolNurseDTO.getPhone());
        p.setEmail(schoolNurseDTO.getEmail());
        p.setCertification(schoolNurseDTO.getCertification());
        p.setSpecialisation(schoolNurseDTO.getSpecialisation());
        schoolNurseService.UpdateSchoolNurses(p);
        return ResponseEntity.ok().body(p);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody NurseLoginResponseDTO loginRequest) {
        SchoolNurse nurse = schoolNurseService.LoginByAccount(loginRequest.getEmail(), loginRequest.getPassword());
        if (nurse != null) {
            // Trả về dữ liệu an toàn, không chứa mật khẩu, giu bao mat
            return ResponseEntity.ok(new LoginResponse(nurse.getEmail(), 2));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password incorrect");
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSchoolNurse(@PathVariable("id") int id) {
        var p = schoolNurseService.GetSchoolNursesById(id);
        if (p == null) {
            return ResponseEntity.notFound().build();
        } else {
            schoolNurseService.DeleteSchoolNurses(id);
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        boolean succes = schoolNurseService.changePassword(changePasswordRequest.getEmail(), changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
        if (succes) {
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect");
        }
    }


}
