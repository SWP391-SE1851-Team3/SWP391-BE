package com.team_3.School_Medical_Management_System.Expection;

import com.team_3.School_Medical_Management_System.InterfaceRepo.ParentRepository;
import com.team_3.School_Medical_Management_System.Model.Parent;
import com.team_3.School_Medical_Management_System.Model.Student;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {

    //MultipartFile: là một interface trong Spring Framework dùng
    // để xử lý các file được tải lên (upload) từ client,
    // ví dụ như file Excel, ảnh, PDF... thông qua HTTP request.

    public static List<Student> parseStudentExcel(MultipartFile file, ParentRepository parentRepo) throws IOException {
        List<Student> list = new ArrayList<>();
        //getInputStream : sẽ lấy luồng dữ liệu (dạng nhị phân) từ file đó . xử lí file
        //Workbook:  là class của thư viện Apache POI dùng để đọc file Excel .xlsx, đọc file lên
        //Workbook : giống như quyển sổ
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0); //Sheet: bên trong là 1 trang tính/tab.

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Student student = new Student();
                student.setFullName(row.getCell(0).getStringCellValue());
                student.setGender((int) row.getCell(1).getNumericCellValue());
                student.setClassName(row.getCell(2).getStringCellValue());
                student.setIsActive((int) row.getCell(3).getNumericCellValue());

                String parentFullName = row.getCell(4).getStringCellValue();
                String parentName = row.getCell(5).getStringCellValue();

                String phone = row.getCell(6).getStringCellValue();
                String email = row.getCell(7).getStringCellValue();
                String occupation = row.getCell(8).getStringCellValue();
                String relationship = row.getCell(9).getStringCellValue();

                Parent parent = parentRepo.findByEmail(email).orElseGet(() -> {
                    Parent p = new Parent();
                    p.setUserName(parentName);
                    p.setPassword("123456");
                    p.setFullName(parentFullName);
                    p.setPhone(phone);
                    p.setEmail(email);
                    p.setIsActive(1); // mặc định là active
                    p.setRoleID(1); // giả sử Role 3 = Phụ huynh
                    p.setOccupation(occupation);
                    p.setRelationship(relationship);
                    return parentRepo.save(p);
                });

                student.setParent(parent);
                list.add(student);
            }
        }
        return list;
    }
}

