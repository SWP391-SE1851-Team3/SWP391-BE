package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationSubmissionInterFace extends JpaRepository<MedicationSubmission, Integer> {
    // Các phương thức tìm kiếm tùy chỉnh
    List<MedicationSubmission> findByParentId(int parentId);
    List<MedicationSubmission> findByStatus(MedicationSubmission.SubmissionStatus status);

    // Phương thức tìm tất cả các đơn thuốc
    List<MedicationSubmission> findAllSubmissions();

    // Các phương thức tìm kiếm theo trạng thái
    List<MedicationSubmission> findPendingSubmissions();
    List<MedicationSubmission> findApprovedSubmissions();
    List<MedicationSubmission> findRejectedSubmissions();
    List<MedicationSubmission> findAdministeredSubmissions();
}
