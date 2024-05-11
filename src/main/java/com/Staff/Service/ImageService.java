package com.Staff.Service;

import com.Staff.Entities.Staff;
import com.Staff.Entities.StaffImage;
import com.Staff.Repository.StaffImageRepository;
import com.Staff.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
@Service
public class ImageService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffImageRepository staffImageRepository;

    public void saveImage(Long staffId, MultipartFile file) throws IOException {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        StaffImage staffImage = new StaffImage();
        staffImage.setImage(file.getBytes());
        staffImage.setFilename(file.getOriginalFilename());
        staffImage.setMimetype(file.getContentType());
        staffImage.setUploadDate(LocalDateTime.now());
        staffImage.setStaff(staff);

        staffImageRepository.save(staffImage);
    }
    public void updateImage(Long staffId, MultipartFile file) throws IOException {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        StaffImage staffImage = staffImageRepository.findByStaffId(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Image not found for student"));

        staffImage.setImage(file.getBytes());
        staffImage.setFilename(file.getOriginalFilename());
        staffImage.setMimetype(file.getContentType());
        staffImage.setUploadDate(LocalDateTime.now());

        staffImageRepository.save(staffImage);
    }
    public byte[] getImageByStaffId(Long staffId) {
        StaffImage studentImage = staffImageRepository.findByStaffId(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Image not found for student"));

        return studentImage.getImage();
    }
}
