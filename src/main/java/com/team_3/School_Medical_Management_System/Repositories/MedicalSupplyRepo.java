package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicalSupplyRepository;
import com.team_3.School_Medical_Management_System.Model.MedicalSupply;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
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
    public void deleteByHealthCheckStudent_CheckID(int checkID) {
        String query = "DELETE FROM MedicalSupply ms WHERE ms.healthCheckStudent.checkID = :checkID";
        entityManager.getTransaction().begin();
        entityManager.createQuery(query)
                .setParameter("checkID", checkID)
                .executeUpdate();
        entityManager.getTransaction().commit();

    }
}
