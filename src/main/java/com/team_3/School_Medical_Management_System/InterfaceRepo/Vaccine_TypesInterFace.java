package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.DTO.VaccineTypeShortDTO;
import com.team_3.School_Medical_Management_System.Model.Vaccine_Types;

import java.util.List;

public interface Vaccine_TypesInterFace {
    public List<Vaccine_Types> getVaccine_Types();
    public VaccineTypeShortDTO getVaccine_Type(int id);
    public Vaccine_Types updateVaccine_Types(Vaccine_Types vaccine_Types);
    public Vaccine_Types deleteVaccine_Types(int id);
    public Vaccine_Types addVaccine_Types(Vaccine_Types vaccine_Types);
    public List<VaccineTypeShortDTO> getVaccine_TypeByName();
    public Vaccine_Types getVaccine_TypeByID(Integer id);
    // public Vaccine_Types getVaccine_TypeByID(Integer id);
    // public Vaccine_Types getVaccine_TypeByID(Integer id);
    // public Vaccine_Types getVaccine_TypeByID(Integer id);
}
