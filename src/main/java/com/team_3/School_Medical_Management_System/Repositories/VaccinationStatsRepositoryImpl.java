package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.VaccinationStatsRepository;
import com.team_3.School_Medical_Management_System.Model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public class VaccinationStatsRepositoryImpl implements VaccinationStatsRepository {

    private final EntityManager entityManager;

    @Autowired
    public VaccinationStatsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long countTotalBatches(LocalDateTime startDate, LocalDateTime endDate) {
        String sql;
        if (startDate != null && endDate != null) {
            sql = "SELECT COUNT(*) FROM Vaccine_Batches WHERE created_at BETWEEN :startDate AND :endDate";
        } else {
            sql = "SELECT COUNT(*) FROM Vaccine_Batches";
        }
        try {
           // String sql = "SELECT COUNT(*) FROM Vaccine_Batches";
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
    public Long countCompletedBatches(LocalDateTime startDate, LocalDateTime endDate) {
        String sql;
        if (startDate != null && endDate != null) {
            sql = "SELECT COUNT(*) FROM Vaccine_Batches WHERE status = 'COMPLETED' AND created_at BETWEEN :startDate AND :endDate";
        } else {
            sql = "SELECT COUNT(*) FROM Vaccine_Batches WHERE status = 'COMPLETED'";
        }
        try {
            //String sql = "SELECT COUNT(*) FROM Vaccine_Batches WHERE status = 'COMPLETED'";
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
    public Long countTotalVaccinated(LocalDateTime startDate, LocalDateTime endDate) {
        String sql;
        if (startDate != null && endDate != null) {
            sql = "SELECT COUNT(DISTINCT StudentID) FROM Vaccination_records WHERE created_at BETWEEN :startDate AND :endDate";
        } else {
            sql = "SELECT COUNT(DISTINCT StudentID) FROM Vaccination_records";
        }
        try {
           // String sql = "SELECT COUNT(DISTINCT StudentID) FROM Vaccination_records";
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
    public Double calculateConsentRate(LocalDateTime startDate, LocalDateTime endDate) {
        String sql;
        if (startDate != null && endDate != null) {
            sql = """
                SELECT 
                    CAST(SUM(CASE WHEN IsAgree = 'Đồng ý' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS DECIMAL(5,2))
                FROM Consent_forms
                WHERE created_at BETWEEN :startDate AND :endDate
                """;
        } else {
            sql = """
                SELECT 
                    CAST(SUM(CASE WHEN IsAgree = 'Đồng ý' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS DECIMAL(5,2))
                FROM Consent_forms
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
    public Long countTotalReactions(LocalDateTime startDate, LocalDateTime endDate) {
        String sql;
        if (startDate != null && endDate != null) {
            sql = "SELECT COUNT(*) FROM Post_vaccination_observations WHERE created_at BETWEEN :startDate AND :endDate";
        } else {
            sql = "SELECT COUNT(*) FROM Post_vaccination_observations";
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
}
