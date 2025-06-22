package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.MedicalEvent_Nurse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MedicalEvent_NurseRepository {
    @Autowired
    private EntityManager entityManager;
    public MedicalEvent_Nurse findByMedicalEvent_EventIDAndSchoolNurse_NurseID(int eventID, Integer nurseID) {
        String jpql = "SELECT m FROM MedicalEvent_Nurse m WHERE m.medicalEvent.eventID = :eventID AND m.schoolNurse.NurseID = :nurseID";
        try {
            return entityManager.createQuery(jpql, MedicalEvent_Nurse.class)
                    .setParameter("eventID", eventID)
                    .setParameter("nurseID", nurseID)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Trả về null nếu không tìm thấy bản ghi
        }
    }
}
