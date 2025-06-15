package com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace;

import com.team_3.School_Medical_Management_System.DTO.VaccinesDTO;
import com.team_3.School_Medical_Management_System.Model.Vaccines;

import java.util.List;

public interface VaccinesServiceInterFace {
    public List<VaccinesDTO> GetAllVaccines();
    public VaccinesDTO GetVaccineByVaccineName(String vaccineName);
    public VaccinesDTO AddVaccine(Vaccines vaccines);
    public VaccinesDTO UpdateVaccine(Vaccines Vaccines);



}
