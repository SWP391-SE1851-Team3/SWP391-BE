package com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace;


import com.team_3.School_Medical_Management_System.DTO.VaccineTypeShortDTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_TypesDTO;
import com.team_3.School_Medical_Management_System.Model.Vaccine_Types;

import java.util.List;

public interface Vaccine_TypesServiceInterFace {
    public List<Vaccine_TypesDTO> getVaccine_Types();
    public VaccineTypeShortDTO getVaccineType(int id);
    public Vaccine_TypesDTO updateVaccine_Types(Vaccine_TypesDTO vaccine_TypesDTO);
    public Vaccine_TypesDTO deleteVaccine_Types(int id);
    public Vaccine_TypesDTO addVaccine_Types(Vaccine_TypesDTO vaccine_TypesDTO);
    public List<VaccineTypeShortDTO> getVaccine_TypeByName();
}
