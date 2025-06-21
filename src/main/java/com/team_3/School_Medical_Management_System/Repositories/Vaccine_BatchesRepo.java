package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.Vaccine_BatchesInterFace;
import com.team_3.School_Medical_Management_System.Model.Vaccine_Batches;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Vaccine_BatchesRepo implements Vaccine_BatchesInterFace {
    private EntityManager entityManager;
    @Autowired
    private SchoolNurseRepo schoolNurseRepo;

    @Autowired
    public Vaccine_BatchesRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Vaccine_Batches> GetAllVaccinesbatch() {
        String sql = "SELECT v FROM Vaccine_Batches v";
        return entityManager.createQuery(sql, Vaccine_Batches.class).getResultList();
    }

    @Override
    public Vaccine_Batches GetVaccineByVaccineName(String vaccineName) {
        String jpql = "SELECT v FROM Vaccine_Batches v WHERE v.vaccineType.Name LIKE :vaccineName";
        return entityManager.createQuery(jpql, Vaccine_Batches.class)
                .setParameter("vaccineName", "%" + vaccineName + "%")
                .getSingleResult();
    }

    @Override
    public Vaccine_Batches GetVaccineByVaccineId(int VaccineId) {
        return entityManager.find(Vaccine_Batches.class, VaccineId);
    }

    @Override
    public Vaccine_Batches AddVaccine_batch(Vaccine_Batches vaccine) {
         entityManager.persist(vaccine);
         return vaccine;
    }

    @Override
    public Vaccine_Batches UpdateVaccine_batch(Vaccine_Batches vaccineDetails) {
        var existingVaccine = GetVaccineByVaccineId(vaccineDetails.getBatchID());
        if (existingVaccine == null) {
            return null;
        }
        existingVaccine.setBatchID(vaccineDetails.getBatchID());
        existingVaccine.setVaccineType(vaccineDetails.getVaccineType());
        existingVaccine.setCreated_at(vaccineDetails.getCreated_at());
        existingVaccine.setUpdated_at(vaccineDetails.getUpdated_at());
        existingVaccine.setScheduled_date(vaccineDetails.getScheduled_date());
        existingVaccine.setQuantity_received(vaccineDetails.getQuantity_received());
        existingVaccine.setNurse(vaccineDetails.getNurse());
        existingVaccine.setLocation(vaccineDetails.getLocation());
        existingVaccine.setNotes(vaccineDetails.getNotes());
        existingVaccine.setStatus(vaccineDetails.getStatus());
        return entityManager.merge(existingVaccine);
    }
}
