package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.StudentHealthProfileDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.StudentHealthProfileServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.StudentHealthProfileInterFace;
import com.team_3.School_Medical_Management_System.Model.StudentHealthProfile;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StudentHealthProfileService implements StudentHealthProfileServiceInterFace {
    private StudentHealthProfileInterFace studentHealthProfileInterFace;

    @Autowired
    public StudentHealthProfileService(StudentHealthProfileInterFace studentHealthProfileInterFace) {
        this.studentHealthProfileInterFace = studentHealthProfileInterFace;
    }

    @Override
    public StudentHealthProfile getHealthProfileByStudentId(int studentId) {
        var student = studentHealthProfileInterFace.getHealthProfileByStudentId(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found");
        } else {
            return student;
        }
    }

    @Override
    public List<StudentHealthProfile> getAllHealthProfiles() {
        return studentHealthProfileInterFace.getAllHealthProfiles();
    }

    @Override
    public StudentHealthProfile updateHealthProfile(StudentHealthProfileDTO dto) {
       return studentHealthProfileInterFace.updateHealthProfile(dto);
    }

    @Override
    public void deleteHealthProfile(int studentId) {
        var student = studentHealthProfileInterFace.getHealthProfileByStudentId(studentId);
        if (student == null) {
            throw new RuntimeException("Student dose not exist");
        } else {
            studentHealthProfileInterFace.deleteHealthProfile(studentId);
        }
    }

    @Override
    public StudentHealthProfile AddHealthProfile(StudentHealthProfileDTO dto) {
        return studentHealthProfileInterFace.AddHealthProfile(dto);
    }

    @Override
    public StudentHealthProfile getHealthProfileByStudentName(String studentName) {
        var student = studentHealthProfileInterFace.getHealthProfileByStudentName(studentName);
        if (student == null) {
            throw new RuntimeException("StudentProfile not found");

        } else {
            return student;
        }
    }
}
