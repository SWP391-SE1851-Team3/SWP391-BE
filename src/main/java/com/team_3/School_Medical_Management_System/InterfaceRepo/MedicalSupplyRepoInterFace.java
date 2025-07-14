package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicalSupply;

import java.util.List;

public interface MedicalSupplyRepoInterFace {
    public List<MedicalSupply> getAllMedicalSupply();
    public MedicalSupply addMedicalSupply(MedicalSupply ms);
    public MedicalSupply updateMedicalSupply(MedicalSupply ms);
    public MedicalSupply getMedicalSupplyById(Integer id);
    public void deleteMedicalSupply(Integer id);
    public List<MedicalSupply> searchMedicalSupplyByName(String name);
    public List<MedicalSupply> searchMedicalSupplyByCategory(String category);
}
