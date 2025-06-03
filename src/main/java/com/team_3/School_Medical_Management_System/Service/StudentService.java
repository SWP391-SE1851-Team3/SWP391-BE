package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.InterFaceSerivce.StudentServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.StudentInterFace;
import com.team_3.School_Medical_Management_System.Model.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StudentService implements StudentServiceInterFace {
    private StudentInterFace studentInterface;
    @Autowired
    public StudentService(StudentInterFace studentInterface) {
        this.studentInterface = studentInterface;
    }

    @Override
    public void addStudent(Student student) {
        var p = getStudent(student.getStudentID());
        if(p == null) {
            studentInterface.addStudent(student);
        }else {
           throw  new RuntimeException("Student already exists");
        }
    }

    @Override
    public void removeStudent(int id) {
        var p = getStudent(id);
        if(p != null) {
            studentInterface.removeStudent(id);
        }else {
            throw  new RuntimeException("Student not found");
        }
    }

    @Override
    public Student getStudent(int id) {
        return studentInterface.getStudent(id);
    }

    @Override
    public List<Student> getStudents() {
        return studentInterface.getStudents();
    }

    @Override
    public Student UpdateStudent(Student student) {
        var p = getStudent(student.getStudentID());
        if(p == null) {
            throw  new RuntimeException("Student not found");
        }else {
            p.setClassName(student.getClassName());
            return studentInterface.UpdateStudent(student);
        }
    }

    @Override
    public Student GetStudentByName(String FullName, String ClassName) {
        var p = studentInterface.GetStudentByName(FullName, ClassName);
        if(p.getFullName().isEmpty() || p.getClassName().isEmpty()) {
            throw new RuntimeException("Student not found");
        }else {
            return p;
        }
    }

    @Override
    public List<Student> getStudentsByParentId(int parentId) {
        return studentInterface.getStudentsByParentId(parentId);
    }
}
