package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.HealthCheckStatsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class HealthCheckStatsRepositoryImpl implements HealthCheckStatsRepository {
    private EntityManager entityManager;

    @Autowired
    public HealthCheckStatsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long countTotalSchedules() {
        try {
            String sql = "SELECT COUNT(*) FROM HealthCheck_Schedule";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }

    @Override
    public Long countCompletedSchedules() {
        try {
            String sql = "SELECT COUNT(*) FROM HealthCheck_Schedule WHERE Status = 1";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }

    @Override
    public Long countTotalChecked() {
        try {
            String sql = "SELECT COUNT(DISTINCT StudentID) FROM HealthCheck_Student";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }

    @Override
    public Double calculateConsentRate() {
        try {
            String sql = """
                SELECT 
                    CAST(SUM(CASE WHEN IsAgreed = 'YES' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS DECIMAL(5,2))
                FROM HealthConsentForm
                """;
            Query query = entityManager.createNativeQuery(sql);
            Object result = query.getSingleResult();

            if (result == null) {
                return 0.0;
            }

            if (result instanceof BigDecimal) {
                return ((BigDecimal) result).doubleValue();
            } else if (result instanceof Number) {
                return ((Number) result).doubleValue();
            }

            return 0.0;
        } catch (Exception e) {

            return 0.0;
        }
    }

    @Override
    public Double calculateAverageBMI() {
        try {
            String sql = "SELECT AVG(BMI) FROM HealthCheck_Student WHERE BMI IS NOT NULL";
            Query query = entityManager.createNativeQuery(sql);
            Object result = query.getSingleResult();

            if (result == null) {
                return 0.0;
            }

            if (result instanceof BigDecimal) {
                return ((BigDecimal) result).doubleValue();
            } else if (result instanceof Number) {
                return ((Number) result).doubleValue();
            }

            return 0.0;
        } catch (Exception e) {

            return 0.0;
        }
    }
}

