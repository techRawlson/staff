package com.Staff.Controller;

import com.Staff.Entities.LoginRecord;
import com.Staff.Entities.StaffCredentials;
import com.Staff.Repository.LoginRecordRepository;
import com.Staff.Repository.StaffRepository;
import com.Staff.Service.LoginCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/staff-login")
public class StaffLoginController {
    @Autowired
    private LoginRecordRepository loginRecordRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private LoginCredentialService staffService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody StaffCredentials staffCredentials) {
        // Validate staff ID and password against the repository
        if (isValidStaff(staffCredentials)) {
            // Check if a login record already exists for the staff ID
            LoginRecord existingRecord = loginRecordRepository.findByStaffId(staffCredentials.getStaffId());
            if (existingRecord == null) {
                // Save login record only if it doesn't exist
                LoginRecord loginRecord = new LoginRecord();
                loginRecord.setStaffId(staffCredentials.getStaffId());
                loginRecord.setLoginTime(LocalDateTime.now());
                loginRecordRepository.save(loginRecord);
            }

            // Retrieve approver name
            String approverName = staffService.getApproverNameByStaffId(staffCredentials.getStaffId());
            String staffName= staffService.getStaffNameByStaffId(staffCredentials.getStaffId());
            // Include userId and approver name in the response
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Login successful!");
            responseBody.put("userId", staffCredentials.getStaffId());
            responseBody.put("approverName", approverName);
            responseBody.put("staffName", staffName);
            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials. Please try again.");
        }
    }
    private boolean isValidStaff(StaffCredentials staffCredentials) {
        // Implement logic to check if the staff ID and password are valid
        // For example, you can use StaffRepository to validate credentials
        return staffRepository.isValidStaff(staffCredentials.getStaffId(), staffCredentials.getPassword());
    }
}
