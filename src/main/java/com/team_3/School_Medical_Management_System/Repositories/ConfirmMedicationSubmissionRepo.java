package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.ConfirmMedicationSubmissionInterFace;
import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;
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
public class ConfirmMedicationSubmissionRepo implements ConfirmMedicationSubmissionInterFace {

    private final EntityManager entityManager;

    @Autowired
    public ConfirmMedicationSubmissionRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<ConfirmMedicationSubmission> findByMedicationSubmissionId(int medicationSubmissionId) {
        try {
            String jpql = "SELECT c FROM Confirm_MedicationSubmission c WHERE c.medicationSubmissionId = :medicationSubmissionId";
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
        String jpql = "SELECT c FROM Confirm_MedicationSubmission c WHERE c.nurseId = :nurseId";
        return entityManager.createQuery(jpql, ConfirmMedicationSubmission.class)
                .setParameter("nurseId", nurseId)
                .getResultList();
    }

    @Override
    public List<ConfirmMedicationSubmission> findByStatus(ConfirmMedicationSubmission.confirmMedicationSubmissionStatus status) {
        String jpql = "SELECT c FROM Confirm_MedicationSubmission c WHERE c.status = :status";
        return entityManager.createQuery(jpql, ConfirmMedicationSubmission.class)
                .setParameter("status", status)
                .getResultList();
    }


    // Required methods from JpaRepository interface

    @Override
    public <S extends ConfirmMedicationSubmission> S save(S entity) {
        if (entity.getConfirmId() == 0) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    @Override
    public Optional<ConfirmMedicationSubmission> findById(Integer id) {
        ConfirmMedicationSubmission found = entityManager.find(ConfirmMedicationSubmission.class, id);
        return Optional.ofNullable(found);
    }

    @Override
    public boolean existsById(Integer id) {
        return findById(id).isPresent();
    }

    @Override
    public List<ConfirmMedicationSubmission> findAll() {
        return entityManager.createQuery("SELECT c FROM Confirm_MedicationSubmission c", ConfirmMedicationSubmission.class).getResultList();
    }

    @Override
    public List<ConfirmMedicationSubmission> findAllById(Iterable<Integer> ids) {
        StringBuilder queryBuilder = new StringBuilder("SELECT c FROM ConfirmMedicationSubmission c WHERE c.confirmId IN (");
        boolean first = true;
        for (Integer id : ids) {
            if (!first) {
                queryBuilder.append(",");
            }
            queryBuilder.append(id);
            first = false;
        }
        queryBuilder.append(")");
        return entityManager.createQuery(queryBuilder.toString(), ConfirmMedicationSubmission.class).getResultList();
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(c) FROM Confirm_MedicationSubmission c", Long.class).getSingleResult();
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        ConfirmMedicationSubmission confirmation = entityManager.find(ConfirmMedicationSubmission.class, id);
        if (confirmation != null) {
            entityManager.remove(confirmation);
        }
    }

    @Override
    @Transactional
    public void delete(ConfirmMedicationSubmission entity) {
        if (entityManager.contains(entity)) {
            entityManager.remove(entity);
        } else {
            entityManager.remove(entityManager.merge(entity));
        }
    }

    @Override
    @Transactional
    public void deleteAllById(Iterable<? extends Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<? extends ConfirmMedicationSubmission> entities) {
        for (ConfirmMedicationSubmission entity : entities) {
            delete(entity);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Confirm_MedicationSubmission").executeUpdate();
    }

    @Override
    @Transactional
    public <S extends ConfirmMedicationSubmission> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new java.util.ArrayList<>();
        for (S entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    @Override
    @Transactional
    public void flush() {
        entityManager.flush();
    }

    @Override
    @Transactional
    public <S extends ConfirmMedicationSubmission> S saveAndFlush(S entity) {
        S result = save(entity);
        flush();
        return result;
    }

    @Override
    @Transactional
    public <S extends ConfirmMedicationSubmission> List<S> saveAllAndFlush(Iterable<S> entities) {
        List<S> result = saveAll(entities);
        flush();
        return result;
    }

    @Override
    @Transactional
    public void deleteAllInBatch(Iterable<ConfirmMedicationSubmission> entities) {
        StringBuilder queryBuilder = new StringBuilder("DELETE FROM Confirm_MedicationSubmission c WHERE c.confirmId IN (");
        boolean first = true;
        for (ConfirmMedicationSubmission entity : entities) {
            if (!first) {
                queryBuilder.append(",");
            }
            queryBuilder.append(entity.getConfirmId());
            first = false;
        }
        queryBuilder.append(")");
        if (!first) { // only execute if there are entities
            entityManager.createQuery(queryBuilder.toString()).executeUpdate();
        }
    }

    @Override
    @Transactional
    public void deleteAllByIdInBatch(Iterable<Integer> ids) {
        StringBuilder queryBuilder = new StringBuilder("DELETE FROM Confirm_MedicationSubmission c WHERE c.confirmId IN (");
        boolean first = true;
        for (Integer id : ids) {
            if (!first) {
                queryBuilder.append(",");
            }
            queryBuilder.append(id);
            first = false;
        }
        queryBuilder.append(")");
        if (!first) { // only execute if there are ids
            entityManager.createQuery(queryBuilder.toString()).executeUpdate();
        }
    }

    @Override
    @Transactional
    public void deleteAllInBatch() {
        entityManager.createQuery("DELETE FROM Confirm_MedicationSubmission").executeUpdate();
    }

    @Override
    public ConfirmMedicationSubmission getOne(Integer id) {
        return entityManager.getReference(ConfirmMedicationSubmission.class, id);
    }

    @Override
    public ConfirmMedicationSubmission getById(Integer id) {
        return entityManager.find(ConfirmMedicationSubmission.class, id);
    }

    @Override
    public ConfirmMedicationSubmission getReferenceById(Integer id) {
        return entityManager.getReference(ConfirmMedicationSubmission.class, id);
    }

    @Override
    public <S extends ConfirmMedicationSubmission> Optional<S> findOne(Example<S> example) {
        // Basic implementation - this could be enhanced for more complex examples
        try {
            String jpql = "SELECT c FROM Confirm_MedicationSubmission c WHERE 1=1";
            // Add conditions based on the example (this is a simplified approach)
            if (example.getProbe().getConfirmId() != 0) {
                jpql += " AND c.confirmId = :id";
            }
            TypedQuery<S> query = (TypedQuery<S>) entityManager.createQuery(jpql, ConfirmMedicationSubmission.class);
            if (example.getProbe().getConfirmId() != 0) {
                query.setParameter("id", example.getProbe().getConfirmId());
            }
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public <S extends ConfirmMedicationSubmission> List<S> findAll(Example<S> example) {
        // Basic implementation - would need to be enhanced for real use
        return (List<S>) findAll();
    }

    @Override
    public <S extends ConfirmMedicationSubmission> List<S> findAll(Example<S> example, Sort sort) {
        // Basic implementation - would need to be enhanced for real use
        return (List<S>) findAll(sort);
    }

    @Override
    public <S extends ConfirmMedicationSubmission> Page<S> findAll(Example<S> example, Pageable pageable) {
        // Basic implementation
        throw new UnsupportedOperationException("findAll with Example and Pageable not implemented");
    }

    @Override
    public <S extends ConfirmMedicationSubmission> long count(Example<S> example) {
        // Basic implementation - would need to be enhanced for real use
        return count();
    }

    @Override
    public <S extends ConfirmMedicationSubmission> boolean exists(Example<S> example) {
        // Basic implementation - would need to be enhanced for real use
        return !findAll(example).isEmpty();
    }

    @Override
    public <S extends ConfirmMedicationSubmission, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        // This method is complex to implement correctly with EntityManager
        throw new UnsupportedOperationException("findBy with Example and Function not implemented");
    }

    @Override
    public List<ConfirmMedicationSubmission> findAll(Sort sort) {
        String jpql = "SELECT c FROM Confirm_MedicationSubmission c";
        // Add ordering based on Sort object
        if (sort != null && sort.isSorted()) {
            jpql += " ORDER BY";
            boolean first = true;
            for (Sort.Order order : sort) {
                if (!first) {
                    jpql += ",";
                }
                jpql += " c." + order.getProperty() + " " + order.getDirection().name();
                first = false;
            }
        }
        return entityManager.createQuery(jpql, ConfirmMedicationSubmission.class).getResultList();
    }

    @Override
    public Page<ConfirmMedicationSubmission> findAll(Pageable pageable) {
        String countQuery = "SELECT COUNT(c) FROM Confirm_MedicationSubmission c";
        long total = entityManager.createQuery(countQuery, Long.class).getSingleResult();

        String jpql = "SELECT c FROM Confirm_MedicationSubmission c";
        // Add ordering based on Sort object
        if (pageable.getSort().isSorted()) {
            jpql += " ORDER BY";
            boolean first = true;
            for (Sort.Order order : pageable.getSort()) {
                if (!first) {
                    jpql += ",";
                }
                jpql += " c." + order.getProperty() + " " + order.getDirection().name();
                first = false;
            }
        }

        List<ConfirmMedicationSubmission> content = entityManager.createQuery(jpql, ConfirmMedicationSubmission.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new org.springframework.data.domain.PageImpl<>(content, pageable, total);
    }

}
