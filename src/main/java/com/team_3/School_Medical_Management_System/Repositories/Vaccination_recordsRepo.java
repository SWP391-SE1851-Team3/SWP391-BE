package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.Vaccination_recordsInterFace;
import com.team_3.School_Medical_Management_System.Model.StudentHealthProfile;
import com.team_3.School_Medical_Management_System.Model.Vaccination_records;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Vaccination_recordsRepo implements Vaccination_recordsInterFace {
    private EntityManager entityManager;
    @Autowired
    public Vaccination_recordsRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Vaccination_records> getVaccination_records() {
        try {
            String query = "SELECT r FROM Vaccination_records r";
            return entityManager.createQuery(query, Vaccination_records.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Vaccination_records addVaccination_records(Vaccination_records vaccination_records) {
        try {
            entityManager.persist(vaccination_records);
            return vaccination_records;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteVaccination_records(int id) {
        var query = getVaccination_records_by_id(id);
        if(query != null) {
            entityManager.remove(query);
        }else{
            System.out.println("Vaccination_records not found");
        }

    }

    @Override
    public Vaccination_records getVaccination_records_by_id(int id) {
      return entityManager.find(Vaccination_records.class, id);
    }

    @Override
    public Vaccination_records updateVaccination_records(Vaccination_records vaccination_records) {
       entityManager.merge(vaccination_records);
       return vaccination_records;
    }

    @Override
    public List<Vaccination_records> getVaccination_recordsByStudentId(int studentId) {
        String sql = "SELECT s FROM Vaccination_records s WHERE s.student.StudentID = :studentId";
        return entityManager.createQuery(sql, Vaccination_records.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }


}
