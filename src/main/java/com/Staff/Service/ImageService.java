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
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class ImageService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffImageRepository staffImageRepository;

    public void saveImage(Long staffId, MultipartFile file) throws IOException {
        Staff staff = findStaffById(staffId);
        validateFileSize(file);

        StaffImage staffImage = createStaffImage(staff, file);
        staffImageRepository.save(staffImage);
    }

    public void updateImage(Long staffId, MultipartFile file) throws IOException {
        Staff staff = findStaffById(staffId);
        validateFileSize(file);

        StaffImage staffImage = findStaffImageById(staffId);
        updateStaffImage(staffImage, file);
        staffImageRepository.save(staffImage);
    }

    public byte[] getImageByStaffId(Long staffId) {
        StaffImage staffImage = findStaffImageById(staffId);
        return staffImage.getImage();
    }

    // Private helper methods

    private Staff findStaffById(Long staffId) {
        return staffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff member not found"));
    }

    private StaffImage findStaffImageById(Long staffId) {
        return staffImageRepository.findByStaffId(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Image not found for staff member"));
    }

    private void validateFileSize(MultipartFile file) throws IOException {
        // Implement file size validation logic here
    }

    private StaffImage createStaffImage(Staff staff, MultipartFile file) throws IOException {
        StaffImage staffImage = new StaffImage();
        staffImage.setImage(file.getBytes());
        staffImage.setFilename(file.getOriginalFilename());
        staffImage.setMimetype(file.getContentType());
        staffImage.setUploadDate(LocalDateTime.now());
        staffImage.setStaff(staff);
        return staffImage;
    }

    private void updateStaffImage(StaffImage staffImage, MultipartFile file) throws IOException {
        staffImage.setImage(file.getBytes());
        staffImage.setFilename(file.getOriginalFilename());
        staffImage.setMimetype(file.getContentType());
        staffImage.setUploadDate(LocalDateTime.now());
    }
}
