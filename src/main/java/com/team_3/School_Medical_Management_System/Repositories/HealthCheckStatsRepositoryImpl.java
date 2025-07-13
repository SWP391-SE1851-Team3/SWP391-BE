package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.HealthCheckStatsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public class HealthCheckStatsRepositoryImpl implements HealthCheckStatsRepository {
    private EntityManager entityManager;

    @Autowired
    public HealthCheckStatsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long countTotalSchedules(LocalDateTime startDate, LocalDateTime endDate) {

        String sql;
        if (startDate != null && endDate != null) {
            sql = "SELECT COUNT(*) FROM HealthCheck_Schedule WHERE Schedule_Date BETWEEN :startDate AND :endDate";
        } else {
            sql = "SELECT COUNT(*) FROM HealthCheck_Schedule";
        }
        try {

            Query query = entityManager.createNativeQuery(sql);
            if (startDate != null && endDate != null) {
                query.setParameter("startDate", startDate);
                query.setParameter("endDate", endDate);
            }
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }

    @Override
    public Long countCompletedSchedules(LocalDateTime startDate, LocalDateTime endDate) {
        String sql;
        if (startDate != null && endDate != null) {
            sql = "SELECT COUNT(*) FROM HealthCheck_Schedule WHERE Status = N'Đã xác nhận' AND Schedule_Date BETWEEN :startDate AND :endDate";
        } else {
            sql = "SELECT COUNT(*) FROM HealthCheck_Schedule WHERE Status = N'Đã xác nhận'";
        }
        try {
            //String sql = "SELECT COUNT(*) FROM HealthCheck_Schedule WHERE Status = 1";
            Query query = entityManager.createNativeQuery(sql);
            if (startDate != null && endDate != null) {
                query.setParameter("startDate", startDate);
                query.setParameter("endDate", endDate);
            }
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }

    @Override
    public Long countTotalChecked(LocalDateTime startDate, LocalDateTime endDate) {
        String sql;
     //   if (startDate != null && endDate != null) {
       //     sql = "SELECT COUNT(DISTINCT StudentID) FROM HealthCheck_Student WHERE CheckDate BETWEEN :startDate AND :endDate";
      //  } else {
            sql = "SELECT COUNT(DISTINCT StudentID) FROM HealthCheck_Student";
       // }
        try {
           // String sql = "SELECT COUNT(DISTINCT StudentID) FROM HealthCheck_Student";
            Query query = entityManager.createNativeQuery(sql);
//            if (startDate != null && endDate != null) {
//                query.setParameter("startDate", startDate);
//                query.setParameter("endDate", endDate);
//            }
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }

    @Override
    public Double calculateConsentRate(LocalDateTime startDate, LocalDateTime endDate) {
        String sql;
        if (startDate != null && endDate != null) {
            sql = """
                SELECT 
                    CAST(SUM(CASE WHEN IsAgreed = N'Đồng ý' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS DECIMAL(5,2))
                FROM HealthConsentForm
                WHERE send_date BETWEEN :startDate AND :endDate
                """;
        } else {
            sql = """
                SELECT 
                    CAST(SUM(CASE WHEN IsAgreed = N'Đồng ý' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS DECIMAL(5,2))
                FROM HealthConsentForm
                """;
        }
        try {

            Query query = entityManager.createNativeQuery(sql);
            if (startDate != null && endDate != null) {
                query.setParameter("startDate", startDate);
                query.setParameter("endDate", endDate);
            }
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
        String sql = "SELECT AVG(BMI) FROM HealthCheck_Student WHERE BMI IS NOT NULL";

        try {
           // String sql = "SELECT AVG(BMI) FROM HealthCheck_Student WHERE BMI IS NOT NULL";
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

