package com.Staff.Repository;

import com.Staff.Entities.StaffImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface StaffImageRepository extends JpaRepository<StaffImage,Long> {
    Optional<StaffImage> findByStaffId(Long staffId);
}
