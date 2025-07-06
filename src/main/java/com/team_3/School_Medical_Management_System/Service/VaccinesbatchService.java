package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.Vaccine_BatchesDTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_Batches_EditDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.Vaccine_BatchesServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.Vaccine_BatchesInterFace;
import com.team_3.School_Medical_Management_System.Model.Vaccine_Batches;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VaccinesbatchService implements Vaccine_BatchesServiceInterFace {
    private Vaccine_BatchesInterFace vaccinesInterFace;

    @Autowired
    public VaccinesbatchService(Vaccine_BatchesInterFace vaccinesInterFace) {
        this.vaccinesInterFace = vaccinesInterFace;
    }

    @Override
    public List<Vaccine_BatchesDTO> GetAllVaccinesbatch() {
        var vaccinesList = vaccinesInterFace.GetAllVaccinesbatch();
        return vaccinesList.stream().map(TransferModelsDTO::MappingVaccines).collect(Collectors.toList());
    }


    @Override
    public Vaccine_BatchesDTO GetVaccineByVaccineName(String vaccineName) {
        var vaccinesList = vaccinesInterFace.GetVaccineByVaccineName(vaccineName);
        if (vaccinesList == null) {
            return null;
        } else {
            return TransferModelsDTO.MappingVaccines(vaccinesList);
        }
    }

    @Override
    public Vaccine_BatchesDTO AddVaccinebatch(Vaccine_BatchesDTO vaccinesDTO) {
        var vaccine = vaccinesInterFace.AddVaccine_batch(TransferModelsDTO.MappingVaccineDTO(vaccinesDTO));
        return TransferModelsDTO.MappingVaccines(vaccine);
    }

    @Override
    public Vaccine_Batches_EditDTO UpdateVaccinebatch(Vaccine_Batches_EditDTO dto) {
        Vaccine_Batches entity = TransferModelsDTO.MappingVaccineBatchesDTO(dto);
        // Gọi cập nhật qua interface
        Vaccine_Batches updatedVaccine = vaccinesInterFace.UpdateVaccine_batch(entity);
        if (updatedVaccine != null) {
            return TransferModelsDTO.MappingVaccineBatches(updatedVaccine);
        }
        return null;
    }

    @Override
    public Vaccine_BatchesDTO getVaccineByID(Integer vaccineID) {
        var p = vaccinesInterFace.GetVaccineByVaccineId(vaccineID);
        return TransferModelsDTO.MappingVaccines(p);
    }

    @Override
    public boolean updateConsentFormStatus(int bacthId, String status) {
        return vaccinesInterFace.updateConsentFormStatus(bacthId, status);
    }



}


