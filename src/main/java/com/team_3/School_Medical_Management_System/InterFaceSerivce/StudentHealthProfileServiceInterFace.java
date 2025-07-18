package com.team_3.School_Medical_Management_System.InterFaceSerivce;

import com.team_3.School_Medical_Management_System.DTO.StudentHealthProfileDTO;
import com.team_3.School_Medical_Management_System.Model.StudentHealthProfile;

import java.util.List;

public interface StudentHealthProfileServiceInterFace {
    StudentHealthProfile getHealthProfileByStudentId(int profileId);
    List<StudentHealthProfile> getAllHealthProfiles();
    StudentHealthProfile updateHealthProfile(StudentHealthProfileDTO dto);
    void deleteHealthProfile(int studentId);
    StudentHealthProfile AddHealthProfile(StudentHealthProfileDTO dto);
    StudentHealthProfile getHealthProfileByStudentName(String studentName);
    StudentHealthProfile getStudentHealthProfileByStudentId(int studentId);
}
