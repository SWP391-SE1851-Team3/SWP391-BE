package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.HealthConsultationRepository;
import com.team_3.School_Medical_Management_System.Model.HealthConsultation;
import com.team_3.School_Medical_Management_System.Model.Student;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@Transactional
public class HealthConsultationRepo implements HealthConsultationRepository {

    private final EntityManager entityManager;

    @Autowired
    public HealthConsultationRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<HealthConsultation> findByStudent(Student student) {
        String jpql = "SELECT h FROM HealthConsultation h WHERE h.student = :student";
        return entityManager.createQuery(jpql, HealthConsultation.class)
                .setParameter("student", student)
                .getResultList();
    }

    @Override
    public List<HealthConsultation> findByStatus(boolean status) {
        String jpql = "SELECT h FROM HealthConsultation h WHERE h.status = :status";
        return entityManager.createQuery(jpql, HealthConsultation.class)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    public void flush() {
        entityManager.flush();
    }

    @Override
    public <S extends HealthConsultation> S saveAndFlush(S entity) {
        S result = save(entity);
        flush();
        return result;
    }

    @Override
    public <S extends HealthConsultation> List<S> saveAllAndFlush(Iterable<S> entities) {
        List<S> result = saveAll(entities);
        flush();
        return result;
    }

    @Override
    public void deleteAllInBatch(Iterable<HealthConsultation> entities) {
        entities.forEach(entityManager::remove);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> ids) {
        String jpql = "DELETE FROM HealthConsultation h WHERE h.id IN :ids";
        entityManager.createQuery(jpql)
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteAllInBatch() {
        entityManager.createQuery("DELETE FROM HealthConsultation").executeUpdate();
    }

    @Override
    public HealthConsultation getOne(Integer id) {
        return getById(id);
    }

    @Override
    public HealthConsultation getById(Integer id) {
        return entityManager.find(HealthConsultation.class, id);
    }

    @Override
    public HealthConsultation getReferenceById(Integer id) {
        return entityManager.getReference(HealthConsultation.class, id);
    }

    @Override
    public <S extends HealthConsultation> Optional<S> findOne(Example<S> example) {
        try {
            List<S> results = findAll(example);
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public <S extends HealthConsultation> List<S> findAll(Example<S> example) {
        // Basic implementation - in real world would need more complex query building
        return (List<S>) findAll();
    }

    @Override
    public <S extends HealthConsultation> List<S> findAll(Example<S> example, Sort sort) {
        // Basic implementation - in real world would need more complex query building
        return (List<S>) findAll(sort);
    }

    @Override
    public <S extends HealthConsultation> Page<S> findAll(Example<S> example, Pageable pageable) {
        // Basic implementation - in real world would need more complex query building
        return (Page<S>) findAll(pageable);
    }

    @Override
    public <S extends HealthConsultation> long count(Example<S> example) {
        return findAll(example).size();
    }

    @Override
    public <S extends HealthConsultation> boolean exists(Example<S> example) {
        return !findAll(example).isEmpty();
    }

    @Override
    public <S extends HealthConsultation, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        // Implementation would need a custom FluentQuery implementation
        throw new UnsupportedOperationException("findBy with queryFunction not implemented");
    }

    @Override
    public <S extends HealthConsultation> S save(S entity) {
        if (entity.getConsultID() == 0) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    @Override
    public <S extends HealthConsultation> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::save);
        return (List<S>) entities;
    }

    @Override
    public Optional<HealthConsultation> findById(Integer id) {
        HealthConsultation entity = entityManager.find(HealthConsultation.class, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public boolean existsById(Integer id) {
        return findById(id).isPresent();
    }

    @Override
    public List<HealthConsultation> findAll() {
        return entityManager.createQuery("SELECT h FROM HealthConsultation h", HealthConsultation.class)
                .getResultList();
    }

    @Override
    public List<HealthConsultation> findAll(Sort sort) {
        // Basic implementation - sorting would require more complex query building
        return findAll();
    }

    @Override
    public Page<HealthConsultation> findAll(Pageable pageable) {
        // Basic implementation - paging would require more complex query building
        throw new UnsupportedOperationException("Paging not implemented");
    }

    @Override
    public List<HealthConsultation> findAllById(Iterable<Integer> ids) {
        // Convert Iterable to List for JPQL IN clause
        StringBuilder idList = new StringBuilder();
        ids.forEach(id -> idList.append(id).append(","));
        String idString = idList.toString();
        if (idString.endsWith(",")) {
            idString = idString.substring(0, idString.length() - 1);
        }

        if (idString.isEmpty()) {
            return List.of();
        }

        String jpql = "SELECT h FROM HealthConsultation h WHERE h.id IN (" + idString + ")";
        return entityManager.createQuery(jpql, HealthConsultation.class).getResultList();
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(h) FROM HealthConsultation h", Long.class)
                .getSingleResult();
    }

    @Override
    public void deleteById(Integer id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @Override
    public void delete(HealthConsultation entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public void deleteAll(Iterable<? extends HealthConsultation> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM HealthConsultation").executeUpdate();
    }
}
