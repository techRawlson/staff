package com.Staff.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class StaffImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] image;
    private String filename;
    private String mimetype;
    private LocalDateTime uploadDate;

    // Other image details, such as filename, mimetype, etc.

    @ManyToOne
    @JoinColumn(name = "staff_id")
    @JsonIgnore
    private Staff staff;
}
