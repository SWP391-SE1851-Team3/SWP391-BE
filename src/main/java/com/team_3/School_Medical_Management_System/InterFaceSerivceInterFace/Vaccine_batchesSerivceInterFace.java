package com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace;

import com.team_3.School_Medical_Management_System.DTO.Vaccine_batchesDTO;

import java.util.List;

public interface Vaccine_batchesSerivceInterFace {
    public List<Vaccine_batchesDTO> getAll();
    public Vaccine_batchesDTO getById(int id);
    public void add(Vaccine_batchesDTO batch);
    public void update(Vaccine_batchesDTO batch);
    public Long countTotalBatch();

}

