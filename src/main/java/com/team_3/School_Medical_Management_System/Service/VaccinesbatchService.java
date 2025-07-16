package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.Vaccine_BatchDTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_BatchesDTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_Batches_EditDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.Vaccine_BatchesServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.VaccineBatchRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.Vaccine_BatchesInterFace;
import com.team_3.School_Medical_Management_System.Model.MedicalSupply;
import com.team_3.School_Medical_Management_System.Model.Vaccine_Batches;
import com.team_3.School_Medical_Management_System.Repositories.Vaccine_BatchesRepo;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VaccinesbatchService implements Vaccine_BatchesServiceInterFace {

    @Autowired
    private Vaccine_BatchesRepo vaccineBatch;

    //    @Autowired
//    private MedicalSupplyRepository supplyRepo;
    @Autowired
    private Consent_formsSerivce consentFormService ;


    @Autowired
    private VaccineBatchRepository vaccineBatchRepository;
    private Vaccine_BatchesInterFace vaccinesInterFace;

    @Autowired
    public VaccinesbatchService(Vaccine_BatchesInterFace vaccinesInterFace) {
        this.vaccinesInterFace = vaccinesInterFace;
    }

    @Override
    public List<Vaccine_BatchDTO> GetAllVaccinesbatch() {
        var vaccinesList = vaccinesInterFace.GetAllVaccinesbatch();


        return vaccinesList.stream().map(batch -> {
            Long countAgree = consentFormService.countConsentFormsIsAgreeByBatch(batch.getDot());
            return TransferModelsDTO.MappingVaccine(batch, countAgree);
        }).collect(Collectors.toList());
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


    private String normalizeAndValidate(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " không nhập đúng yêu cầu");
        }
        if (input.startsWith(" ")) {
            throw new IllegalArgumentException(fieldName + " không được bắt đầu bằng khoảng trắng");
        }
        if (!input.matches("[\\p{L}0-9\\s]+")) {
            throw new IllegalArgumentException(fieldName + " không được chứa ký tự đặc biệt");
        }

        char firstChar = input.charAt(0);
        if (Character.isLetter(firstChar) && Character.isLowerCase(firstChar)) {
            throw new IllegalArgumentException(fieldName + " phải bắt đầu bằng chữ cái viết hoa");
        }

        input = input.toLowerCase();
        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) {
                result.append(Character.toUpperCase(w.charAt(0)))
                        .append(w.substring(1))
                        .append(" ");
            }
        }
        return result.toString().trim();
    }

    @Override
    public Vaccine_BatchesDTO AddVaccinebatch(Vaccine_BatchesDTO vaccinesDTO) {
        Optional<Vaccine_Batches> existingBatch = vaccineBatchRepository.findByDot(vaccinesDTO.getDot());
        if (existingBatch.isPresent()) {
            throw new IllegalArgumentException("Dot \"" + vaccinesDTO.getDot() + "\" đã tồn tại. Vui lòng chọn tên khác.");
        }

        String dot = normalizeAndValidate(vaccinesDTO.getDot(), "Dot");
        vaccinesDTO.setDot(dot);

        String location = normalizeAndValidate(vaccinesDTO.getLocation(), "Location");
        vaccinesDTO.setLocation(location);

        String notes = normalizeAndValidate(vaccinesDTO.getNotes(), "Notes");
        vaccinesDTO.setNotes(notes);

        Vaccine_Batches newBatchEntity = TransferModelsDTO.MappingVaccineDTO(vaccinesDTO);
        Vaccine_Batches savedBatch = vaccinesInterFace.AddVaccine_batch(newBatchEntity);
        return TransferModelsDTO.MappingVaccines(savedBatch);

//         int doseCount = vaccinesDTO.getQuantity_received(); // ví dụ: 100 liều
//
//        // Lấy typeID từ DTO (hoặc từ savedBatch)
//        Integer typeId = vaccinesDTO.getVaccineTypeID();
//
//        // Tìm tất cả vật tư liên quan đến loại vaccine đó (thông qua batch.vaccineType)
//        List<MedicalSupply> supplies = supplyRepo.findByVaccineTypeId(typeId);
//
//        for (MedicalSupply supply : supplies) {
//            int used = doseCount; // Mặc định 1 vật tư cho 1 liều
//
//            if (supply.getQuantityAvailable() < used) {
//                throw new RuntimeException("Không đủ vật tư: " + supply.getSupplyName());
//            }
//            // Trừ vật tư
//            supply.setQuantityAvailable(supply.getQuantityAvailable() - used);
//            supplyRepo.save(supply);
//        }


    }


    @Override
    public Vaccine_Batches_EditDTO UpdateVaccinebatch(Vaccine_Batches_EditDTO dto) {
        String dot = normalizeAndValidate(dto.getDot(), "Dot");
        dto.setDot(dot);

        String location = normalizeAndValidate(dto.getLocation(), "Location");
        dto.setLocation(location);

        String notes = normalizeAndValidate(dto.getNotes(), "Notes");
        dto.setNotes(notes);
        var entity = TransferModelsDTO.MappingVaccineBatchesDTO(dto);
        var updatedVaccine = vaccinesInterFace.UpdateVaccine_batch(entity);
//        int doseCount = dto.getQuantity_received(); // ví dụ: 100 liều
//
//        // Lấy typeID từ DTO (hoặc từ savedBatch)
//        Integer typeId = dto.getVaccineTypeID();
//
//        // Tìm tất cả vật tư liên quan đến loại vaccine đó (thông qua batch.vaccineType)
//        List<MedicalSupply> supplies = supplyRepo.findByVaccineTypeId(typeId);
//        for (MedicalSupply supply : supplies) {
//            int used = doseCount; // Mặc định 1 vật tư cho 1 liều
//            if (supply.getQuantityAvailable() < used) {
//                throw new RuntimeException("Không đủ vật tư: " + supply.getSupplyName());
//            }
//            // Trừ vật tư
//            supply.setQuantityAvailable(supply.getQuantityAvailable() - used);
//            supplyRepo.save(supply);
        //}
        return TransferModelsDTO.MappingVaccineBatches(updatedVaccine);
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
//    @Override
//    public void administerVaccine(int vaccineId, int doseCount) {
//        Vaccine_Batches vaccine = vaccineBatch.findById(vaccineId).
//                orElseThrow(() -> new RuntimeException("Không tìm thấy vaccine"));
//        int currvaccines = vaccine.getQuantity_received();
//        if (currvaccines < doseCount) {
//            throw new RuntimeException("Không đủ vaccine để tiêm");
//        }
//        vaccine.setQuantity_received(currvaccines - doseCount);
//        vaccineBatch.save(vaccine);
//
//        List<MedicalSupply> supplies = supplyRepo.findByVaccineBatch(vaccine);
//        for (MedicalSupply supply : supplies) {
//            int used = doseCount; // mỗi liều trừ 1 vật tư (giả định)
//            if (supply.getQuantityAvailable() < used) {
//                throw new RuntimeException("Không đủ vật tư: " + supply.getSupplyName());
//            }
//            supply.setQuantityAvailable(supply.getQuantityAvailable() - used);
//            supplyRepo.save(supply);
//        }
//    }

}


