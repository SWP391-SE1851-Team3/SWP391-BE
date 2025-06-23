package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicationStatsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class MedicationStatsRepositoryImpl implements MedicationStatsRepository {

    private EntityManager entityManager;

    @Autowired
    public MedicationStatsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long countTotalSubmissions() {
        try {
            String sql = "SELECT COUNT(*) FROM MedicationSubmission";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }

    @Override
    public Long countApprovedSubmissions() {
        try {
            String sql = "SELECT COUNT(*) FROM Confirm_MedicationSubmission WHERE Status = 'APPROVED'";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }

    @Override
    public Long countRejectedSubmissions() {
        try {
            String sql = "SELECT COUNT(*) FROM Confirm_MedicationSubmission WHERE Status = 'REJECTED'";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {

            return 0L;
        }
    }

    @Override
    public Double calculateApprovalRate() {
        try {
            String sql = """
                SELECT 
                    CAST(SUM(CASE WHEN Status = 'APPROVED' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS DECIMAL(5,2))
                FROM Confirm_MedicationSubmission
                WHERE Status IN ('APPROVED', 'REJECTED')
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
}
