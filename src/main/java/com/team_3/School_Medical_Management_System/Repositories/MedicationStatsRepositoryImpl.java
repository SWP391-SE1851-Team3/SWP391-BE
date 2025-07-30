package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicationStatsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public class MedicationStatsRepositoryImpl implements MedicationStatsRepository {

    private EntityManager entityManager;

    @Autowired
    public MedicationStatsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long countTotalSubmissions(LocalDateTime startDate, LocalDateTime endDate) {
        String sql;
        if (startDate != null && endDate != null) {
            sql = "SELECT COUNT(*) FROM MedicationSubmission WHERE SubmissionDate BETWEEN :startDate AND :endDate";
        } else {
            sql = "SELECT COUNT(*) FROM MedicationSubmission";
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
    public Long countApprovedSubmissions(LocalDateTime startDate, LocalDateTime endDate) {
        String sql;
        if (startDate != null && endDate != null) {
            sql = "SELECT COUNT(*) FROM Confirm_MedicationSubmission WHERE Status = N'Đã phát thuốc' AND SubmissionDate BETWEEN :startDate AND :endDate";
        } else {
            sql = "SELECT COUNT(*) FROM Confirm_MedicationSubmission WHERE Status = N'Đã phát thuốc'";
        }
        try {
            //String sql = "SELECT COUNT(*) FROM Confirm_MedicationSubmission WHERE Status = 'APPROVED'";
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
    public Long countRejectedSubmissions(LocalDateTime startDate, LocalDateTime endDate) {
        String sql;
        if (startDate != null && endDate != null) {
            sql = "SELECT COUNT(*) FROM Confirm_MedicationSubmission WHERE Status = N'Đã hủy' AND SubmissionDate BETWEEN :startDate AND :endDate";
        } else {
            sql = "SELECT COUNT(*) FROM Confirm_MedicationSubmission WHERE Status = N'Đã hủy'";
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
    public Double calculateApprovalRate(LocalDateTime startDate, LocalDateTime endDate) {
        String sql;
        if (startDate != null && endDate != null) {
            sql = """
                SELECT 
                    CAST(SUM(CASE WHEN Status = N'Đã phát thuốc' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS DECIMAL(5,2))
                FROM Confirm_MedicationSubmission
                WHERE Status IN (N'Đã phát thuốc', N'Đã hủy') AND SubmissionDate BETWEEN :startDate AND :endDate
                """;
        } else {
            sql = """
                SELECT 
                    CAST(SUM(CASE WHEN Status = N'Đã phát thuốc' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS DECIMAL(5,2))
                FROM Confirm_MedicationSubmission
                WHERE Status IN (N'Đã phát nhận', N'Đã hủy')
                """;
        }
        try {
//            String sql = """
//                SELECT
//                    CAST(SUM(CASE WHEN Status = 'APPROVED' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS DECIMAL(5,2))
//                FROM Confirm_MedicationSubmission
//                WHERE Status IN ('APPROVED', 'REJECTED')
//                """;
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
}
