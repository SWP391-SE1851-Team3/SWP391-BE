package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicationSubmissionInterFace;
import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@Transactional
public abstract class MedicationSubmissionRepo implements MedicationSubmissionInterFace {

    private final EntityManager entityManager;

    @Autowired
    public MedicationSubmissionRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<MedicationSubmission> findByParentId(int parentId) {
        String jpql = "SELECT m FROM MedicationSubmission m WHERE m.parentId = :parentId";
        return entityManager.createQuery(jpql, MedicationSubmission.class)
                .setParameter("parentId", parentId)
                .getResultList();
    }

    @Override
    public List<MedicationSubmission> findByStatus(MedicationSubmission.SubmissionStatus status) {
        String jpql = "SELECT m FROM MedicationSubmission m WHERE m.status = :status";
        return entityManager.createQuery(jpql, MedicationSubmission.class)
                .setParameter("status", status)
                .getResultList();
    }

    public List<MedicationSubmission> findAllSubmissions() {
        return entityManager.createQuery("SELECT m FROM MedicationSubmission m", MedicationSubmission.class).getResultList();
    }

    @Transactional
    public MedicationSubmission createSubmission(MedicationSubmission submission) {
        submission.setMedicationSubmissionDate(LocalDateTime.now());
        submission.setStatus(MedicationSubmission.SubmissionStatus.PENDING);
        entityManager.persist(submission);
        return submission;
    }


}
