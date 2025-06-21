package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.DTO.Consent_formsDTO;
import com.team_3.School_Medical_Management_System.Enum.ConsentFormStatus;
import com.team_3.School_Medical_Management_System.InterfaceRepo.Consent_formsInterFace;
import com.team_3.School_Medical_Management_System.Model.Consent_forms;
import com.team_3.School_Medical_Management_System.Model.StudentHealthProfile;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.text.Normalizer;
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
                "JOIN c.vaccineBatches v";
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
    public List<Consent_forms> getConsent_formsIsAgree(String dot) {
        String jpql = "SELECT DISTINCT c FROM Consent_forms c " +
                "JOIN c.vaccineBatches v " +
                "WHERE c.IsAgree = 'Đồng Ý' AND v.dot = :dot";
        return entityManager.createQuery(jpql, Consent_forms.class)
                .setParameter("dot", dot)
                .getResultList();
    }

    @Override
    public List<Consent_forms> getConsent_formsClass(String class_name) {
        String jpql = "SELECT c FROM Consent_forms c WHERE c.student.ClassName = :class_name";
        return entityManager.createQuery(jpql, Consent_forms.class).setParameter("class_name", class_name).getResultList();
    }

    @Override
    public Consent_forms getConsent_formsById(Integer consentFormId) {
        return entityManager.find(Consent_forms.class, consentFormId);
    }

    @Override
    public Consent_forms updateConsent_forms(Consent_forms consent_forms) {
        entityManager.merge(consent_forms);
        return consent_forms;
    }

    @Override
    public Long countConsentFormsIsAgreeByBatch(String dot) {
//        String jpql = "SELECT  COUNT(c) FROM Consent_forms c " +
//                "JOIN  c.vaccineBatches v " +
//                "WHERE c.IsAgree = 'Đồng Ý'  AND v.dot = :dot";
//        return entityManager.createQuery(jpql, Long.class)
//                .setParameter("dot", dot)
//                .getSingleResult();
        String sql = "SELECT COUNT(*) FROM Consent_forms c " +
                "JOIN Vaccine_Batches v ON c.BatchID = v.BatchID " +
                "WHERE c.IsAgree = ?1 AND v.dot = ?2";

        return ((Number) entityManager.createNativeQuery(sql)
                .setParameter(1, "Đồng Ý")
                .setParameter(2, dot.trim())
                .getSingleResult()).longValue();
    }

    @Override
    public Long countConsentFormsDisAgreeByBatch(String dot) {
//        String jpql = "SELECT  COUNT(c) FROM Consent_forms c " +
//                "JOIN  c.vaccineBatches v " +
//                "WHERE c.IsAgree = 'Không Đồng Ý' AND v.dot = :dot";
//        return entityManager.createQuery(jpql, Long.class)
//                .setParameter("dot", dot.trim())
//                .getSingleResult();

        String sql = "SELECT COUNT(*) FROM Consent_forms c " +
                "JOIN Vaccine_Batches v ON c.BatchID = v.BatchID " +
                "WHERE c.IsAgree = ?1 AND v.dot = ?2";

        return ((Number) entityManager.createNativeQuery(sql)
                .setParameter(1, "Không Đồng Ý")
                .setParameter(2, dot.trim())
                .getSingleResult()).longValue();
    }

    @Override
    public Long countConsentFormsPendingByBatch(String dot) {
        String sql = "SELECT COUNT(*) FROM Consent_forms c " +
                "JOIN Vaccine_Batches v ON c.BatchID = v.BatchID " +
                "WHERE c.IsAgree = ?1 AND v.dot = ?2";

        return ((Number) entityManager.createNativeQuery(sql)
                .setParameter(1, "Chờ phản hồi")
                .setParameter(2, dot.trim())
                .getSingleResult()).longValue();
    }


    @Override
    public Consent_forms getConsentByStudentId(int studentId) {
        String jpql = "SELECT C FROM Consent_forms C WHERE C.student.StudentID   = :studentId";
        List<Consent_forms> results = entityManager.createQuery(jpql, Consent_forms.class)
                .setParameter("studentId", studentId)
                .getResultList();
        return results.stream().findFirst().orElse(null);
    }

    @Override
    public List<Consent_forms> findPendingForParent() {
        String jpql = "SELECT c FROM Consent_forms c WHERE c.status = :status";
        return entityManager.createQuery(jpql, Consent_forms.class)
                .setParameter("status", ConsentFormStatus.CREATED)
                .getResultList();
    }
}
