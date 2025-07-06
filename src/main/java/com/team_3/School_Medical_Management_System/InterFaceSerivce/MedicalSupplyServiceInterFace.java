package com.team_3.School_Medical_Management_System.InterFaceSerivce;

import com.team_3.School_Medical_Management_System.DTO.MedicalSupplyDTO;

import java.util.List;

public interface MedicalSupplyServiceInterFace {
    public List<MedicalSupplyDTO> getAllMedicalSupply();
    public MedicalSupplyDTO addMedicalSupply(MedicalSupplyDTO ms);
    public MedicalSupplyDTO updateMedicalSupply(MedicalSupplyDTO ms);
}
