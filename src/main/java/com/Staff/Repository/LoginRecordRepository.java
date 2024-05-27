package com.Staff.Repository;


import com.Staff.Entities.LoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRecordRepository extends JpaRepository<LoginRecord, Long> {
    LoginRecord findByStaffId(String staffId);
}