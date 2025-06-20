package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.DTO.VaccinesDTO;
import com.team_3.School_Medical_Management_System.Model.Vaccines;

import java.util.List;

public interface VaccinesInterFace {
    public List<Vaccines> GetAllVaccines();
    public Vaccines GetVaccineByVaccineName(String vaccineName);
    public Vaccines GetVaccineByVaccineId(int VaccineId);
    public Vaccines AddVaccine(Vaccines vaccine);
    public Vaccines UpdateVaccine(Vaccines vaccine);


}

