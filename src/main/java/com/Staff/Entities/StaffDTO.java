package com.Staff.Entities;

import com.Staff.Repository.StaffImageRepository;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class StaffDTO {
    private Long id;
    private String name;
    private String gender;
    private Long mobile;
    private LocalDate dateOfJoining;
    private String address;
    private LocalDate dob;
    private String designation;
    private String email;
    private String staffId;
    private String department;

    public StaffDTO(Staff staff) {
        this.id = staff.getId();
        this.name = staff.getName();
        this.gender = staff.getGender();
        this.mobile = staff.getMobile();
        this.dateOfJoining = staff.getDateOfJoining();
        this.address = staff.getAddress();
        this.dob = staff.getDob();
        this.designation = staff.getDesignation();
        this.email = staff.getEmail();
        this.staffId = staff.getStaffId();
        this.department = staff.getDepartment();
    }
}
