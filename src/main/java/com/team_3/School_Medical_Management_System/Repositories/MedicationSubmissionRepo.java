package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicationSubmissionInterFace;
import com.team_3.School_Medical_Management_System.Model.MedicationDetail;
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
public class MedicationSubmissionRepo implements MedicationSubmissionInterFace {

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



    public List<MedicationSubmission> findAllSubmissions() {
        return entityManager.createQuery("SELECT m FROM MedicationSubmission m", MedicationSubmission.class).getResultList();
    }




// Required methods from JpaRepository interface

    @Override
    public <S extends MedicationSubmission> S save(S entity) {
        if (entity.getMedicationSubmissionId() == 0) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }


    @Override
    public Optional<MedicationSubmission> findById(Integer id) {
        MedicationSubmission found = entityManager.find(MedicationSubmission.class, id);
        return Optional.ofNullable(found);
    }

    @Override
    public boolean existsById(Integer id) {
        return findById(id).isPresent();
    }

    @Override
    public List<MedicationSubmission> findAll() {
        return entityManager.createQuery("SELECT m FROM MedicationSubmission m", MedicationSubmission.class).getResultList();
    }

    @Override
    public List<MedicationSubmission> findAllById(Iterable<Integer> ids) {
        StringBuilder queryBuilder = new StringBuilder("SELECT m FROM MedicationSubmission m WHERE m.medicationSubmissionId IN (");
        boolean first = true;
        for (Integer id : ids) {
            if (!first) {
                queryBuilder.append(",");
            }
            queryBuilder.append(id);
            first = false;
        }
        queryBuilder.append(")");
        return entityManager.createQuery(queryBuilder.toString(), MedicationSubmission.class).getResultList();
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(m) FROM MedicationSubmission m", Long.class).getSingleResult();
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        MedicationSubmission submission = entityManager.find(MedicationSubmission.class, id);
        if (submission != null) {
            entityManager.remove(submission);
        }
    }

    @Override
    @Transactional
    public void delete(MedicationSubmission entity) {
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
    public void deleteAll(Iterable<? extends MedicationSubmission> entities) {
        for (MedicationSubmission entity : entities) {
            delete(entity);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM MedicationSubmission").executeUpdate();
    }

    @Override
    @Transactional
    public <S extends MedicationSubmission> List<S> saveAll(Iterable<S> entities) {
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
    public <S extends MedicationSubmission> S saveAndFlush(S entity) {
        S result = save(entity);
        flush();
        return result;
    }

    @Override
    @Transactional
    public <S extends MedicationSubmission> List<S> saveAllAndFlush(Iterable<S> entities) {
        List<S> result = saveAll(entities);
        flush();
        return result;
    }

    @Override
    @Transactional
    public void deleteAllInBatch(Iterable<MedicationSubmission> entities) {
        StringBuilder queryBuilder = new StringBuilder("DELETE FROM MedicationSubmission m WHERE m.medicationSubmissionId IN (");
        boolean first = true;
        for (MedicationSubmission entity : entities) {
            if (!first) {
                queryBuilder.append(",");
            }
            queryBuilder.append(entity.getMedicationSubmissionId());
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
        StringBuilder queryBuilder = new StringBuilder("DELETE FROM MedicationSubmission m WHERE m.medicationSubmissionId IN (");
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
        entityManager.createQuery("DELETE FROM MedicationSubmission").executeUpdate();
    }

    @Override
    public MedicationSubmission getOne(Integer id) {
        return entityManager.getReference(MedicationSubmission.class, id);
    }

    @Override
    public MedicationSubmission getById(Integer id) {
        return entityManager.find(MedicationSubmission.class, id);
    }

    @Override
    public MedicationSubmission getReferenceById(Integer id) {
        return entityManager.getReference(MedicationSubmission.class, id);
    }

    @Override
    public <S extends MedicationSubmission> Optional<S> findOne(Example<S> example) {
        // Basic implementation - this could be enhanced for more complex examples
        try {
            String jpql = "SELECT m FROM MedicationSubmission m WHERE 1=1";
            // Add conditions based on the example (this is a simplified approach)
            if (example.getProbe().getMedicationSubmissionId() != 0) {
                jpql += " AND m.medicationSubmissionId = :id";
            }
            TypedQuery<S> query = (TypedQuery<S>) entityManager.createQuery(jpql, MedicationSubmission.class);
            if (example.getProbe().getMedicationSubmissionId() != 0) {
                query.setParameter("id", example.getProbe().getMedicationSubmissionId());
            }
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public <S extends MedicationSubmission> List<S> findAll(Example<S> example) {
        // Basic implementation - would need to be enhanced for real use
        return (List<S>) findAll();
    }

    @Override
    public <S extends MedicationSubmission> List<S> findAll(Example<S> example, Sort sort) {
        // Basic implementation - would need to be enhanced for real use
        return (List<S>) findAll(sort);
    }

    @Override
    public <S extends MedicationSubmission> Page<S> findAll(Example<S> example, Pageable pageable) {
        // Basic implementation
        throw new UnsupportedOperationException("findAll with Example and Pageable not implemented");
    }

    @Override
    public <S extends MedicationSubmission> long count(Example<S> example) {
        // Basic implementation - would need to be enhanced for real use
        return count();
    }

    @Override
    public <S extends MedicationSubmission> boolean exists(Example<S> example) {
        // Basic implementation - would need to be enhanced for real use
        return !findAll(example).isEmpty();
    }

    @Override
    public <S extends MedicationSubmission, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        // This method is complex to implement correctly with EntityManager
        throw new UnsupportedOperationException("findBy with Example and Function not implemented");
    }

    @Override
    public List<MedicationSubmission> findAll(Sort sort) {
        String jpql = "SELECT m FROM MedicationSubmission m";
        // Add ordering based on Sort object
        if (sort != null && sort.isSorted()) {
            jpql += " ORDER BY";
            boolean first = true;
            for (Sort.Order order : sort) {
                if (!first) {
                    jpql += ",";
                }
                jpql += " m." + order.getProperty() + " " + order.getDirection().name();
                first = false;
            }
        }
        return entityManager.createQuery(jpql, MedicationSubmission.class).getResultList();
    }

    @Override
    public Page<MedicationSubmission> findAll(Pageable pageable) {
        String countQuery = "SELECT COUNT(m) FROM MedicationSubmission m";
        long total = entityManager.createQuery(countQuery, Long.class).getSingleResult();

        String jpql = "SELECT m FROM MedicationSubmission m";
        // Add ordering based on Sort object
        if (pageable.getSort().isSorted()) {
            jpql += " ORDER BY";
            boolean first = true;
            for (Sort.Order order : pageable.getSort()) {
                if (!first) {
                    jpql += ",";
                }
                jpql += " m." + order.getProperty() + " " + order.getDirection().name();
                first = false;
            }
        }

        List<MedicationSubmission> content = entityManager.createQuery(jpql, MedicationSubmission.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new org.springframework.data.domain.PageImpl<>(content, pageable, total);
    }

    @Override
    public List<MedicationSubmission> findByStudentIdIn(List<Integer> studentIds) {
        if (studentIds == null || studentIds.isEmpty()) return List.of();
        String jpql = "SELECT m FROM MedicationSubmission m WHERE m.studentId IN :ids";
        return entityManager.createQuery(jpql, MedicationSubmission.class)
                .setParameter("ids", studentIds)
                .getResultList();
    }
}
