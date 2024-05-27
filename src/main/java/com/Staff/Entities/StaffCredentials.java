package com.Staff.Entities;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class StaffCredentials {
    @Id
    private String staffId;
    private String password;
}