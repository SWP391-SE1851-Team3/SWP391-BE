package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicalEventStatsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class MedicalEventStatsRepositoryImpl implements MedicalEventStatsRepository {
    private EntityManager entityManager;

    @Autowired
    public MedicalEventStatsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long countTotalEvents() {
        try {
            String sql = "SELECT COUNT(*) FROM MedicalEvent";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {
         e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public Long countEmergencyEvents() {
        try {
            String sql = "SELECT COUNT(*) FROM MedicalEvent WHERE IsEmergency = 1";
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {
          e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public Long countCompletedEvents() {
        try {
            String sql = """
                SELECT COUNT(*) FROM MedicalEventDetails 
                WHERE ProcessingStatus = 'COMPLETED'
                """;
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {
          e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public Long countPendingEvents() {
        try {
            String sql = """
                SELECT COUNT(*) FROM MedicalEventDetails 
                WHERE ProcessingStatus = 'PENDING'
                """;
            Query query = entityManager.createNativeQuery(sql);
            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public Double calculateNotificationRate() {
        try {
            String sql = """
                SELECT 
                    CAST(SUM(CASE WHEN HasParentBeenInformed = 1 THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS DECIMAL(5,2))
                FROM MedicalEvent
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
            e.printStackTrace();
            return 0.0;
        }
    }
}
