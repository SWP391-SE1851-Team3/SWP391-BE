package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.DTO.StudentMappingParent;
import com.team_3.School_Medical_Management_System.InterfaceRepo.StudentInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.StudentRepository;
import com.team_3.School_Medical_Management_System.Model.Student;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentRepo implements StudentInterFace {
    private EntityManager entityManager;

    @Autowired
    public StudentRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    private StudentRepository studentRepo;
    @Override
    public void addStudent(Student student) {
        entityManager.persist(student);
    }

    @Override
    public void removeStudent(int id) {
        entityManager.remove(id);

    }

    @Override
    public Student getStudent(int id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    public List<Student> getStudents() {
        String sql = "select s from Student s";
        return entityManager.createQuery(sql, Student.class).getResultList();
    }

    @Override
    public Student UpdateStudent(Student student) {
        return entityManager.merge(student);
    }

    @Override
    public Student GetStudentByName(String FullName, String ClassName) {
        String sql = "select s from Student s where s.FullName = :FullName and s.ClassName = :ClassName";
        return entityManager.createQuery(sql, Student.class).
                setParameter("FullName", FullName).
                setParameter("ClassName", ClassName).
                getSingleResult();
    }

    @Override
    public List<Student> getStudentsByParentID(int parentID) {
        String sql = "select s from Student s where s.ParentID = :ParentID";
        List<Student> students = entityManager.createQuery(sql, Student.class)
                .setParameter("ParentID", parentID)
                .getResultList();


        return students;
    }

    @Override
    public List<Student> findByClassNameContainingIgnoreCase(String className) {
        String sql = " from Student s where lower(s.ClassName) like lower(concat('%', :className, '%'))";
        return entityManager.createQuery(sql, Student.class)
                .setParameter("className", className)
                .getResultList();
    }

    @Override
    public List<Student> findByFullNameContainingIgnoreCase(String fullName) {
        String sql = "from Student s where lower(s.FullName) like lower(concat('%', :fullName, '%'))";
        return entityManager.createQuery(sql, Student.class)
                .setParameter("fullName", fullName)
                .getResultList();
    }


}