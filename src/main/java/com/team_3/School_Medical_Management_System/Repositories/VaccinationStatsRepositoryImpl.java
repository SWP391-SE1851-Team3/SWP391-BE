package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.VaccinationStatsRepository;
import com.team_3.School_Medical_Management_System.Model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class VaccinationStatsRepositoryImpl implements VaccinationStatsRepository {

    private final EntityManager entityManager;

    @Autowired
    public VaccinationStatsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long countTotalBatches() {
        try {
            String sql = "SELECT COUNT(*) FROM Vaccine_Batches";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }

    @Override
    public Long countCompletedBatches() {
        try {
            String sql = "SELECT COUNT(*) FROM Vaccine_Batches WHERE status = 'COMPLETED'";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }

    @Override
    public Long countTotalVaccinated() {
        try {
            String sql = "SELECT COUNT(DISTINCT StudentID) FROM Vaccination_records";
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
                    CAST(SUM(CASE WHEN IsAgree = 'Đồng ý' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS DECIMAL(5,2))
                FROM Consent_forms
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
    public Long countTotalReactions() {
        try {
            String sql = "SELECT COUNT(*) FROM Post_vaccination_observations";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }
}
