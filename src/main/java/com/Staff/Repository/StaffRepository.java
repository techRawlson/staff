package com.Staff.Repository;

import com.Staff.Entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    boolean existsByStaffId(String staffId);
    boolean existsByEmail(String email);
    Optional<Staff> findById(Long id);
}
