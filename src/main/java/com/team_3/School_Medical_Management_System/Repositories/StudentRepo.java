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
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class StudentRepo implements StudentInterFace {
    private EntityManager entityManager;

    @Autowired
    public StudentRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void addStudent(Student student) {
        entityManager.persist(student);
    }

    @Override
    public void removeStudent(Integer id) {

        Student student = entityManager.find(Student.class, id);
        if (student != null) {
            entityManager.remove(student); // ✅ đúng, truyền entity
        } else {
            throw new RuntimeException("Student not found");
        }
    }


    @Override
    public Student getStudent(Integer id) {
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
        String sql = "select s from Student s where s.FullName = :FullName and s.className = :ClassName";
        return entityManager.createQuery(sql, Student.class).
                setParameter("FullName", FullName).
                setParameter("ClassName", ClassName).
                getSingleResult();
    }

    @Override
    public List<Student> getStudentsByParentID(int parentID) {
        String sql = "select s from Student s where s.parent.ParentID = :ParentID";
        List<Student> students = entityManager.createQuery(sql, Student.class)
                .setParameter("ParentID", parentID)
                .getResultList();
        return students;
    }

    @Override
    public Optional<Student> findById(int studentId) {
        Student student = entityManager.find(Student.class, studentId);
        return Optional.ofNullable(student);
    }

    @Override
    public List<Student> findByClassName(String className) {
        String jpql = "SELECT s FROM Student s WHERE s.className = :className";
        return entityManager.createQuery(jpql, Student.class)
                .setParameter("className", className)
                .getResultList();
    }

}