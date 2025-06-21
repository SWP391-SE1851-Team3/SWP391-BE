package com.team_3.School_Medical_Management_System.Repositories;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ManagerInterFace;
import com.team_3.School_Medical_Management_System.Model.Manager;
import com.team_3.School_Medical_Management_System.Model.Parent;
import com.team_3.School_Medical_Management_System.Model.SchoolNurse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ManagerRepo implements ManagerInterFace {
    private EntityManager entityManager;
    @Autowired
    public ManagerRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Manager LoginByAccount(String Email, String Password) {
        String sql = "SELECT m FROM Manager m WHERE m.Email = :Email AND m.Password = :Password";
        try {
            return entityManager.createQuery(sql, Manager.class)
                    .setParameter("Email", Email)
                    .setParameter("Password", Password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


}
