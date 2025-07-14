package com.team_3.School_Medical_Management_System.InterFaceSerivce;

import com.team_3.School_Medical_Management_System.DTO.MedicalSupplyDTO;
import com.team_3.School_Medical_Management_System.DTO.SupplyCategoryDTO;

import java.util.List;

public interface MedicalSupplyServiceInterFace {
    List<MedicalSupplyDTO> getAllMedicalSupply();
    MedicalSupplyDTO addMedicalSupply(MedicalSupplyDTO ms);
    MedicalSupplyDTO updateMedicalSupply(MedicalSupplyDTO ms);
    MedicalSupplyDTO getMedicalSupplyById(Integer id);
    void deleteMedicalSupply(Integer id);
    List<MedicalSupplyDTO> searchMedicalSupplyByName(String name);
    List<MedicalSupplyDTO> searchMedicalSupplyByCategory(String category);
    List<MedicalSupplyDTO> searchMedicalSupplyByCategoryId(Integer categoryId);
    boolean existsById(Integer id);
    List<SupplyCategoryDTO> getAllCategories();
}
