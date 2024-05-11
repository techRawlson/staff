package com.Staff.Entities;

import jakarta.persistence.*;
import lombok.Data;




@Data
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Subject(String name) {
        this.name = name;
    }

    // default constructor for JPA
    public Subject() {}
}
