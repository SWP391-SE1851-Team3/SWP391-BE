package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.StudentMappingParent;
import com.team_3.School_Medical_Management_System.DTO.StudentsDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.StudentServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.StudentInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.StudentRepository;
import com.team_3.School_Medical_Management_System.Model.Student;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService implements StudentServiceInterFace {
    private StudentInterFace studentInterFace;
    @Autowired
    private StudentRepository studentRepo;
    @Autowired
   public StudentService(StudentInterFace studentInterFace) {
        this.studentInterFace = studentInterFace;
    }


    @Override
    public void addStudent(Student student) {
        var p = getStudent(student.getStudentID());
        if (p == null) {
            studentInterFace.addStudent(student);
        } else {
            throw new RuntimeException("Student already exists");
        }
    }

    @Override
    public void removeStudent(int id) {
        var p = getStudent(id);
        if (p != null) {
            studentInterFace.removeStudent(id);
        } else {
            throw new RuntimeException("Student not found");
        }
    }

    @Override
    public Student getStudent(int id) {
        return studentInterFace.getStudent(id);
    }

    @Override
    public List<Student> getStudents() {
        return studentInterFace.getStudents();
    }

    @Override
    public Student UpdateStudent(Student student) {
        var p = getStudent(student.getStudentID());
        if (p == null) {
            throw new RuntimeException("Student not found");
        } else {
            p.setClassName(student.getClassName());
            return studentInterFace.UpdateStudent(student);
        }
    }

    @Override
    public Student GetStudentByName(String FullName, String ClassName) {
        var p = studentInterFace.GetStudentByName(FullName, ClassName);
        if (p.getFullName().isEmpty() || p.getClassName().isEmpty()) {
            throw new RuntimeException("Student not found");
        } else {
            return p;
        }
    }

    @Override
    public List<StudentMappingParent> getStudentsByParentID(int parentID) {
        List<Student> students = studentInterFace.getStudentsByParentID(parentID);
        if (students == null || students.isEmpty()) {
            throw new RuntimeException("Không tìm thấy học sinh nào với parentID = " + parentID);
        }
        return students.stream()
                .map(TransferModelsDTO::MappingStudent)
                .collect(Collectors.toList()); // ✅ Trả về tất cả học sinh đã mapping
    }

    @Override
    public List<StudentsDTO> getAllStudentsByClassName(String className) {

        List<Student> clasName = studentRepo.findAll();
        List<StudentsDTO> sameClassName = new ArrayList<>();
        for (Student m : clasName) {
            if(m.getClassName().equalsIgnoreCase(className)){
                StudentsDTO studentsDTO = new StudentsDTO();
                studentsDTO.setStudentID(m.getStudentID());

                studentsDTO.setGender(m.getGender());
                studentsDTO.setFullName(m.getFullName());
                studentsDTO.setClassName(m.getClassName());
                studentsDTO.setIsActive(m.getIsActive());
                studentsDTO.setParentID(m.getParentID());
                sameClassName.add(studentsDTO);
            }
        }

        return sameClassName;
    }
}
