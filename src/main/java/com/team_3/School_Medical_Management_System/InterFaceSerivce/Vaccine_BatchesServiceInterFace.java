package com.team_3.School_Medical_Management_System.InterFaceSerivce;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_BatchDTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_BatchesDTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_Batches_EditDTO;

import java.util.List;

public interface Vaccine_BatchesServiceInterFace {
    public List<Vaccine_BatchDTO> GetAllVaccinesbatch();
    public Vaccine_BatchesDTO GetVaccineByVaccineName(String vaccineName);
    public Vaccine_BatchesDTO AddVaccinebatch(Vaccine_BatchesDTO vaccinesDTO);
    public Vaccine_Batches_EditDTO UpdateVaccinebatch(Vaccine_Batches_EditDTO dto);
    public Vaccine_BatchesDTO getVaccineByID(Integer vaccineID);
    public boolean updateConsentFormStatus(int consentFormId, String status);

//    public void administerVaccine(int vaccineId, int doseCount);
}
