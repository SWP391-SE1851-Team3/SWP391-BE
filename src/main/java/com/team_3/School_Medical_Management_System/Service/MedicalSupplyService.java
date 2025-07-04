package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.MedicalSupplyDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.MedicalSupplyServiceInterFace;
import com.team_3.School_Medical_Management_System.Repositories.MedicalSupplyRepo;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicalSupplyService implements MedicalSupplyServiceInterFace {
    private MedicalSupplyRepo medicalSupplyRepo;
    @Autowired
    public MedicalSupplyService(MedicalSupplyRepo medicalSupplyRepo) {
        this.medicalSupplyRepo = medicalSupplyRepo;
    }
    @Override
    public List<MedicalSupplyDTO> getAllMedicalSupply() {
       var p = medicalSupplyRepo.getAllMedicalSupply();
       return p.stream().map(TransferModelsDTO :: mapMedicalSupplyDTO).collect(Collectors.toList());
    }

    @Override
    public MedicalSupplyDTO addMedicalSupply(MedicalSupplyDTO ms) {
      var p =   medicalSupplyRepo.addMedicalSupply(TransferModelsDTO.mapToMedicalSupply(ms));
        return TransferModelsDTO.mapMedicalSupplyDTO(p);


    }

    @Override
    public MedicalSupplyDTO updateMedicalSupply(MedicalSupplyDTO ms) {
        var p =   medicalSupplyRepo.updateMedicalSupply(TransferModelsDTO.mapToMedicalSupply(ms));
        return TransferModelsDTO.mapMedicalSupplyDTO(p);
    }
}
