package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.DTO.StudentMappingParent;
import com.team_3.School_Medical_Management_System.Model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentInterFace {
    public void addStudent(Student student);

    public void removeStudent(int id);

    public Student getStudent(int id);

    public List<Student> getStudents();

    public Student UpdateStudent(Student student);

    public Student GetStudentByName(String FullName, String ClassName);

    public List<Student> getStudentsByParentID(int parentID);

    public Optional<Student> findById(int studentId);

    List<Student> findByClassName(String className);

    public void setNullParentIDByParentID(int parentID);
}
