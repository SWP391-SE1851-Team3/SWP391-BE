package com.team_3.School_Medical_Management_System.Repositories;


import com.team_3.School_Medical_Management_System.DTO.Consent_form_dot;
import com.team_3.School_Medical_Management_System.DTO.VaccineTypeShortDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.Consent_formsInterFace;
import com.team_3.School_Medical_Management_System.Model.Consent_forms;
import com.team_3.School_Medical_Management_System.Model.Vaccine_Batches;
import com.team_3.School_Medical_Management_System.Model.Vaccine_Types;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
        String jpql = "SELECT c FROM Consent_forms c WHERE c.student.className = :class_name";
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
    public List<Consent_forms> getConsentByStudentId(int studentId) {
        String jpql = "SELECT C FROM Consent_forms C WHERE C.student.StudentID   = :studentId";
        List<Consent_forms> results = entityManager.createQuery(jpql, Consent_forms.class)
                .setParameter("studentId", studentId)
                .getResultList();
        return results;
    }

    @Override
    public List<Consent_forms> findPendingForParent(int parentId) {
        String jpql = "SELECT c FROM Consent_forms c " +
                "JOIN FETCH c.student s " +
                "WHERE c.status = :status AND c.parent.ParentID = :parentId";
        return entityManager.createQuery(jpql, Consent_forms.class)
                .setParameter("status", "Ðang Chờ Phản Hồi")
                .setParameter("parentId", parentId)
                .getResultList();
    }

    @Override
    public List<Consent_forms> getAllConsentForms() {
        String sql = "SELECT c FROM Consent_forms c";
        return entityManager.createQuery(sql, Consent_forms.class).getResultList();
    }

    @Override
    public Consent_forms updateConsent(Consent_forms consent_forms) {
        var updateConsent = getConsent_formsById(consent_forms.getConsent_id());
        if(updateConsent != null) {
            Vaccine_Types vaccineTypes = new Vaccine_Types();
            vaccineTypes.setVaccineTypeID(consent_forms.getVaccineBatches().getVaccineType().getVaccineTypeID());
            Vaccine_Batches vaccineBatches = new Vaccine_Batches();
            vaccineBatches.setVaccineType(vaccineTypes);
            updateConsent.setVaccineBatches(vaccineBatches);
            updateConsent.setSend_date(consent_forms.getSend_date());
            updateConsent.setExpire_date(consent_forms.getExpire_date());
            return updateConsent;
        }else {
            return null;
        }
    }

    @Override
    public List<Consent_forms> getStudentConsentForms(String class_name) {
        String jpql = """
        SELECT c
        FROM Consent_forms c
        JOIN c.student s
        WHERE s.className = :class_name
    """;
        return entityManager.createQuery(jpql, Consent_forms.class)
                .setParameter("class_name", class_name)
                .getResultList();
    }

    @Override
    public List<Consent_form_dot> findDot() {
        String jpql = "SELECT new com.team_3.School_Medical_Management_System.DTO.Consent_form_dot(c.consent_id, v.dot) " +
                "FROM Consent_forms c JOIN c.vaccineBatches v";
        return entityManager.createQuery(jpql, Consent_form_dot.class).getResultList();
    }

    public List<Consent_forms> getIsAgree() {
        String jpql = "SELECT c FROM Consent_forms c WHERE c.IsAgree = :isAgree";
        return entityManager.createQuery(jpql, Consent_forms.class)
                .setParameter("isAgree", "Đồng Ý")
                .getResultList();
    }

    @Override
    public List<Consent_forms> getDisAgree() {
        String jpql = "SELECT c FROM Consent_forms c WHERE c.IsAgree = :isAgree";
        return entityManager.createQuery(jpql, Consent_forms.class)
                .setParameter("isAgree", "Ko dong y")
                .getResultList();
    }

    @Override
    public List<Consent_forms> getStatus() {
        String jpql = "SELECT c FROM Consent_forms c WHERE c.status = :status";
        return entityManager.createQuery(jpql, Consent_forms.class)
                .setParameter("status", "ÐÃ PHÊ DUY?T")
                .getResultList();
    }

    @Override
    public List<Consent_forms> getDisStatus() {
        String jpql = "SELECT c FROM Consent_forms c WHERE c.status = :status";
        return entityManager.createQuery(jpql, Consent_forms.class)
                .setParameter("status", "Dang Cho Phan hoi")
                .getResultList();
    }

    @Override
    public void deleteConsentFormsByStudentId(int studentId) {
        String jpql = "DELETE FROM Consent_forms c WHERE c.student.StudentID = :studentId";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("studentId", studentId);
        query.executeUpdate();
    }

    @Override
    public void deleteByConsent_FormByParentID(int parentId) {
        String jpql = "DELETE FROM Consent_forms c WHERE c.parent.ParentID = :parentId";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("parentId", parentId);
        query.executeUpdate();
    }


}
