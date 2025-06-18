package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.Consent_formsInterFace;
import com.team_3.School_Medical_Management_System.Model.Consent_forms;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class Consent_formsRepo implements Consent_formsInterFace {
    private EntityManager entityManager;
    @Autowired
    public Consent_formsRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Consent_forms> getConsent_forms() {
        String JPQL = "SELECT c FROM Consent_forms c " +
                "JOIN c.schedule s " +
                "JOIN c.vaccine v";
        return entityManager.createQuery(JPQL, Consent_forms.class).getResultList();
    }

    @Override
    public Consent_forms addConsent_forms(Consent_forms consent_forms) {
        entityManager.persist(consent_forms);
        return consent_forms;
    }

    @Override
    public List<Consent_forms> getConsent_formsByParentName(String fullName) {
        String jpql = "SELECT c FROM Consent_forms c WHERE c.parent.FullName = :fullName";
        return entityManager.createQuery(jpql, Consent_forms.class)
                .setParameter("fullName", fullName)
                .getResultList();
    }

    @Override
    public List<Consent_forms> getConsent_formsIsAgree(int batch_id) {
        String jpql = "SELECT DISTINCT c FROM Consent_forms c " +
                "JOIN c.vaccine v " +
                "JOIN Vaccine_batches vb ON vb.vaccine = v " +
                "JOIN Vaccination_schedule vs ON vs.Vaccine = v " +
                "WHERE c.IsAgree = 1 AND vb.batch_id = :batch_id";
        return entityManager.createQuery(jpql, Consent_forms.class)
                .setParameter("batch_id", batch_id)
                .getResultList();
    }
}
