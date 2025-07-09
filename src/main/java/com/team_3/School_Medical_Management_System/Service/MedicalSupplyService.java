package com.team_3.School_Medical_Management_System.Service;


import com.team_3.School_Medical_Management_System.DTO.MedicalSupplyDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicalSupplyReportDTO;
import com.team_3.School_Medical_Management_System.DTO.SupplyCategoryDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.MedicalSupplyServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicalSupplyRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.SupplyCategoryRepo;
import com.team_3.School_Medical_Management_System.Model.MedicalSupply;
import com.team_3.School_Medical_Management_System.Model.SupplyCategory;
import com.team_3.School_Medical_Management_System.Model.HealthCheck_Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicalSupplyService implements MedicalSupplyServiceInterFace {

    private final SupplyCategoryRepo supplyCategory;
    private final MedicalSupplyRepository medicalSupplyRepository;

    @Autowired
    public MedicalSupplyService(SupplyCategoryRepo supplyCategory,
                              MedicalSupplyRepository medicalSupplyRepository) {
        this.supplyCategory = supplyCategory;
        this.medicalSupplyRepository = medicalSupplyRepository;
    }

    // Helper methods để convert giữa Entity và DTO
    private MedicalSupplyDTO convertToDTO(MedicalSupply medicalSupply) {
        MedicalSupplyDTO dto = new MedicalSupplyDTO();
        dto.setMedicalSupplyID(medicalSupply.getMedicalSupplyId());
        dto.setSupplyName(medicalSupply.getSupplyName());
        dto.setUnit(medicalSupply.getUnit());
        dto.setQuantityAvailable(medicalSupply.getQuantityAvailable());
        dto.setReorderLevel(medicalSupply.getReorderLevel());
        dto.setStorageTemperature(medicalSupply.getStorageTemperature());

        // Convert java.sql.Date to java.util.Date
        if (medicalSupply.getDateAdded() != null) {
            dto.setDateAdded(new java.util.Date(medicalSupply.getDateAdded().getTime()));
        }

        // Set category ID if category exists (convert Long to Integer)
        if (medicalSupply.getCategory() != null) {
            dto.setCategoryID(medicalSupply.getCategory().getCategoryID().intValue());
        }

        // Set health check ID if healthCheckStudent exists
        if (medicalSupply.getHealthCheckStudent() != null) {
            dto.setHealthCheckId(medicalSupply.getHealthCheckStudent().getCheckID());
        }

        return dto;
    }

    private MedicalSupply convertToEntityForCreate(MedicalSupplyDTO dto) {
        MedicalSupply medicalSupply = new MedicalSupply();
        medicalSupply.setSupplyName(dto.getSupplyName());
        medicalSupply.setUnit(dto.getUnit());
        medicalSupply.setQuantityAvailable(dto.getQuantityAvailable());
        medicalSupply.setReorderLevel(dto.getReorderLevel());
        medicalSupply.setStorageTemperature(dto.getStorageTemperature());

        if (dto.getDateAdded() != null) {
            medicalSupply.setDateAdded(new java.sql.Date(dto.getDateAdded().getTime()));
        }

        if (dto.getCategoryID() != null) {
            SupplyCategory category = new SupplyCategory();
            category.setCategoryID(dto.getCategoryID().longValue());
            medicalSupply.setCategory(category);
        }

        if (dto.getHealthCheckId() != null) {
            HealthCheck_Student healthCheck = new HealthCheck_Student();
            healthCheck.setCheckID(dto.getHealthCheckId());
            medicalSupply.setHealthCheckStudent(healthCheck);
        }

        return medicalSupply;
    }

    private MedicalSupply convertToEntityForUpdate(MedicalSupplyDTO dto) {
        MedicalSupply medicalSupply = new MedicalSupply();

        if (dto.getMedicalSupplyID() != null) {
            medicalSupply.setMedicalSupplyId(dto.getMedicalSupplyID());
        }

        medicalSupply.setSupplyName(dto.getSupplyName());
        medicalSupply.setUnit(dto.getUnit());
        medicalSupply.setQuantityAvailable(dto.getQuantityAvailable());
        medicalSupply.setReorderLevel(dto.getReorderLevel());
        medicalSupply.setStorageTemperature(dto.getStorageTemperature());

        // Convert java.util.Date to java.sql.Date
        if (dto.getDateAdded() != null) {
            medicalSupply.setDateAdded(new java.sql.Date(dto.getDateAdded().getTime()));
        }

        if (dto.getCategoryID() != null) {
            SupplyCategory category = new SupplyCategory();
            category.setCategoryID(dto.getCategoryID().longValue());
            medicalSupply.setCategory(category);
        }

        if (dto.getHealthCheckId() != null) {
            HealthCheck_Student healthCheck = new HealthCheck_Student();
            healthCheck.setCheckID(dto.getHealthCheckId());
            medicalSupply.setHealthCheckStudent(healthCheck);
        }

        return medicalSupply;
    }


    public List<MedicalSupplyReportDTO> getMedicalSupplyReport(Integer categoryId) {
        List<MedicalSupply> supplies;
        if (categoryId == null) {
            supplies = medicalSupplyRepository.findAll();
        } else {
            supplies = medicalSupplyRepository.findByCategoryCategoryId(categoryId);
        }

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

    @Override
    public List<MedicalSupplyDTO> getAllMedicalSupply() {
        List<MedicalSupply> medicalSupplies = medicalSupplyRepository.findAll();
        return medicalSupplies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MedicalSupplyDTO addMedicalSupply(MedicalSupplyDTO dto) {
        MedicalSupply medicalSupply = convertToEntityForCreate(dto);
        MedicalSupply savedSupply = medicalSupplyRepository.save(medicalSupply);
        return convertToDTO(savedSupply);
    }

    @Override
    public MedicalSupplyDTO updateMedicalSupply(MedicalSupplyDTO dto) {
        MedicalSupply medicalSupply = convertToEntityForUpdate(dto);
        MedicalSupply updatedSupply = medicalSupplyRepository.save(medicalSupply);
        return convertToDTO(updatedSupply);
    }

    @Override
    public MedicalSupplyDTO getMedicalSupplyById(Integer id) {
        MedicalSupply medicalSupply = medicalSupplyRepository.findById(id).orElse(null);
        if (medicalSupply == null) {
            throw new RuntimeException("Medical Supply not found with ID: " + id);
        }
        return convertToDTO(medicalSupply);
    }

    @Override
    public void deleteMedicalSupply(Integer id) {
        if (!medicalSupplyRepository.existsById(id)) {
            throw new RuntimeException("Medical Supply not found with ID: " + id);
        }
        medicalSupplyRepository.deleteById(id);
    }

    @Override
    public List<MedicalSupplyDTO> searchMedicalSupplyByName(String name) {
        // Assuming we need to add this method to repository interface
        List<MedicalSupply> medicalSupplies = medicalSupplyRepository.findBySupplyNameContainingIgnoreCase(name);
        return medicalSupplies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalSupplyDTO> searchMedicalSupplyByCategory(String category) {
        // Tìm kiếm theo tên category
        List<MedicalSupply> medicalSupplies = medicalSupplyRepository.findByCategoryCategoryNameContainingIgnoreCase(category);
        return medicalSupplies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalSupplyDTO> searchMedicalSupplyByCategoryId(Integer categoryId) {
        // Tìm kiếm theo category ID
        List<MedicalSupply> medicalSupplies = medicalSupplyRepository.findByCategoryCategoryId(categoryId);
        return medicalSupplies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Integer id) {
        return medicalSupplyRepository.existsById(id);
    }
}

