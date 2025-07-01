package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.StudentDTO;
import com.team_3.School_Medical_Management_System.DTO.StudentMappingParent;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.StudentServiceInterFace;
import com.team_3.School_Medical_Management_System.Model.Student;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")

public class StudentContoller {
    private StudentServiceInterFace studentService;

    @Autowired
    public StudentContoller(StudentServiceInterFace studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> GetAllStudent() {
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> GetStudentById(@PathVariable Integer id) {
        var p = studentService.getStudent(id);
        if (p == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(p);
        }
    }

    @GetMapping("/{fullName}/{className}")
    public ResponseEntity<Student> GetStudentByFullName(@PathVariable String fullName, @PathVariable String className) {
        var p = studentService.GetStudentByName(fullName, className);
        if (p == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(p);
        }
    }

    @PostMapping
    public void AddStudent(@Valid @RequestBody Student student) {
        studentService.addStudent(student);
    }

    @DeleteMapping("/{id}")
    public void DeleteStudent(@PathVariable Integer id) {
        var p = studentService.getStudent(id);
        if (p == null) {
            return;
        } else {
            studentService.removeStudent(id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> UpdateStudent(@PathVariable int id, @RequestBody StudentDTO studentDTO) {
        var p = studentService.getStudent(id);
        if (p == null) {
            return ResponseEntity.notFound().build();
        } else {
            p.setClassName(studentDTO.getClassName());
            studentService.UpdateStudent(p);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("Parents/{parentId}")
    public List<StudentMappingParent> getStudentsByParentID(@PathVariable("parentId") int parentId) {
        return studentService.getStudentsByParentID(parentId);
    }


}
