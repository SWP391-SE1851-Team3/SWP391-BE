package com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace;

import com.team_3.School_Medical_Management_System.DTO.StudentMappingParent;
import com.team_3.School_Medical_Management_System.DTO.StudentsDTO;
import com.team_3.School_Medical_Management_System.Model.Student;

import java.util.List;

public interface StudentServiceInterFace {
    public void addStudent(Student student);
    public void removeStudent(Integer id);
    public Student getStudent(Integer id);
    public List<Student> getStudents();
    public Student UpdateStudent(Student student);
    public Student GetStudentByName(String FullName, String ClassName);
    public List<StudentMappingParent> getStudentsByParentID(int parentID);
    public List<StudentsDTO> getAllStudentsByClassName(String className);
}
