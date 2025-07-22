package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicalSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicalSupplyRepository extends JpaRepository<MedicalSupply, Integer> {

    // Tìm những MedicalSupply có Quantity < ReorderLevel
    @Query("SELECT ms FROM MedicalSupply ms WHERE ms.quantityAvailable < ms.reorderLevel")
    List<MedicalSupply> findByQuantityAvailableLessThanReorderLevel();

    // Tìm MedicalSupply theo category ID
    @Query("SELECT ms FROM MedicalSupply ms WHERE ms.category.categoryID = :categoryId")
    List<MedicalSupply> findByCategoryCategoryId(@Param("categoryId") Integer categoryId);

    // Tìm MedicalSupply theo tên (chứa chuỗi tìm kiếm, không phân biệt hoa thường)
    List<MedicalSupply> findBySupplyNameContainingIgnoreCase(String name);

    // Tìm MedicalSupply theo tên category (chứa chuỗi tìm kiếm, không phân biệt hoa thường)
    @Query("SELECT ms FROM MedicalSupply ms WHERE LOWER(ms.category.categoryName) LIKE LOWER(CONCAT('%', :categoryName, '%'))")
    List<MedicalSupply> findByCategoryCategoryNameContainingIgnoreCase(@Param("categoryName") String categoryName);
}
