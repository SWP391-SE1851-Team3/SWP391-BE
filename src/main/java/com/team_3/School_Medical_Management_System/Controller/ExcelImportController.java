package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.Expection.ExcelHelper;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ParentRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.StudentRepository;
import com.team_3.School_Medical_Management_System.Model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/excel")
public class ExcelImportController {

    @Autowired
    private ParentRepository parentRepo;

    @Autowired
    private StudentRepository studentRepo;

    @PostMapping(value = "/import-students", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importStudents(@RequestParam("file") MultipartFile file) {
        try {
            List<Student> students = ExcelHelper.parseStudentExcel(file, parentRepo);
            studentRepo.saveAll(students);
            return ResponseEntity.ok("Import thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }
}
