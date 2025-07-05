package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicalSupply;

import java.util.List;

public interface MedicalSupplyRepository {
    public List<MedicalSupply> findByQuantityAvailableLessThanReorderLevel(); // tìm những MedicalS có Quantity < ReorderLevel

    public   List<MedicalSupply> findByCategoryCategoryId(Integer categoryId);
    public List<MedicalSupply> findAll();// lấy tất cả MedicalSupply

    public MedicalSupply findById(Integer id);

    public  void save(MedicalSupply m);// tìm MedicalSupply theo id

}
