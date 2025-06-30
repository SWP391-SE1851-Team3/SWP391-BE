package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.DTO.Consent_form_dot;
import com.team_3.School_Medical_Management_System.Model.Vaccine_Batches;

import java.util.List;

public interface Vaccine_BatchesInterFace {
    public List<Vaccine_Batches> GetAllVaccinesbatch();
    public Vaccine_Batches GetVaccineByVaccineName(String vaccineName);
    public Vaccine_Batches GetVaccineByVaccineId(Integer VaccineId);
    public Vaccine_Batches AddVaccine_batch(Vaccine_Batches vaccine);
    public Vaccine_Batches UpdateVaccine_batch(Vaccine_Batches vaccine);
    public boolean updateConsentFormStatus(int consentFormId, String status);
}

