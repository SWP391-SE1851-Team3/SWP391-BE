package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.VaccinesDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.VaccinesServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.VaccinesInterFace;
import com.team_3.School_Medical_Management_System.Model.Vaccines;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VaccinesService implements VaccinesServiceInterFace {
    private VaccinesInterFace vaccinesInterFace;

    @Autowired
    public VaccinesService(VaccinesInterFace vaccinesInterFace) {
        this.vaccinesInterFace = vaccinesInterFace;
    }

    @Override
    public List<VaccinesDTO> GetAllVaccines() {
        var vaccinesList = vaccinesInterFace.GetAllVaccines();
        return vaccinesList.stream().map(TransferModelsDTO::MappingVaccines).collect(Collectors.toList());
    }


    @Override
    public VaccinesDTO GetVaccineByVaccineName(String vaccineName) {
        var vaccinesList = vaccinesInterFace.GetVaccineByVaccineName(vaccineName);
        if (vaccinesList == null) {
            return null;
        } else {
            return TransferModelsDTO.MappingVaccines(vaccinesList);
        }
    }

    @Override
    public VaccinesDTO AddVaccine(VaccinesDTO vaccinesDTO) {
        var vaccine = vaccinesInterFace.AddVaccine(TransferModelsDTO.MappingVaccineDTO(vaccinesDTO));
        return TransferModelsDTO.MappingVaccines(vaccine);
    }

    @Override
    public VaccinesDTO UpdateVaccine(VaccinesDTO vaccinesDTO) {
        var updatedVaccine = vaccinesInterFace.UpdateVaccine(TransferModelsDTO.MappingVaccineDTO(vaccinesDTO));
        if (updatedVaccine != null) {
            return TransferModelsDTO.MappingVaccines(updatedVaccine);
        }
        return null;
    }
}


