package com.Staff.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    @OneToMany(mappedBy = "staff")
    private List<StaffImage> images;







    @ElementCollection

    @CollectionTable(name = "staff_subjects", joinColumns = @JoinColumn(name = "staff_id"))
    @Column(name = "subject")
    private List<String> subjects = new ArrayList<>();

    public Staff(String name, String gender, Long mobile, LocalDate dateOfJoining, String address,
                 LocalDate dob, String designation, String email, String staffId, String department) {
        this.name = name;
        this.gender = gender;
        this.mobile = mobile;
        this.dateOfJoining = dateOfJoining;
        this.address = address;
        this.dob = dob;
        this.designation = designation;
        this.email = email;
        this.staffId = staffId;
        this.department = department;
    }
}
