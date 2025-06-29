package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.HealthCheckStudentRepository;
import com.team_3.School_Medical_Management_System.Model.HealthCheck_Student;
import com.team_3.School_Medical_Management_System.Model.HealthCheck_Schedule;
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
public class HealthCheckStudentRepo implements HealthCheckStudentRepository {

    private final EntityManager entityManager;

    @Autowired
    public HealthCheckStudentRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<HealthCheck_Student> findByStudent_StudentID(int studentID) {
        String jpql = "SELECT h FROM HealthCheck_Student h WHERE h.studentID = :studentID";
        return entityManager.createQuery(jpql, HealthCheck_Student.class)
                .setParameter("studentID", studentID)
                .getResultList();
    }

    @Override
    public void flush() {
        entityManager.flush();
    }

    @Override
    public <S extends HealthCheck_Student> S saveAndFlush(S entity) {
        S result = save(entity);
        flush();
        return result;
    }

    @Override
    public <S extends HealthCheck_Student> List<S> saveAllAndFlush(Iterable<S> entities) {
        List<S> result = saveAll(entities);
        flush();
        return result;
    }

    @Override
    public void deleteAllInBatch(Iterable<HealthCheck_Student> entities) {
        entities.forEach(entityManager::remove);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> ids) {
        String jpql = "DELETE FROM HealthCheck_Student h WHERE h.id IN :ids";
        entityManager.createQuery(jpql)
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public void deleteAllInBatch() {
        entityManager.createQuery("DELETE FROM HealthCheck_Student").executeUpdate();
    }

    @Override
    public HealthCheck_Student getOne(Integer id) {
        return getById(id);
    }

    @Override
    public HealthCheck_Student getById(Integer id) {
        return entityManager.find(HealthCheck_Student.class, id);
    }

    @Override
    public HealthCheck_Student getReferenceById(Integer id) {
        return entityManager.getReference(HealthCheck_Student.class, id);
    }

    @Override
    public <S extends HealthCheck_Student> Optional<S> findOne(Example<S> example) {
        try {
            List<S> results = findAll(example);
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public <S extends HealthCheck_Student> List<S> findAll(Example<S> example) {
        // Basic implementation - in real world would need more complex query building
        return (List<S>) findAll();
    }

    @Override
    public <S extends HealthCheck_Student> List<S> findAll(Example<S> example, Sort sort) {
        // Basic implementation - in real world would need more complex query building
        return (List<S>) findAll(sort);
    }

    @Override
    public <S extends HealthCheck_Student> Page<S> findAll(Example<S> example, Pageable pageable) {
        // Basic implementation - in real world would need more complex query building
        return (Page<S>) findAll(pageable);
    }

    @Override
    public <S extends HealthCheck_Student> long count(Example<S> example) {
        return findAll(example).size();
    }

    @Override
    public <S extends HealthCheck_Student> boolean exists(Example<S> example) {
        return !findAll(example).isEmpty();
    }

    @Override
    public <S extends HealthCheck_Student, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        // Implementation would need a custom FluentQuery implementation
        throw new UnsupportedOperationException("findBy with queryFunction not implemented");
    }

    @Override
    public <S extends HealthCheck_Student> S save(S entity) {
        if (entity.getCheckID() == 0) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    @Override
    public <S extends HealthCheck_Student> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::save);
        return (List<S>) entities;
    }

    @Override
    public Optional<HealthCheck_Student> findById(Integer id) {
        HealthCheck_Student entity = entityManager.find(HealthCheck_Student.class, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public boolean existsById(Integer id) {
        return findById(id).isPresent();
    }

    @Override
    public List<HealthCheck_Student> findAll() {
        return entityManager.createQuery("SELECT h FROM HealthCheck_Student h", HealthCheck_Student.class)
                .getResultList();
    }

    @Override
    public List<HealthCheck_Student> findAll(Sort sort) {
        // Basic implementation - sorting would require more complex query building
        return findAll();
    }

    @Override
    public Page<HealthCheck_Student> findAll(Pageable pageable) {
        // Basic implementation - paging would require more complex query building
        throw new UnsupportedOperationException("Paging not implemented");
    }

    @Override
    public List<HealthCheck_Student> findAllById(Iterable<Integer> ids) {
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

        String jpql = "SELECT h FROM HealthCheck_Student h WHERE h.checkID IN (" + idString + ")";
        return entityManager.createQuery(jpql, HealthCheck_Student.class).getResultList();
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(h) FROM HealthCheck_Student h", Long.class)
                .getSingleResult();
    }

    @Override
    public void deleteById(Integer id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @Override
    public void delete(HealthCheck_Student entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public void deleteAll(Iterable<? extends HealthCheck_Student> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM HealthCheck_Student").executeUpdate();
    }

    @Override
    public Integer findMaxCheckIdByStudentId(int studentId) {
        String jpql = "SELECT MAX(h.checkID) FROM HealthCheck_Student h WHERE h.studentID = :studentId";
        try {
            return entityManager.createQuery(jpql, Integer.class)
                    .setParameter("studentId", studentId)
                    .getSingleResult();
        } catch (Exception e) {
            // Return null if no results found or any other error
            return null;
        }
    }
}
