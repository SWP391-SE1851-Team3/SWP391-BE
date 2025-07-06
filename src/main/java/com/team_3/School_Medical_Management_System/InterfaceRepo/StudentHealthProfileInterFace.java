package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.DTO.StudentHealthProfileDTO;
import com.team_3.School_Medical_Management_System.Model.StudentHealthProfile;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StudentHealthProfileInterFace {
    StudentHealthProfile getHealthProfileByStudentId(int profileId);
    List<StudentHealthProfile> getAllHealthProfiles();
    StudentHealthProfile updateHealthProfile(StudentHealthProfileDTO dto);
    void deleteHealthProfile(int studentId);
    StudentHealthProfile AddHealthProfile(StudentHealthProfileDTO dto);
    StudentHealthProfile getHealthProfileByStudentName(String studentName);
    StudentHealthProfile getStudentHealthProfileByStudentId(int studentId);

}
