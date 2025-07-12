package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicalSupplyRepository;
import com.team_3.School_Medical_Management_System.Model.MedicalSupply;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MedicalSupplyRepo implements MedicalSupplyRepository {
    private final EntityManager entityManager;

    @Autowired
    public MedicalSupplyRepo(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public List<MedicalSupply> findByQuantityAvailableLessThanReorderLevel() {
        String query = "SELECT ms FROM MedicalSupply ms WHERE ms.quantityAvailable < ms.reorderLevel";
        return entityManager.createQuery(query, MedicalSupply.class).getResultList();
    }

    @Override
    public List<MedicalSupply> findByCategoryCategoryId(Integer categoryId) {
        String query = "SELECT ms FROM MedicalSupply ms WHERE ms.category.categoryID = :categoryId";
        return entityManager.createQuery(query, MedicalSupply.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public List<MedicalSupply> findBySupplyNameContainingIgnoreCase(String name) {
        String query = "SELECT ms FROM MedicalSupply ms WHERE LOWER(ms.supplyName) LIKE LOWER(:name)";
        return entityManager.createQuery(query, MedicalSupply.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public List<MedicalSupply> findByCategoryCategoryNameContainingIgnoreCase(String categoryName) {
        String query = "SELECT ms FROM MedicalSupply ms WHERE LOWER(ms.category.categoryName) LIKE LOWER(:categoryName)";
        return entityManager.createQuery(query, MedicalSupply.class)
                .setParameter("categoryName", "%" + categoryName + "%")
                .getResultList();
    }

    // Implement các method từ JpaRepository
    @Override
    public List<MedicalSupply> findAll() {
        String query = "SELECT ms FROM MedicalSupply ms";
        return entityManager.createQuery(query, MedicalSupply.class).getResultList();
    }

    @Override
    public Optional<MedicalSupply> findById(Integer id) {
        MedicalSupply medicalSupply = entityManager.find(MedicalSupply.class, id);
        return Optional.ofNullable(medicalSupply);
    }

    @Override
    public boolean existsById(Integer id) {
        MedicalSupply medicalSupply = entityManager.find(MedicalSupply.class, id);
        return medicalSupply != null;
    }

    @Override
    public <S extends MedicalSupply> S save(S entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (entity.getMedicalSupplyId() == null) {
                entityManager.persist(entity);
            } else {
                entity = entityManager.merge(entity);
            }
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void deleteById(Integer id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            MedicalSupply medicalSupply = entityManager.find(MedicalSupply.class, id);
            if (medicalSupply != null) {
                entityManager.remove(medicalSupply);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void delete(MedicalSupply entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (entityManager.contains(entity)) {
                entityManager.remove(entity);
            } else {
                entityManager.remove(entityManager.merge(entity));
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public long count() {
        String query = "SELECT COUNT(ms) FROM MedicalSupply ms";
        return entityManager.createQuery(query, Long.class).getSingleResult();
    }

    // Implement các method khác từ JpaRepository (có thể để empty hoặc throw UnsupportedOperationException)
    @Override
    public <S extends MedicalSupply> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<MedicalSupply> findAllById(Iterable<Integer> integers) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends MedicalSupply> entities) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public void flush() {
        entityManager.flush();
    }

    @Override
    public <S extends MedicalSupply> S saveAndFlush(S entity) {
        S result = save(entity);
        flush();
        return result;
    }

    @Override
    public <S extends MedicalSupply> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public void deleteAllInBatch(Iterable<MedicalSupply> entities) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public void deleteAllInBatch() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public MedicalSupply getOne(Integer integer) {
        return entityManager.getReference(MedicalSupply.class, integer);
    }

    @Override
    public MedicalSupply getById(Integer integer) {
        return entityManager.find(MedicalSupply.class, integer);
    }

    @Override
    public MedicalSupply getReferenceById(Integer integer) {
        return entityManager.getReference(MedicalSupply.class, integer);
    }

    @Override
    public <S extends MedicalSupply> Optional<S> findOne(org.springframework.data.domain.Example<S> example) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public <S extends MedicalSupply> List<S> findAll(org.springframework.data.domain.Example<S> example) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public <S extends MedicalSupply> List<S> findAll(org.springframework.data.domain.Example<S> example, org.springframework.data.domain.Sort sort) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public <S extends MedicalSupply> org.springframework.data.domain.Page<S> findAll(org.springframework.data.domain.Example<S> example, org.springframework.data.domain.Pageable pageable) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public <S extends MedicalSupply> long count(org.springframework.data.domain.Example<S> example) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public <S extends MedicalSupply> boolean exists(org.springframework.data.domain.Example<S> example) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public <S extends MedicalSupply, R> R findBy(org.springframework.data.domain.Example<S> example, java.util.function.Function<org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<MedicalSupply> findAll(org.springframework.data.domain.Sort sort) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public org.springframework.data.domain.Page<MedicalSupply> findAll(org.springframework.data.domain.Pageable pageable) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
