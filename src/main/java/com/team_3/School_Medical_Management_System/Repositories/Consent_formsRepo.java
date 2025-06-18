package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.Consent_formsInterFace;
import com.team_3.School_Medical_Management_System.Model.Consent_forms;
import com.team_3.School_Medical_Management_System.Model.StudentHealthProfile;
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

    @Override
    public List<Consent_forms> getConsent_formsClass(String class_name) {
        String jpql = "SELECT c FROM Consent_forms c WHERE c.student.ClassName = :class_name";
        return entityManager.createQuery(jpql, Consent_forms.class).setParameter("class_name", class_name).getResultList();
    }

    @Override
    public Consent_forms getConsent_formsById(int consentFormId) {
        return entityManager.find(Consent_forms.class, consentFormId);
    }

    @Override
    public Consent_forms updateConsent_forms(Consent_forms consent_forms) {
        entityManager.merge(consent_forms);
        return consent_forms;
    }

    @Override
    public Long countConsentFormsIsAgreeByBatch(int batch_id) {
        String jpql = "SELECT  COUNT(c) FROM Consent_forms c " +
                "JOIN c.vaccine v " +
                "JOIN Vaccine_batches vb ON vb.vaccine = v " +
                "JOIN Vaccination_schedule vs ON vs.Vaccine = v " +
                "WHERE c.IsAgree = 1 AND vb.batch_id = :batch_id";
        return entityManager.createQuery(jpql, Long.class)
                .setParameter("batch_id", batch_id)
                .getSingleResult();
    }

    @Override
    public Long countConsentFormsDisAgreeByBatch(int batch_id) {
        String jpql = "SELECT  COUNT(c) FROM Consent_forms c " +
                "JOIN c.vaccine v " +
                "JOIN Vaccine_batches vb ON vb.vaccine = v " +
                "JOIN Vaccination_schedule vs ON vs.Vaccine = v " +
                "WHERE c.IsAgree = 0 AND vb.batch_id = :batch_id";
        return entityManager.createQuery(jpql, Long.class)
                .setParameter("batch_id", batch_id)
                .getSingleResult();
    }

    @Override
    public Long countConsentFormsPendingByBatch(int batch_id) {
        String jpql = "SELECT  COUNT(c) FROM Consent_forms c " +
                "JOIN c.vaccine v " +
                "JOIN Vaccine_batches vb ON vb.vaccine = v " +
                "JOIN Vaccination_schedule vs ON vs.Vaccine = v " +
                "WHERE c.IsAgree IS NULL AND vb.batch_id = :batch_id";
        return entityManager.createQuery(jpql, Long.class)
                .setParameter("batch_id", batch_id)
                .getSingleResult();
    }

    @Override
    public Consent_forms getConsentByStudentId(int studentId) {
        String jpql = "SELECT C FROM Consent_forms C WHERE C.student.StudentID   = :studentId";
        List<Consent_forms> results = entityManager.createQuery(jpql, Consent_forms.class)
                .setParameter("studentId", studentId)
                .getResultList();
        return results.stream().findFirst().orElse(null);
    }

}
