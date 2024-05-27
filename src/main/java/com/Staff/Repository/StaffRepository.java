package com.Staff.Repository;

import com.Staff.Entities.Staff;
import com.Staff.Entities.StaffCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    boolean existsByStaffId(String staffId);
    boolean existsByEmail(String email);
    Optional<Staff> findById(Long id);
    List<Staff> findBySubjectsContainingIgnoreCase(String subject);
    @Query("SELECT COUNT(s) > 0 FROM Staff s WHERE s.staffId = :staffId AND s.password = :password")
    boolean isValidStaff(@Param("staffId") String staffId, @Param("password") String password);
    Optional<StaffCredentials> findByStaffIdAndPassword(String staffId, String password);
}
