package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicalEventStatsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Repository
public class MedicalEventStatsRepositoryImpl implements MedicalEventStatsRepository {
    private EntityManager entityManager;

    @Autowired
    public MedicalEventStatsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
//nghĩa là trong DB  tôi sử dụng LocalDateTime hoặc 1 Định dạng thời gian nào mà có cả giờ nhưng hiện tại tôi tìm kiếm theo ngày thàng năm k có giờ lên tôi cần đổi thành định dạng db giống với định dạng của kiểu mình muốn tìm kiếm đúng
    @Override
    public Long countTotalEvents(LocalDateTime startDate, LocalDateTime endDate) {
        String jpql;

        if(startDate == null || endDate == null) {
            jpql = "SELECT COUNT(e) FROM MedicalEvent e";
        } else {
            jpql = "SELECT COUNT(e) FROM MedicalEvent e WHERE e.eventDateTime BETWEEN :startDate AND :endDate";
        }
        try {
            Query query = entityManager.createQuery(jpql);
            if (startDate != null && endDate != null) {
                query.setParameter("startDate", startDate);
                query.setParameter("endDate", endDate);
            }
            Number result = (Number) query.getSingleResult();
            return result != null ? result.longValue() : 0L;
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi đếm sự kiện", e);
        }
    }


    @Override
    public Long countEmergencyEvents(LocalDateTime startDate, LocalDateTime endDate) {
        String jpql;
        if (startDate == null || endDate == null) {
            jpql = "SELECT COUNT(e) FROM MedicalEvent e WHERE e.isEmergency = true";
        } else {
            jpql = "SELECT COUNT(e) FROM MedicalEvent e WHERE e.isEmergency = true AND e.eventDateTime BETWEEN :startDate AND :endDate";
        }

        try {
            Query query = entityManager.createQuery(jpql);
            if (startDate != null && endDate != null) {
                query.setParameter("startDate", startDate);
                query.setParameter("endDate", endDate);
            }
            Number result = (Number) query.getSingleResult();
            return result != null ? result.longValue() : 0L;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public Long countCompletedEvents(LocalDateTime startDate, LocalDateTime endDate) {
        String jpql;

            jpql = "SELECT COUNT(e) FROM MedicalEventDetails e WHERE e.processingStatus = 'COMPLETED'";

        try {
            Query query = entityManager.createQuery(jpql);


            Number result = (Number) query.getSingleResult();
            return result != null ? result.longValue() : 0L;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public Long countPendingEvents(LocalDateTime startDate, LocalDateTime endDate) {

        String sql;

            sql = "SELECT COUNT(*) FROM MedicalEventDetails WHERE ProcessingStatus = 'PENDING'";

        try {
//            String sql = """
//                    SELECT COUNT(*) FROM MedicalEventDetails
//                    WHERE ProcessingStatus = 'PENDING' and EventDateTime BETWEEN ? AND ?
//                    """;
            Query query = entityManager.createNativeQuery(sql);

            Number result = (Number) query.getSingleResult();
            return result.longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public Double calculateNotificationRate(LocalDateTime startDate, LocalDateTime endDate) {
        String jpql;
        if (startDate == null || endDate == null) {
            jpql = "SELECT COALESCE(SUM(CASE WHEN e.hasParentBeenInformed = true THEN 1 ELSE 0 END) * 100.0 / COUNT(e), 0.0) " +
                    "FROM MedicalEvent e";
        } else {
            jpql = "SELECT COALESCE(SUM(CASE WHEN e.hasParentBeenInformed = true THEN 1 ELSE 0 END) * 100.0 / COUNT(e), 0.0) " +
                    "FROM MedicalEvent e WHERE e.eventDateTime BETWEEN :startDate AND :endDate";
        }

        try {
            Query query = entityManager.createQuery(jpql, Double.class);
            if (startDate != null && endDate != null) {
                query.setParameter("startDate", startDate);
                query.setParameter("endDate", endDate);
            }

            Double result = (Double) query.getSingleResult();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
