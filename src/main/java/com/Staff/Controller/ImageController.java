package com.Staff.Controller;

import com.Staff.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/StaffImage")
public class ImageController {
    @Autowired
    private ImageService imageService;
    @PostMapping("/{staffId}")
    public ResponseEntity<String> uploadImage(@PathVariable Long staffId, @RequestParam("file") MultipartFile file) {
        try {
            imageService.saveImage(staffId, file);
            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }
    @PutMapping("/{staffId}")
    public ResponseEntity<String> updateImage(@PathVariable Long staffId, @RequestParam("file") MultipartFile file) {
        try {
            imageService.updateImage(staffId, file);
            return ResponseEntity.ok("Image updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update image: " + e.getMessage());
        }
    }

    // GET method for retrieving image data by student ID
    @GetMapping("/{staffId}")
    public ResponseEntity<byte[]> getImageByStudentId(@PathVariable Long staffId) {
        try {
            byte[] imageData = imageService.getImageByStaffId(staffId);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(("Failed to retrieve image: " + e.getMessage()).getBytes());
        }
    }
}
