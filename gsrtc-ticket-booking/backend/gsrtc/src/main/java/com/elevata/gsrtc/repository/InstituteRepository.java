package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.dto.InstituteDTO;
import com.elevata.gsrtc.entity.InstituteRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstituteRepository
        extends JpaRepository<InstituteRegistry, Integer> {
    Optional<InstituteRegistry> findByInstituteCode(String instituteCode);

    boolean existsByInstituteCode(String instituteCode);

    @Query("""
            SELECT new com.elevata.gsrtc.dto.InstituteDTO(
                instituteId, instituteCode, instituteName, instituteType,
                ownership, location, institutePhone, instituteEmail
            )
            FROM InstituteRegistry ir
            WHERE ir.instituteCode = :instituteCode
        """)
    Optional<InstituteDTO> fetchInstituteDetails(
            @Param("instituteCode") String instituteCode);
}
