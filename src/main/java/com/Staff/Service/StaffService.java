package com.Staff.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.Staff.Entities.Staff;
import com.Staff.Entities.StaffDTO;
import com.Staff.Repository.StaffImageRepository;
import com.Staff.Repository.StaffRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public void saveBulkStaff(List<Staff> staffList) {
        List<Staff> staffEntities = staffList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        staffRepository.saveAll(staffEntities);
    }

    private Staff convertToEntity(Staff dto) {
        Staff staff = new Staff();
        staff.setName(dto.getName());
        staff.setGender(dto.getGender());
        staff.setMobile(dto.getMobile());
        staff.setDateOfJoining(dto.getDateOfJoining());
        staff.setAddress(dto.getAddress());
        staff.setDob(dto.getDob());
        staff.setDesignation(dto.getDesignation());
        staff.setEmail(dto.getEmail());
        staff.setStaffId(dto.getStaffId());
        staff.setDepartment(dto.getDepartment());
        return staff;
    }
    @Value("${api.endpoint.url}")
    private String apiUrl;
    public String processExcelFile(MultipartFile file){
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());

            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            List<Staff> staff=new ArrayList<>();
            for(Row row: sheet){
                if(row.getRowNum()==0){
                    continue;
                }
                // Assuming the columns in Excel sheet are ordered according to the fields in the new entity
                String name = row.getCell(0).getStringCellValue();
                String gender = row.getCell(1).getStringCellValue();
                Long mobile = (long) row.getCell(2).getNumericCellValue();
                LocalDate dateOfJoining = row.getCell(3).getLocalDateTimeCellValue().toLocalDate(); // Assuming date of joining is in date format
                String address = row.getCell(4).getStringCellValue();
                LocalDate dob = row.getCell(5).getLocalDateTimeCellValue().toLocalDate(); // Assuming date of birth is in date format
                String designation = row.getCell(6).getStringCellValue();
                String email = row.getCell(7).getStringCellValue();
                String staffId = row.getCell(8).getStringCellValue();
                String department = row.getCell(9).getStringCellValue();

                staff.add(new Staff(name,gender,mobile,dateOfJoining,address,dob,designation,email,staffId,department));

            }
            RestTemplate restTemplate=new RestTemplate();

            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<Staff>> requestEntity=new HttpEntity<>(staff,headers);

            ResponseEntity<String> response=restTemplate.exchange(apiUrl,HttpMethod.POST,requestEntity,String.class);

            if(response.getStatusCode()==HttpStatus.OK){
                return "data uploaded successfully";
            }
            else {
                return "Failed to upload data. Server returned status: " + response.getStatusCodeValue();
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            return "Failed to process Excel file.";
        }

    }

    public List<Staff> getAllStaff(){
        return staffRepository.findAll();
    }
    public List<StaffDTO> getAllStaffDataExceptImgAndSubject() {
        List<Staff> staffList = staffRepository.findAll();
        return staffList.stream()
                .map(StaffDTO::new) // Convert each Staff entity to StaffDTO
                .collect(Collectors.toList());
    }
    public Optional<Staff> getStaffById(Long id) {
        return staffRepository.findById(id);
    }
    @Autowired
    private StaffImageRepository staffImageRepository;
    public void deleteStaffById(Long staffId) {
        // Find the student by ID
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + staffId));

        // Delete associated images
        if (staff.getImages() != null && !staff.getImages().isEmpty()) {
            staffImageRepository.deleteAll(staff.getImages());
        }

        // Delete the student
        staffRepository.delete(staff);
    }

}
