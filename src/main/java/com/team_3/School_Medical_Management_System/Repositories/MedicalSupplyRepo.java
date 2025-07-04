package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicalSupplyRepoInterFace;
import com.team_3.School_Medical_Management_System.Model.MedicalSupply;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class MedicalSupplyRepo implements MedicalSupplyRepoInterFace {

    private EntityManager entityManager;
    @Autowired
    public MedicalSupplyRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<MedicalSupply> getAllMedicalSupply() {
       String sql = "select ms from MedicalSupply ms";
       return entityManager.createQuery(sql, MedicalSupply.class).getResultList();
    }



    @Override
    public MedicalSupply addMedicalSupply(MedicalSupply ms) {
        entityManager.persist(ms);
        return ms;
    }

    @Override
    public MedicalSupply updateMedicalSupply(MedicalSupply ms) {
        MedicalSupply p = entityManager.find(MedicalSupply.class, ms.getMedicalSupplyID());
        if(p != null) {
            p.setMedicalSupplyID(ms.getMedicalSupplyID());
            p.setSupplyName(ms.getSupplyName());
            p.setQuantityAvailable(ms.getQuantityAvailable());
            p.setUnit(ms.getUnit());
            p.setReorderLevel(ms.getReorderLevel());
            p.setDateAdded(ms.getDateAdded());
            p.setHealthCheck(ms.getHealthCheck());
            p.setVaccineType(ms.getVaccineType());
            entityManager.merge(p);
            return p;
        }else {
            throw new IllegalArgumentException("Medical Supply not found");
        }
    }
}
