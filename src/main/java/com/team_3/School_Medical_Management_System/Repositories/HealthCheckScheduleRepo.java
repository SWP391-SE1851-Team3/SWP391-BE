package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.HealthCheck_Schedule;
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
public class HealthCheckScheduleRepo implements com.team_3.School_Medical_Management_System.InterfaceRepo.HealthCheckScheduleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final SimpleJpaRepository<HealthCheck_Schedule, Integer> repository;

    public HealthCheckScheduleRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.repository = new SimpleJpaRepository<>(HealthCheck_Schedule.class, entityManager);
    }

    // Phương thức tùy chỉnh
    @Override
    public List<HealthCheck_Schedule> findByStatus(String status) {
        TypedQuery<HealthCheck_Schedule> query = entityManager.createQuery(
                "SELECT h FROM HealthCheck_Schedule h WHERE h.status = :status",
                HealthCheck_Schedule.class
        );
        query.setParameter("status", status);
        return query.getResultList();
    }

    // Các phương thức CRUD cơ bản
    @Override
    public <S extends HealthCheck_Schedule> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<HealthCheck_Schedule> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<HealthCheck_Schedule> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(HealthCheck_Schedule entity) {
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
    public <S extends HealthCheck_Schedule> List<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public long count() {
        return repository.count();
    }

    // Phương thức bắt buộc theo lỗi
    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public <S extends HealthCheck_Schedule> S saveAndFlush(S entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public <S extends HealthCheck_Schedule> List<S> saveAllAndFlush(Iterable<S> entities) {
        return repository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<HealthCheck_Schedule> entities) {
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
    public List<HealthCheck_Schedule> findAllById(Iterable<Integer> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public HealthCheck_Schedule getOne(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public HealthCheck_Schedule getById(Integer id) {
        return repository.getById(id);
    }

    @Override
    public HealthCheck_Schedule getReferenceById(Integer id) {
        return repository.getReferenceById(id);
    }

    @Override
    public void deleteAll(Iterable<? extends HealthCheck_Schedule> entities) {
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

    // Phương thức từ QueryByExampleExecutor (để tránh lỗi khi chạy)
    @Override
    public <S extends HealthCheck_Schedule> List<S> findAll(Example<S> example) {
        return repository.findAll(example);
    }

    @Override
    public <S extends HealthCheck_Schedule> List<S> findAll(Example<S> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    @Override
    public <S extends HealthCheck_Schedule> Page<S> findAll(Example<S> example, Pageable pageable) {
        return repository.findAll(example, pageable);
    }

    @Override
    public <S extends HealthCheck_Schedule> long count(Example<S> example) {
        return repository.count(example);
    }

    @Override
    public <S extends HealthCheck_Schedule> boolean exists(Example<S> example) {
        return repository.exists(example);
    }

    @Override
    public <S extends HealthCheck_Schedule, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        return repository.findBy(example, queryFunction);
    }

    @Override
    public <S extends HealthCheck_Schedule> Optional<S> findOne(Example<S> example) {
        return repository.findOne(example);
    }

    @Override
    public List<HealthCheck_Schedule> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<HealthCheck_Schedule> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // Implementation cho các phương thức mới được thêm vào interface
    @Override
    public HealthCheck_Schedule findByName(String name) {
        TypedQuery<HealthCheck_Schedule> query = entityManager.createQuery(
                "SELECT h FROM HealthCheck_Schedule h WHERE h.name = :name",
                HealthCheck_Schedule.class
        );
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null; // Trả về null nếu không tìm thấy
        }
    }

    @Override
    public boolean existsByName(String name) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(h) FROM HealthCheck_Schedule h WHERE h.name = :name",
                Long.class
        );
        query.setParameter("name", name);
        return query.getSingleResult() > 0;
    }
}
