package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.dto.StaffManagementDTO;
import com.elevata.gsrtc.entity.Staff;
import com.elevata.gsrtc.enums.EmploymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer>{
    @Query("""
        SELECT new com.elevata.gsrtc.dto.StaffManagementDTO(
            s.fullName,
            s.stf,
            s.mobileNumber,
            s.licenseNumber,
            s.role,
            s.status,
            d.depotName
        )
        FROM Staff s
        JOIN s.busDepot d
        WHERE d.depotId = :depotId
          AND (:stf IS NULL OR s.stf = :stf)
          AND (:fullName IS NULL
               OR LOWER(s.fullName) LIKE LOWER(CONCAT('%', :fullName, '%')))
          AND (:role IS NULL OR s.role = :role)
          AND (:licenseNumber IS NULL OR s.licenseNumber = :licenseNumber)
          AND (:status IS NULL OR s.status = :status)
    """)
    List<StaffManagementDTO> searchStaff(
            @Param("depotId") int depotId,
            @Param("stf") String stf,
            @Param("fullName") String fullName,
            @Param("role") String role,
            @Param("licenseNumber") String licenseNumber,
            @Param("status") EmploymentStatus status
    );

//    @Query(value = """
//        SELECT stf
//        FROM staff
//        ORDER BY created_at DESC
//        LIMIT 1
//    """, nativeQuery = true)
    Optional<Staff> findTopByOrderByCreatedAtDesc();

    Staff findByStf(String stf);

    Staff findByFullName(String fullName);

    long count();
}
