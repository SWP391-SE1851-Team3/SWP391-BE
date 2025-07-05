package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicalSupplyRepository;
import com.team_3.School_Medical_Management_System.Model.MedicalSupply;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicalSupplyRepoInterFace;
import com.team_3.School_Medical_Management_System.Model.MedicalSupply;
import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class MedicalSupplyRepo implements MedicalSupplyRepository {
    private EntityManager entityManager;

    @Autowired
    public MedicalSupplyRepo(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public List<MedicalSupply> findByQuantityAvailableLessThanReorderLevel() {
        String query = "SELECT ms FROM MedicalSupply ms WHERE ms.quantityAvailable < ms.reorderLevel";
        return entityManager.createQuery(query, MedicalSupply.class).getResultList();
    }

    @Override
    public List<MedicalSupply> findByCategoryCategoryId(Integer categoryId) {
        String query = "SELECT ms FROM MedicalSupply ms WHERE ms.category.categoryID = :categoryId";
        return entityManager.createQuery(query, MedicalSupply.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public List<MedicalSupply> findAll() {
        String query = "SELECT ms FROM MedicalSupply ms";
        return entityManager.createQuery(query, MedicalSupply.class).getResultList();
    }

    @Override
    public MedicalSupply findById(Integer id) {
        String query = "SELECT ms FROM MedicalSupply ms WHERE ms.id = :id";
        return entityManager.createQuery(query, MedicalSupply.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void save(MedicalSupply m) {
        entityManager.getTransaction().begin();
        entityManager.merge(m); // Cập nhật MedicalSupply đã tồn tại

        entityManager.getTransaction().commit();

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

