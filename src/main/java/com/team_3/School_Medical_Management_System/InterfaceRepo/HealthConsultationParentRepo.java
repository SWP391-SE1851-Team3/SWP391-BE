package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.HealthConsultation_Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthConsultationParentRepo extends JpaRepository<HealthConsultation_Parent, Integer> {
    // Các phương thức tìm kiếm tùy chỉnh có thể được thêm vào đây nếu cần
    // Ví dụ: tìm kiếm theo parentID, v.v.

    public void deleteByParentID(int parentID);
}
