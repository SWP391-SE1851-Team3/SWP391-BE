package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ConfirmMedicationSubmissionInterFace;
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
public abstract class ConfirmMedicationSubmissionRepo implements ConfirmMedicationSubmissionInterFace {

    private final EntityManager entityManager;

    @Autowired
    public ConfirmMedicationSubmissionRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<ConfirmMedicationSubmission> findByMedicationSubmissionId(int medicationSubmissionId) {
        try {
            String jpql = "SELECT c FROM ConfirmMedicationSubmission c WHERE c.medicationSubmissionId = :medicationSubmissionId";
            ConfirmMedicationSubmission result = entityManager.createQuery(jpql, ConfirmMedicationSubmission.class)
                    .setParameter("medicationSubmissionId", medicationSubmissionId)
                    .getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ConfirmMedicationSubmission> findByNurseId(int nurseId) {
        String jpql = "SELECT c FROM ConfirmMedicationSubmission c WHERE c.nurseId = :nurseId";
        return entityManager.createQuery(jpql, ConfirmMedicationSubmission.class)
                .setParameter("nurseId", nurseId)
                .getResultList();
    }

    @Override
    public List<ConfirmMedicationSubmission> findByStatus(boolean status) {
        String jpql = "SELECT c FROM ConfirmMedicationSubmission c WHERE c.status = :status";
        return entityManager.createQuery(jpql, ConfirmMedicationSubmission.class)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    public List<ConfirmMedicationSubmission> findByReceivedMedicine(boolean receivedMedicine) {
        String jpql = "SELECT c FROM ConfirmMedicationSubmission c WHERE c.receivedMedicine = :receivedMedicine";
        return entityManager.createQuery(jpql, ConfirmMedicationSubmission.class)
                .setParameter("receivedMedicine", receivedMedicine)
                .getResultList();
    }


}
