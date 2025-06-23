package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.HealthConsentForm;
import com.team_3.School_Medical_Management_System.Model.HealthCheck_Schedule;
import com.team_3.School_Medical_Management_System.Model.Student;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class HealthConsentFormRepo implements com.team_3.School_Medical_Management_System.InterfaceRepo.HealthConsentFormRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final SimpleJpaRepository<HealthConsentForm, Integer> repository;

    public HealthConsentFormRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.repository = new SimpleJpaRepository<>(HealthConsentForm.class, entityManager);
    }

    // Các phương thức tùy chỉnh từ interface
    @Override
    public List<HealthConsentForm> findByStudent(Student student) {
        TypedQuery<HealthConsentForm> query = entityManager.createQuery(
                "SELECT h FROM HealthConsentForm h WHERE h.student = :student",
                HealthConsentForm.class
        );
        query.setParameter("student", student);
        return query.getResultList();
    }

    @Override
    public List<HealthConsentForm> findByHealthCheckSchedule(HealthCheck_Schedule healthCheckSchedule) {
        TypedQuery<HealthConsentForm> query = entityManager.createQuery(
                "SELECT h FROM HealthConsentForm h WHERE h.healthCheckSchedule = :healthCheckSchedule",
                HealthConsentForm.class
        );
        query.setParameter("healthCheckSchedule", healthCheckSchedule);
        return query.getResultList();
    }

    @Override
    public List<HealthConsentForm> findByHealthCheckScheduleAndIsAgreed(HealthCheck_Schedule healthCheckSchedule, Boolean isAgreed) {
        TypedQuery<HealthConsentForm> query = entityManager.createQuery(
                "SELECT h FROM HealthConsentForm h WHERE h.healthCheckSchedule = :healthCheckSchedule AND h.isAgreed = :isAgreed",
                HealthConsentForm.class
        );
        query.setParameter("healthCheckSchedule", healthCheckSchedule);
        query.setParameter("isAgreed", isAgreed);
        return query.getResultList();
    }

    @Override
    public List<HealthConsentForm> findByStudentAndIsProcessed(Student student, Boolean isProcessed) {
        TypedQuery<HealthConsentForm> query = entityManager.createQuery(
                "SELECT h FROM HealthConsentForm h WHERE h.student = :student AND h.isProcessed = :isProcessed",
                HealthConsentForm.class
        );
        query.setParameter("student", student);
        query.setParameter("isProcessed", isProcessed);
        return query.getResultList();
    }

    // Các phương thức CRUD cơ bản
    @Override
    public <S extends HealthConsentForm> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<HealthConsentForm> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<HealthConsentForm> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(HealthConsentForm entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public <S extends HealthConsentForm> List<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public long count() {
        return repository.count();
    }

    // Các phương thức bắt buộc khác
    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public <S extends HealthConsentForm> S saveAndFlush(S entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public <S extends HealthConsentForm> List<S> saveAllAndFlush(Iterable<S> entities) {
        return repository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<HealthConsentForm> entities) {
        repository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> ids) {
        repository.deleteAllByIdInBatch(ids);
    }

    @Override
    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    @Override
    public List<HealthConsentForm> findAllById(Iterable<Integer> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public HealthConsentForm getOne(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public HealthConsentForm getById(Integer id) {
        return repository.getById(id);
    }

    @Override
    public HealthConsentForm getReferenceById(Integer id) {
        return repository.getReferenceById(id);
    }

    @Override
    public void deleteAll(Iterable<? extends HealthConsentForm> entities) {
        repository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> ids) {
        repository.deleteAllById(ids);
    }

    // Phương thức từ QueryByExampleExecutor
    @Override
    public <S extends HealthConsentForm> List<S> findAll(Example<S> example) {
        return repository.findAll(example);
    }

    @Override
    public <S extends HealthConsentForm> List<S> findAll(Example<S> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    @Override
    public <S extends HealthConsentForm> Page<S> findAll(Example<S> example, Pageable pageable) {
        return repository.findAll(example, pageable);
    }

    @Override
    public <S extends HealthConsentForm> long count(Example<S> example) {
        return repository.count(example);
    }

    @Override
    public <S extends HealthConsentForm> boolean exists(Example<S> example) {
        return repository.exists(example);
    }

    @Override
    public <S extends HealthConsentForm> Optional<S> findOne(Example<S> example) {
        return repository.findOne(example);
    }

    @Override
    public Page<HealthConsentForm> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<HealthConsentForm> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public <S extends HealthConsentForm, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        return repository.findBy(example, queryFunction);
    }
}
