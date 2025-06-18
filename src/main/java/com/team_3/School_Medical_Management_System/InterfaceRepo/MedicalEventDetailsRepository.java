package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicalEventDetails;
import com.team_3.School_Medical_Management_System.Model.MedicalEventDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;


// dựa vào các interface này thì có thể tìm kiếm sự chung giữa 2 bảng đó
public interface MedicalEventDetailsRepository extends JpaRepository<MedicalEventDetails, MedicalEventDetailsId> {


}
