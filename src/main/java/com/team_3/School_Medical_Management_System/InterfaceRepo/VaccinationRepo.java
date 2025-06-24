package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Vaccination_records;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VaccinationRepo extends JpaRepository<Vaccination_records, Integer> {
    // Có thể thêm hàm tìm kiếm custom nếu cần
    @EntityGraph(attributePaths = {
            "student",
            "student.parent",
            "vaccineBatches.vaccineType",
            "createdByNurse",         // ✅ sửa đúng tên field
            "updatedByNurse"          // ✅ nếu bạn muốn luôn fetch cả người cập nhật
    })
    @Query("SELECT v FROM Vaccination_records v WHERE v.VaccinationRecordID = :id")
    Optional<Vaccination_records> findFullRecordById(@Param("id") Integer id);


}