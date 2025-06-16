package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.ParentInterFace;
import com.team_3.School_Medical_Management_System.Model.Parent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ParentRepo implements ParentInterFace  {
    private EntityManager entityManager;
    @Autowired
    public ParentRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Parent> getParents() {
        String sql = "select p from Parent p ";
        return entityManager.createQuery(sql, Parent.class).getResultList();
    }

    @Override
    public Parent getParentByName(String fullName) {
        String jpql = "SELECT p FROM Parent p WHERE p.FullName = :fullName";
        return entityManager.createQuery(jpql, Parent.class)
                .setParameter("fullName", fullName)
                .getSingleResult();
    }


    @Override
    public void AddNewParent(Parent parent) {
         entityManager.persist(parent);
    }

    @Override
    public Parent UpdateParent(Parent parent) {
       return entityManager.merge(parent);
    }

    @Override
    public void DeleteParent(int id) {
        Parent p = entityManager.find(Parent.class, id);  // Tìm entity
        if (p != null) {
            entityManager.remove(p);  // Xóa entity
        }
    }


    @Override
    public Parent LoginByAccount(String email, String password) {
        String sql = "SELECT p FROM Parent p WHERE p.Email = :email AND p.Password = :password";
        try {
            return entityManager.createQuery(sql, Parent.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    @Override
    public Parent GetParentById(int id) {
        return entityManager.find(Parent.class, id);
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        String jpql = "SELECT p FROM Parent p WHERE p.Email = :email";
        List<Parent> results = entityManager.createQuery(jpql, Parent.class)
                .setParameter("email", email)
                .getResultList();
        if (results.isEmpty()) {
            return false;
        }
        Parent p = results.get(0);
        // So sánh mật khẩu cũ trực tiếp
        if (!p.getPassword().equals(oldPassword)) {
            return false; // mật khẩu cũ không đúng
        }
        // Cập nhật mật khẩu mới
        p.setPassword(newPassword);
        entityManager.merge(p);
        return true;
    }

    @Override
    public Parent getParentByEmail(String Email) {
        String jpql = "SELECT p FROM Parent p WHERE p.Email = :Email";
        return entityManager.createQuery(jpql, Parent.class)
                .setParameter("Email", Email)
                .getSingleResult();
    }

    @Override
    public boolean existsByUserName(String userName) {

        return entityManager.createQuery(" SELECT COUNT(p) FROM Parent p WHERE p.UserName = :userName", Long.class)
                .setParameter("userName", userName)
                .getSingleResult() > 0;

    }

    @Override
    public boolean existsByEmail(String userName) {
        return entityManager.createQuery("SELECT COUNT(p) FROM Parent p WHERE p.Email = :Email", Long.class)
                .setParameter("Email", userName)
                .getSingleResult() > 0;
    }
}
