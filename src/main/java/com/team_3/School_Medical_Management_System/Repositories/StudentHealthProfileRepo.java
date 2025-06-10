package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.DTO.StudentHealthProfileDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.StudentHealthProfileInterFace;
import com.team_3.School_Medical_Management_System.Model.Student;
import com.team_3.School_Medical_Management_System.Model.StudentHealthProfile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class StudentHealthProfileRepo implements StudentHealthProfileInterFace {
    private EntityManager entityManager;

    @Autowired
    public StudentHealthProfileRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public StudentHealthProfile getHealthProfileByStudentId(int profileId) {
        return entityManager.find(StudentHealthProfile.class, profileId);
    }

    @Override
    public List<StudentHealthProfile> getAllHealthProfiles() {
        String sql = "select s from StudentHealthProfile s";
        return entityManager.createQuery(sql, StudentHealthProfile.class).getResultList();
    }

    @Override
    public StudentHealthProfile updateHealthProfile(StudentHealthProfileDTO dto) {
        String sql = "SELECT s FROM Student s WHERE s.FullName = :name";
        List<Student> listStudent = entityManager.createQuery(sql, Student.class).setParameter("name", dto.getFullName()).getResultList();

        if(listStudent.isEmpty()){
            throw new RuntimeException("Student not found");
        }
        Student student = listStudent.get(0); // lấy ra 1tk student
        List<StudentHealthProfile> existing = entityManager.createQuery(
                        "SELECT s FROM StudentHealthProfile s WHERE s.StudentID = :id", StudentHealthProfile.class)
                .setParameter("id", student.getStudentID())
                .getResultList();
        if(!existing.isEmpty()){
            StudentHealthProfile profile = existing.get(0); //lấy tk  vừa tìm thấy
            profile.setStudentID(student.getStudentID());
            profile.setParentID(student.getParent().getParentID());
            profile.setAllergyDetails(dto.getAllergyDetails());
            profile.setChronicDiseases(dto.getChronicDiseases());
            profile.setTreatmentHistory(dto.getTreatmentHistory());
            profile.setVisionLeft(dto.getVisionLeft());
            profile.setVisionRight(dto.getVisionRight());
            profile.setHearingScore(dto.getHearingScore());
            profile.setHeight(dto.getHeight());
            profile.setWeight(dto.getWeight());
            profile.setVaccines(dto.getVaccines());
            profile.setNoteOfParent(dto.getNoteOfParent());
            profile.setLastUpdated(new Date());
            entityManager.merge(profile);
            return profile;
        }else {
            throw new RuntimeException("Student health profile not found");
        }
    }

    @Override
    public void deleteHealthProfile(int studentId) {
        entityManager.remove(entityManager.find(StudentHealthProfile.class, studentId));
    }
    @Override
    public StudentHealthProfile AddHealthProfile(StudentHealthProfileDTO dto) {
        List<Student> students = entityManager.createQuery(
                        "SELECT s FROM Student s WHERE s.FullName = :name", Student.class)
                .setParameter("name", dto.getFullName())
                .getResultList();
        if (students.isEmpty()) {
            throw new RuntimeException("Student doesn't exits " + dto.getFullName());
        }
        Student student = students.get(0);// lấy tk student đầu tiên ra
        List<StudentHealthProfile> existing = entityManager.createQuery(
                        "SELECT s FROM StudentHealthProfile s WHERE s.StudentID = :id", StudentHealthProfile.class)
                .setParameter("id", student.getStudentID())
                .getResultList();
        if (!existing.isEmpty()) {
            throw new RuntimeException("StudentHealthProfile already exists .");
        } else {
            StudentHealthProfile profile = new StudentHealthProfile();
            profile.setStudentID(student.getStudentID());
            profile.setParentID(student.getParent().getParentID());
            profile.setAllergyDetails(dto.getAllergyDetails());
            profile.setChronicDiseases(dto.getChronicDiseases());
            profile.setTreatmentHistory(dto.getTreatmentHistory());
            profile.setVisionLeft(dto.getVisionLeft());
            profile.setVisionRight(dto.getVisionRight());
            profile.setHearingScore(dto.getHearingScore());
            profile.setHeight(dto.getHeight());
            profile.setWeight(dto.getWeight());
            profile.setVaccines(dto.getVaccines());
            profile.setNoteOfParent(dto.getNoteOfParent());
            profile.setLastUpdated(new Date());
            entityManager.persist(profile);
            return profile;

        }
    }

    @Override
    public StudentHealthProfile getHealthProfileByStudentName(String studentName) {
        String jpql = "SELECT s FROM StudentHealthProfile s JOIN Student st ON s.StudentID = st.StudentID WHERE st.FullName = :studentName";
        try {
            return entityManager.createQuery(jpql, StudentHealthProfile.class)
                    .setParameter("studentName", studentName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // hoặc throw custom exception nếu cần
        }
    }

    @Override
    public StudentHealthProfile getStudentHealthProfileByStudentId(int studentId) {
        String sql = "SELECT s FROM StudentHealthProfile s WHERE s.StudentID = :studentId";
        return entityManager.createQuery(sql, StudentHealthProfile.class).getSingleResult();
    }
}

