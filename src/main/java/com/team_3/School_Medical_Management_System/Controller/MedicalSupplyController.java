package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.MedicalSupplyDTO;
import com.team_3.School_Medical_Management_System.DTO.SupplyCategoryDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.MedicalSupplyServiceInterFace;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-supplies")
public class MedicalSupplyController {
    private final MedicalSupplyServiceInterFace medicalSupplyService;

    @Autowired
    public MedicalSupplyController(MedicalSupplyServiceInterFace medicalSupplyService) {
        this.medicalSupplyService = medicalSupplyService;
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả vật tư y tế")
    public ResponseEntity<List<MedicalSupplyDTO>> getAll() {
        List<MedicalSupplyDTO> list = medicalSupplyService.getAllMedicalSupply();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @Operation(summary = "Thêm mới vật tư y tế")
    public ResponseEntity<MedicalSupplyDTO> add(@RequestBody MedicalSupplyDTO dto) {
        MedicalSupplyDTO result = medicalSupplyService.addMedicalSupply(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật thông tin vật tư y tế theo ID")
    public ResponseEntity<MedicalSupplyDTO> update(
            @PathVariable Integer id,
            @RequestBody MedicalSupplyDTO dto
    ) {
        dto.setMedicalSupplyID(id); // Gán ID vào DTO
        MedicalSupplyDTO result = medicalSupplyService.updateMedicalSupply(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin vật tư y tế theo ID")
    public ResponseEntity<MedicalSupplyDTO> getById(@PathVariable Integer id) {
        try {
            MedicalSupplyDTO result = medicalSupplyService.getMedicalSupplyById(id);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            // Log lỗi để debug
            System.err.println("Error getting medical supply by ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Log lỗi hệ thống
            System.err.println("System error getting medical supply by ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa vật tư y tế theo ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            medicalSupplyService.deleteMedicalSupply(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Log lỗi để debug
            System.err.println("Error deleting medical supply by ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Log lỗi hệ thống
            System.err.println("System error deleting medical supply by ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Tìm kiếm vật tư y tế theo tên")
    public ResponseEntity<List<MedicalSupplyDTO>> searchByName(@RequestParam String name) {
        List<MedicalSupplyDTO> list = medicalSupplyService.searchMedicalSupplyByName(name);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search/category")
    @Operation(summary = "Tìm kiếm vật tư y tế theo tên danh mục")
    public ResponseEntity<List<MedicalSupplyDTO>> searchByCategory(@RequestParam String category) {
        List<MedicalSupplyDTO> list = medicalSupplyService.searchMedicalSupplyByCategory(category);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search/category-id")
    @Operation(summary = "Tìm kiếm vật tư y tế theo ID danh mục")
    public ResponseEntity<List<MedicalSupplyDTO>> searchByCategoryId(@RequestParam Integer categoryId) {
        List<MedicalSupplyDTO> list = medicalSupplyService.searchMedicalSupplyByCategoryId(categoryId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/categories")
    @Operation(summary = "Lấy danh sách tất cả danh mục vật tư y tế")
    public ResponseEntity<List<SupplyCategoryDTO>> getAllCategories() {
        List<SupplyCategoryDTO> categories = medicalSupplyService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

}
