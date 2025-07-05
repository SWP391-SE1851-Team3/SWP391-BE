package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicalSupply;


import java.util.List;

public interface MedicalSupplyRepository {
    public List<MedicalSupply> findByQuantityAvailableLessThanReorderLevel(); // tìm những MedicalS có Quantity < ReorderLevel

    public   List<MedicalSupply> findByCategoryCategoryId(Integer categoryId);
    public List<MedicalSupply> findAll();// lấy tất cả MedicalSupply

    public MedicalSupply findById(Integer id);

    public  void save(MedicalSupply m);// tìm MedicalSupply theo id

//=======
//import com.team_3.School_Medical_Management_System.Model.Vaccine_Batches;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface MedicalSupplyRepository extends JpaRepository<MedicalSupply, Integer> {
////    List<MedicalSupply> findByVaccineBatch(Vaccine_Batches vaccineBatch);
//    @Query("SELECT ms FROM MedicalSupply ms WHERE ms.vaccineType.VaccineTypeID = :typeId")
//    List<MedicalSupply> findByVaccineTypeId(@Param("typeId") Integer typeId);
//>>>>>>> 0e0489f240b459f168dafc724b5e07a210c27d6f
}
