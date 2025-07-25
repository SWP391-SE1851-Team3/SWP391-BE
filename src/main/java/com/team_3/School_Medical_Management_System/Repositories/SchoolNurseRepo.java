package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.SchoolNurseInterFace;
import com.team_3.School_Medical_Management_System.Model.Parent;
import com.team_3.School_Medical_Management_System.Model.SchoolNurse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class SchoolNurseRepo implements SchoolNurseInterFace {
    private EntityManager entityManager;

    @Autowired
    public SchoolNurseRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<SchoolNurse> getSchoolNurses() {
        String sql = "select s from SchoolNurse s";
        return entityManager.createQuery(sql, SchoolNurse.class).getResultList();
    }

    @Override
    public SchoolNurse getSchoolNursesByName(String FullName) {
        String sql = "select s from SchoolNurse s where s.FullName = :FullName";
        return entityManager.createQuery(sql, SchoolNurse.class).setParameter("FullName", FullName).getSingleResult();
    }

    @Override
    public void AddNewSchoolNurses(SchoolNurse schoolNurse) {
        entityManager.persist(schoolNurse);

    }

    @Override
    public SchoolNurse UpdateSchoolNurses(SchoolNurse schoolNurse) {
        return entityManager.merge(schoolNurse);
    }

    @Override
    public void DeleteSchoolNurses(int id) {
        entityManager.remove(entityManager.find(SchoolNurse.class, id));

    }

    @Override
    public SchoolNurse LoginByAccount(String Email, String Password) {
        String sql = "select s from SchoolNurse s where s.Email = :Email and s.Password = :Password";
        return entityManager.createQuery(sql, SchoolNurse.class).setParameter("Email", Email).setParameter("Password", Password).getSingleResult();

    }

    @Override
    public SchoolNurse GetSchoolNursesById(int id) {
        return entityManager.find(SchoolNurse.class, id);
    }


    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        String jpql = "SELECT s FROM SchoolNurse s WHERE s.Email = :email";
        List<SchoolNurse> results = entityManager.createQuery(jpql, SchoolNurse.class)
                                  .setParameter("email", email)
                                  .getResultList();
        if (results.isEmpty()) {
            return false;
        }
        SchoolNurse p = results.get(0); // lấy ra cái dối tượng đầu tiên
        // So sánh mật khẩu cũ trực tiếp
        if (!p.getPassword().equals(oldPassword)) {
            return false;
        }
        // Cập nhật mật khẩu mới
        p.setPassword(newPassword);
        entityManager.merge(p);
        return true;
    }

    @Override
    public boolean existsByUserName(String userName) {
        return entityManager.createQuery(" SELECT COUNT(s) FROM SchoolNurse s WHERE s.UserName = :userName", Long.class)
                .setParameter("userName", userName)
                .getSingleResult() > 0;
    }

    @Override
    public boolean existsByEmail(String mail) {
        return entityManager.createQuery("SELECT COUNT(s) FROM SchoolNurse s WHERE s.Email = :Email", Long.class)
                .setParameter("Email", mail)
                .getSingleResult() > 0;
    }

    @Override
    public SchoolNurse findByEmailLogin(String emailName) {
        String jpql = "SELECT s FROM SchoolNurse s WHERE s.Email = :emailName";
        try {
            return entityManager.createQuery(jpql, SchoolNurse.class)
                    .setParameter("emailName", emailName.trim())
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Không tìm thấy kết quả
        }
    }

//    @Override
//    public SchoolNurse findByUserName(String userName) {
//        String jpql = "SELECT s FROM SchoolNurse s WHERE s.UserName = :userName";
//        try {
//
//            SchoolNurse schoolNurse = entityManager.createQuery(jpql, SchoolNurse.class)
//                    .setParameter("userName", userName.trim())
//                    .getSingleResult();
//           return schoolNurse; // Trả về đối tượng SchoolNurse nếu tìm thấy
//        } catch (Exception e) {
//            return null; // Không tìm thấy kết quả
//        }
//       // Trả về null nếu không tìm thấy
//    }

    @Override
    public SchoolNurse checkIdAndRoleExist(int id, int roleId) {
        String jpql = "SELECT s FROM SchoolNurse s WHERE s.NurseID = :id AND s.RoleID = :roleId";
        try {
            return entityManager.createQuery(jpql, SchoolNurse.class)
                    .setParameter("id", id)
                    .setParameter("roleId", roleId)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Không tìm thấy kết quả
        }
    }

    @Override
    public void updateNurse(int id, int isActive) {
        String jpql = "UPDATE SchoolNurse s SET s.IsActive = :isActive WHERE s.NurseID = :id";
        entityManager.createQuery(jpql)
                .setParameter("isActive", isActive)
                .setParameter("id", id)
                .executeUpdate();
    }


}
