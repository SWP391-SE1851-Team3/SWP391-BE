package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.MedicalSupplyReportDTO;
import com.team_3.School_Medical_Management_System.DTO.SupplyCategoryDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicalSupplyRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.SupplyCategoryRepo;
import com.team_3.School_Medical_Management_System.Model.MedicalSupply;
import com.team_3.School_Medical_Management_System.Model.SupplyCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalSupplyService {
    private SupplyCategoryRepo supplyCategory;
    private MedicalSupplyRepository medicalSupplyRepository;

    @Autowired


    public MedicalSupplyService(SupplyCategoryRepo supplyCategory, MedicalSupplyRepository medicalSupplyRepository) {
        this.supplyCategory = supplyCategory;
        this.medicalSupplyRepository = medicalSupplyRepository;
    }


    public List<MedicalSupplyReportDTO> getMedicalSupplyReport(Integer categoryId) {
        // Lấy danh sách vật tư từ repository
        List<MedicalSupply> supplies;
        if (categoryId == null) {
            supplies = medicalSupplyRepository.findAll();
        } else {
            supplies = medicalSupplyRepository.findByCategoryCategoryId(categoryId);
        }

        // Chuyển đổi danh sách vật tư sang DTO bằng vòng lặp for
        List<MedicalSupplyReportDTO> report = new ArrayList<>();
        for (MedicalSupply supply : supplies) {
            MedicalSupplyReportDTO dto = toReportDTO(supply);
            report.add(dto);
        }

        return report;
    }

    public List<MedicalSupplyReportDTO> getLowStockReport() {
        // Lấy danh sách vật tư dưới mức đặt hàng lại từ repository
        List<MedicalSupply> lowStockSupplies = medicalSupplyRepository.findByQuantityAvailableLessThanReorderLevel();

        // Chuyển đổi danh sách sang DTO bằng vòng lặp for
        List<MedicalSupplyReportDTO> report = new ArrayList<>();
        for (MedicalSupply supply : lowStockSupplies) {
            MedicalSupplyReportDTO dto = toReportDTO(supply);
            report.add(dto);
        }

        return report;
    }

    private MedicalSupplyReportDTO toReportDTO(MedicalSupply supply) {
        MedicalSupplyReportDTO dto = new MedicalSupplyReportDTO();
        dto.setSupplyName(supply.getSupplyName());
        // Kiểm tra null để tránh lỗi khi danh mục không tồn tại
        dto.setCategoryName(supply.getCategory() != null ? supply.getCategory().getCategoryName() : "Không xác định");
        dto.setUnit(supply.getUnit());
        dto.setQuantityAvailable(supply.getQuantityAvailable());
        dto.setReorderLevel(supply.getReorderLevel());
        // Kiểm tra xem số lượng có dưới mức đặt hàng lại không
        dto.setIsBelowReorderLevel(supply.getQuantityAvailable() < supply.getReorderLevel());
        dto.setDateAdded(supply.getDateAdded());
        dto.setStorageTemperature(supply.getStorageTemperature());
        return dto;
    }

    public List<SupplyCategoryDTO> getAllCategories() {

        List<SupplyCategory> list = supplyCategory.findAll();
        List<SupplyCategoryDTO> dto = new ArrayList<>();
        for (SupplyCategory category : list) {
            SupplyCategoryDTO d = new SupplyCategoryDTO();
            d.setId(category.getCategoryID());
            d.setCategoryName(category.getCategoryName());
            dto.add(d);
        }
        return dto;
    }

}
