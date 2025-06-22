package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.NotificationsParent;
import com.team_3.School_Medical_Management_System.Model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationsParentRepository extends JpaRepository<NotificationsParent, Integer> {
    List<NotificationsParent> findByParent(Parent parent);
    List<NotificationsParent> findByParentAndStatus(Parent parent, boolean status);
}
