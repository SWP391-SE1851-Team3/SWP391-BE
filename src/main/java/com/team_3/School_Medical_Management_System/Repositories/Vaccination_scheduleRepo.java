package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.Vaccination_scheduleInterFace;
import com.team_3.School_Medical_Management_System.Model.Vaccination_schedule;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Vaccination_scheduleRepo implements Vaccination_scheduleInterFace {
    private EntityManager entityManager;

    @Autowired
    public Vaccination_scheduleRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Vaccination_schedule vaccination_scheduleById(int id) {
        return entityManager.find(Vaccination_schedule.class, id);
    }

    @Override
    public List<Vaccination_schedule> vaccination_schedules() {
        String jpql = "SELECT v FROM Vaccination_schedule v " +
                "JOIN v.Vaccine vaccine " +
                "JOIN v.Nurse nurse";  // JOIN với bảng Vaccine và SchoolNurse

        return entityManager.createQuery(jpql, Vaccination_schedule.class)
                .getResultList();
    }

    @Override
    public Vaccination_schedule addVaccination_schedule(Vaccination_schedule vaccination_schedule) {
        entityManager.persist(vaccination_schedule);
        return vaccination_schedule;
    }


    @Override
    public Vaccination_schedule updateVaccination_schedule(Vaccination_schedule vaccination_schedule) {
        var p = vaccination_scheduleById(vaccination_schedule.getSchedule_id());
        if (p != null) {
            entityManager.merge(vaccination_schedule);
            return vaccination_schedule;
        }else {
            return null;
        }
    }


}
