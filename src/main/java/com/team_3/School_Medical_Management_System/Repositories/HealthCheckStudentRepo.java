package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.HealthCheckStudentRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HealthCheckStudentRepo implements HealthCheckStudentRepository {
    private EntityManager entityManager;

    @Autowired
    public HealthCheckStudentRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void deleteByStudentId(int studentId) {
        String jpql = "DELETE FROM HealthCheck_Student h WHERE h.studentID = :studentId";
        entityManager.createQuery(jpql).setParameter("studentId", studentId).executeUpdate();
    }
}
