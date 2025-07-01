package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.HealthConsultation_Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HealthConsultationParentRepo extends JpaRepository<HealthConsultation_Parent, Integer> {
    // Các phương thức tìm kiếm tùy chỉnh có thể được thêm vào đây nếu cần
    // Ví dụ: tìm kiếm theo parentID, v.v.

    @Modifying

    @Query("DELETE FROM HealthConsultation_Parent hcp WHERE hcp.consultID = :consultID")
    public void deleteByConsultID(int consultID);


    @Modifying
    @Query("DELETE FROM HealthConsultation_Parent hcp WHERE hcp.parentID = :parentID")
    public void deleteByParentID(int parentID);


    @Modifying

    @Query("DELETE FROM HealthConsultation_Parent hcp WHERE hcp.consultID IN " +
            "(SELECT hc.consultID FROM HealthConsultation hc WHERE hc.studentID = :studentId OR hc.checkID IN " +
            "(SELECT hcs.checkID FROM HealthCheck_Student hcs WHERE hcs.studentID = :studentId))")
    void deleteByStudentID(@Param("studentId") int studentId);
}
