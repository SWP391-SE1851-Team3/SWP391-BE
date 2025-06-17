package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Vaccine_batches;

import java.util.List;

public interface Vaccine_batchesInterFace {
    public List<Vaccine_batches> getAll();
    public Vaccine_batches getById(int id);
    public void add(Vaccine_batches batch);
    public void update(Vaccine_batches batch);
    public Long countTotalBatch();



}
