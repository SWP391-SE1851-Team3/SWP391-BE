package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Vaccination_records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinationRepo extends JpaRepository<Vaccination_records, Integer> {
    // Có thể thêm hàm tìm kiếm custom nếu cần
}