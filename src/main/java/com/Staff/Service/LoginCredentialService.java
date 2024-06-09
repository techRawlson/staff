package com.Staff.Service;

import com.Staff.Entities.Staff;
import com.Staff.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginCredentialService {
    @Autowired
    private StaffRepository staffRepository;
    public String getApproverNameByStaffId(String staffId) {
        return staffRepository.findByStaffId(staffId)
                .map(Staff::getApprover)
                .orElse("Approver not found");
    }
    public String getStaffNameByStaffId(String staffId){
        return staffRepository.findByStaffId(staffId)
                .map(Staff::getName)
                .orElse("Name not found");
    }
}
