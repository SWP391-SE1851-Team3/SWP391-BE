package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.Vaccine_batchesInterFace;
import com.team_3.School_Medical_Management_System.Model.Vaccine_batches;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Vaccine_batchesRepo implements Vaccine_batchesInterFace {
    private EntityManager entityManager;



    @Autowired
    public Vaccine_batchesRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Vaccine_batches> getAll() {
       String jpql = "SELECT v FROM Vaccine_batches v";
       return entityManager.createQuery(jpql, Vaccine_batches.class).getResultList();
    }

    @Override
    public Vaccine_batches getById(int id) {
        return entityManager.find(Vaccine_batches.class, id);
    }

    @Override
    public void add(Vaccine_batches batch) {
        entityManager.persist(batch);
    }

    @Override
    public void update(Vaccine_batches batch) {
        var vaccineId = entityManager.find(Vaccine_batches.class, batch.getVaccine().getVaccine_id());
        if(vaccineId == null) {
            throw new IllegalArgumentException("Vaccine_id not found");
        }
        var p = entityManager.find(Vaccine_batches.class, batch.getBatch_id());
        if(p != null) {
            p.setBatch_number(batch.getBatch_number());
            p.setVaccine(batch.getVaccine());
            p.setQuantity_received(batch.getQuantity_received());
            p.setReceived_date(batch.getReceived_date());
            entityManager.merge(p);
        }
    }

    @Override
    public Long countTotalBatch() {
        String jqpl = "SELECT COUNT(*) FROM Vaccine_batches";
        return entityManager.createQuery(jqpl, Long.class).getSingleResult();
    }


}
