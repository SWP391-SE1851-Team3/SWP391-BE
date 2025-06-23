package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.SystemStatsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SystemStatsRepositoryImpl implements SystemStatsRepository {
    private final EntityManager entityManager;

    @Autowired
    public SystemStatsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long countStudents() {
        try {
            String sql = "SELECT COUNT(*) FROM Student";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }

    @Override
    public Long countActiveStudents() {
        try {
            String sql = "SELECT COUNT(*) FROM Student WHERE IsActive = 1";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }

    @Override
    public Long countParents() {
        try {
            String sql = "SELECT COUNT(*) FROM Parent";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }

    @Override
    public Long countNurses() {
        try {
            String sql = "SELECT COUNT(*) FROM SchoolNurse";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }

    @Override
    public Long countManagers() {
        try {
            String sql = "SELECT COUNT(*) FROM Manager";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }
}
