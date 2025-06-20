package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.Vaccine_batchesDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Vaccine_batchesSerivceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.Vaccine_batchesInterFace;
import com.team_3.School_Medical_Management_System.Repositories.VaccinesRepo;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO.MappingVaccineBatchDTO;

@Service
@Transactional
public class Vaccine_batchesService implements Vaccine_batchesSerivceInterFace {

    private Vaccine_batchesInterFace vaccine_batchesService;

    @Autowired
    private VaccinesRepo vaccinesRepo;

    @Autowired
    public Vaccine_batchesService(Vaccine_batchesInterFace vaccine_batchesService) {
        this.vaccine_batchesService = vaccine_batchesService;
    }

    @Override
    public List<Vaccine_batchesDTO> getAll() {
       var list = vaccine_batchesService.getAll();
       return list.stream().map(TransferModelsDTO :: MappingVaccineBatchDTO).collect(Collectors.toList());
    }

    @Override
    public Vaccine_batchesDTO getById(int id) {
        var vaccineBatchesId = vaccine_batchesService.getById(id);
        return MappingVaccineBatchDTO(vaccineBatchesId);

    }

    @Override
    public void add(Vaccine_batchesDTO batch) {
        var p = vaccinesRepo.GetVaccineByVaccineId(batch.getVaccine_id());
        if(p != null) {
            vaccine_batchesService.add(TransferModelsDTO.MappingVaccineBatch(batch));
        }else{
            throw new RuntimeException("Vaccine_id is not found");
        }

    }

    @Override
    public void update(Vaccine_batchesDTO batch) {
        vaccine_batchesService.update(TransferModelsDTO.MappingVaccineBatch(batch));
    }

    @Override
    public Long countTotalBatch() {
        return vaccine_batchesService.countTotalBatch();
    }


}
