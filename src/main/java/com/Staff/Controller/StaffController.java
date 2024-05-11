package com.Staff.Controller;

import com.Staff.Entities.Staff;
import com.Staff.Entities.StaffDTO;
import com.Staff.Entities.Subject;
import com.Staff.Repository.StaffRepository;
import com.Staff.Repository.SubjectRepository;
import com.Staff.Service.StaffService;
import com.Staff.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//http://192.168.1.10:8083/api/staff
@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
   private StaffRepository staffRepository;

    @Autowired
    private  StaffService staffService;


    @GetMapping("/saved-Staff")
    public ResponseEntity<List<StaffDTO>> getAllStaffDataExceptImgAndSubject() {
        try {
            List<StaffDTO> staffDTOs = staffService.getAllStaffDataExceptImgAndSubject();
            return ResponseEntity.ok(staffDTOs);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            // You can add a logger here
            e.printStackTrace();
            // Return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }






//    @PostMapping("/create-staff")
//    public Staff createStudent(@RequestParam("name") String name,
//                               @RequestParam("gender") String gender,
//                               @RequestParam("mobile") Long mobile,
//                               @RequestParam("address") String address,
//                               @RequestParam("designation")String designation,
//                               @RequestParam("date_of_joining") LocalDate date_of_joining,
//                               @RequestParam("staff_id") String staff_id,
//                               @RequestParam("department") String department,
//
//                               @RequestParam("dob") LocalDate dob,
//
//                               @RequestParam("email") String email,
//
//
//                               @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture) throws IOException {
//
//        // Save the image if provided
//        String profilePictureName = null;
//        if (profilePicture != null && !profilePicture.isEmpty()) {
//            profilePictureName = profilePicture.getOriginalFilename();
//            byte[] bytes = profilePicture.getBytes();
//            Path path = Paths.get(IMAGE_UPLOAD_DIR + "\\" + profilePictureName);
//            Files.write(path, bytes);
//        }
//
//        // Create the student object
//        Staff staff = new Staff();
//        staff.setName(name);
//        staff.setMobile(mobile);
//        staff.setAddress(address);
//        staff.setDob(dob);
//        staff.setDesignation(designation);
//        staff.setGender(gender);
//        staff.setDepartment(department);
//        staff.setEmail(email);
//        staff.setDesignation(designation);
//        staff.setStaffId(staff_id);
//        staff.setDateOfJoining(date_of_joining);
//
//        staff.setProfilePicture(profilePictureName);
//
//        staff=staffRepository.save(staff);
//
//        // Save the student object to the database or perform any other necessary operations
//        // studentRepository.save(student);
//
//        return staff;
//    }

//    @PostMapping("/create-staff")
//    public Staff createStudent(@RequestParam("name") String name,
//                               @RequestParam("gender") String gender,
//                               @RequestParam("mobile") Long mobile,
//                               @RequestParam("address") String address,
//                               @RequestParam("designation") String designation,
//                               @RequestParam("date_of_joining") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_of_joining,
//                               @RequestParam("staff_id") String staff_id,
//                               @RequestParam("department") String department,
//                               @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob,
//                               @RequestParam("email") String email,
//                               @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture) throws IOException {
//
//        // Save the image if provided
//        String profilePictureName = null;
//        if (profilePicture != null && !profilePicture.isEmpty()) {
//            profilePictureName = profilePicture.getOriginalFilename();
//            byte[] bytes = profilePicture.getBytes();
//            Path path = Paths.get(IMAGE_UPLOAD_DIR + "\\" + profilePictureName);
//            Files.write(path, bytes);
//        }
//
//        // Create the student object
//        Staff staff = new Staff();
//        staff.setName(name);
//        staff.setMobile(mobile);
//        staff.setAddress(address);
//        staff.setDob(dob);
//        staff.setDesignation(designation);
//        staff.setGender(gender);
//        staff.setDepartment(department);
//        staff.setEmail(email);
//        staff.setDesignation(designation);
//        staff.setStaffId(staff_id);
//        staff.setDateOfJoining(date_of_joining);
//        staff.setProfilePicture(profilePictureName);
//
//        staff = staffRepository.save(staff);
//
//        return staff;
//    }







    @PostMapping("/create-staff")
    public ResponseEntity<?> createStaff(@RequestParam("name") String name,
                                         @RequestParam("gender") String gender,
                                         @RequestParam("mobile") Long mobile,
                                         @RequestParam("address") String address,
                                         @RequestParam("designation") String designation,
                                         @RequestParam("date_of_joining") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_of_joining,
                                         @RequestParam("staff_id") String staff_id,
                                         @RequestParam("department") String department,
                                         @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob,
                                         @RequestParam("email") String email,
                                         @RequestParam(value = "subjects", required = false) List<String> subjects) throws IOException {

        // Check if the email already exists
        if (staffRepository.existsByEmail(email) || dob.isEqual(date_of_joining)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email already exists");
        }
        // Check if the birthdate is before the joining date
        if (dob.isAfter(date_of_joining) ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Birthdate must be before the joining date");
        }

        // Check if the staff ID already exists
        if (staffRepository.existsByStaffId(staff_id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Staff ID already exists");
        }

        // Save the profile picture if provided


        // Create the staff object
        Staff staff=new Staff();
        staff.setName(name);
        staff.setGender(gender);
        staff.setMobile(mobile);
        staff.setAddress(address);
        staff.setDob(dob);
        staff.setDesignation(designation);
        staff.setStaffId(staff_id);
        staff.setDepartment(department);
        staff.setEmail(email);
        staff.setDateOfJoining(date_of_joining);


        // Associate subjects with the staff member if provided
        if (subjects != null && !subjects.isEmpty()) {
            staff.getSubjects().addAll(subjects);
        }

        // Save the staff object to the database
        staff = staffRepository.save(staff);

        return ResponseEntity.status(HttpStatus.CREATED).body(staff);
    }






    @GetMapping("/{id}")
    public ResponseEntity<?> getStaffById(@PathVariable Long id) {
        Optional<Staff> staffOptional = staffService.getStaffById(id);
        if (staffOptional.isPresent()) {
            return ResponseEntity.ok(staffOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }




//    @PutMapping("/update/{id}")
//    public Staff updateStaff(@PathVariable Long id, @RequestBody Staff staffDetails) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        Staff staff = staffRepository.findById(id).orElse(null);
//        if (staff != null) {
//            staff.setName(staffDetails.getName());
//            staff.setGender(staffDetails.getGender());
//            staff.setMobile(staffDetails.getMobile());
//            staff.setDateOfJoining(staffDetails.getDateOfJoining());
//            staff.setAddress(staffDetails.getAddress());
//            staff.setDob(staffDetails.getDob());
//            staff.setDesignation(staffDetails.getDesignation());
//            staff.setEmail(staffDetails.getEmail());
//            staff.setStaffId(staffDetails.getStaffId());
//            staff.setDepartment(staffDetails.getDepartment());
//
//            return staffRepository.save(staff);
//        }
//        return null;
//    }

    @DeleteMapping("/delete/{id}")
    public String deleteStaff(@PathVariable Long id) {
        staffRepository.deleteById(id);
        return "Staff with ID: " + id + " deleted successfully.";
    }
    @PostMapping("/bulk")
    public ResponseEntity<String> uploadBulk(@RequestBody List<Staff> staff) {
        try {
            staffService.saveBulkStaff(staff);
            return ResponseEntity.status(HttpStatus.OK).body("Bulk staff data uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload");
        }
    }



    @PutMapping("update/{id}")
    public ResponseEntity<Staff> updateStudentById(@PathVariable("id") Long id,
                                                   @RequestParam(value = "name",required = false) String name,
                                                   @RequestParam(value = "gender",required = false) String gender,
                                                   @RequestParam(value = "mobile",required = false) Long mobile,
                                                   @RequestParam(value = "address",required = false) String address,
                                                   @RequestParam(value = "designation",required = false)String designation,
                                                   @RequestParam(value = "date_of_joining",required = false) LocalDate date_of_joining,
                                                   @RequestParam(value = "staff_id",required = false) String staff_id,
                                                   @RequestParam(value = "department",required = false) String department,
                                                   @RequestParam(value = "dob",required = false) LocalDate dob,
                                                   @RequestParam(value = "email",required = false) String email,
                                                   @RequestParam(value = "subjects", required = false) List<String> subjects)


                                                    {
        Optional<Staff> optionalStaff = staffRepository.findById(id);
        if (optionalStaff.isPresent()) {
            Staff existingStaff = optionalStaff.get();

            // Update the image if provided



            // Update other student fields if provided
            if (name != null) {
                existingStaff.setName(name);
            }


            if (gender != null) {
                existingStaff.setGender(gender);
            }
            if (mobile != null) {
                existingStaff.setMobile(mobile);
            }
            if (address != null) {
                existingStaff.setAddress(address);
            }
            if (subjects != null) {
                existingStaff.setSubjects(subjects);
            }
            if (department != null) {
                existingStaff.setDepartment(department);
            }
            if (date_of_joining != null) {
                existingStaff.setDateOfJoining(date_of_joining);
            }
            if (staff_id != null) {
                existingStaff.setStaffId(staff_id);
            }
            if (designation != null) {
                existingStaff.setDesignation(designation);
            }




            if (dob != null) {
                existingStaff.setDob(dob);
            }

            if (email != null) {
                existingStaff.setEmail(email);
            }



            // Save the updated student object to the database
            Staff savedStudent = staffRepository.save(existingStaff);
            return ResponseEntity.ok().body(savedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private SubjectRepository subjectRepository;

//    @PostMapping("/create-subject")
//    public Subject createSubject(@RequestParam("name") String name) {
//        return subjectService.createSubject(name);
//    }
@PostMapping("/create-subject")
public ResponseEntity<?> createSubject(@RequestParam("name") String name) {
    // Check if the subject already exists
    if (subjectService.isSubjectExists(name)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Subject already exists");
    }

    // If the subject doesn't exist, create it
    Subject subject = subjectService.createSubject(name);
    return ResponseEntity.status(HttpStatus.CREATED).body(subject);
}


    @GetMapping("/all-subjects")
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/subject/{id}")
    public Optional<Subject> getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id);
    }
    @DeleteMapping("/delete-Subject/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        try {
            subjectRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload-excel")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file) {
        return staffService.processExcelFile(file);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long id) {
        try {
            staffService.deleteStaffById(id);
            return ResponseEntity.ok("staff with ID " + id + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete staff with ID " + id + ": " + e.getMessage());
        }
    }

}

