package com.team_3.School_Medical_Management_System.Service;
import com.team_3.School_Medical_Management_System.DTO.VaccineTypeShortDTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_TypesDTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_Types_Edit_DTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.Vaccine_TypesServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.Vaccine_TypesInterFace;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class Vaccine_TypesService implements Vaccine_TypesServiceInterFace {

    private Vaccine_TypesInterFace vaccine_typesInterFace;
    @Autowired
    public Vaccine_TypesService(Vaccine_TypesInterFace vaccine_typesInterFace) {
        this.vaccine_typesInterFace = vaccine_typesInterFace;
    }

    @Override
    public List<Vaccine_TypesDTO> getVaccine_Types() {
        var result = vaccine_typesInterFace.getVaccine_Types();
        return result.stream().map(TransferModelsDTO::MappingVaccineTypes).collect(Collectors.toList());
    }

    @Override
    public VaccineTypeShortDTO getVaccineType(int id) {
        var result = vaccine_typesInterFace.getVaccine_Type(id);
        if (result == null) {
            throw new RuntimeException ("Vaccine type not found with id: " + id);
        }
        return result;
    }

    @Override
    public Vaccine_Types_Edit_DTO updateVaccine_Types(Vaccine_Types_Edit_DTO vaccine_types_edit_dto) {
        var updateVaccineType = vaccine_typesInterFace.updateVaccine_Types(TransferModelsDTO.MappingVaccineTypesEditDTO(vaccine_types_edit_dto));
        return TransferModelsDTO.MappingVaccineTypesEdit(updateVaccineType);
    }

    @Override
    public Vaccine_TypesDTO deleteVaccine_Types(int id) {
        return TransferModelsDTO.MappingVaccineTypes(vaccine_typesInterFace.deleteVaccine_Types(id));
    }

    @Override
    public Vaccine_TypesDTO addVaccine_Types(Vaccine_TypesDTO vaccine_TypesDTO) {
        var addVaccines_type = vaccine_typesInterFace.addVaccine_Types(TransferModelsDTO.MappingVaccineTypesDTO(vaccine_TypesDTO));
        return TransferModelsDTO.MappingVaccineTypes(addVaccines_type);
    }

    @Override
    public List<VaccineTypeShortDTO> getVaccine_TypeByName() {
        return vaccine_typesInterFace.getVaccine_TypeByName();
    }

    @Override
    public Vaccine_TypesDTO getVaccine_TypeByID(Integer id) {
        var getVaccineByID = vaccine_typesInterFace.getVaccine_TypeByID(id);
        if (getVaccineByID == null) {
            throw new RuntimeException ("Vaccine type not found with id: " + id);
        }else {
            return TransferModelsDTO.MappingVaccineTypes(getVaccineByID);
        }
    }
}
