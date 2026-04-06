package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.dto.DepotAdminResponseDTO;
import com.elevata.gsrtc.dto.GraphDTO;
import com.elevata.gsrtc.entity.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);

    @Query("SELECT u FROM AppUser u WHERE u.email = :email")
    AppUser findUserByEmail(@Param("email") String email);

    Optional<AppUser> findByBusDepotDepotId(int depotId);

    boolean existsByEmail(String email);

    @Query("""
            SELECT new com.elevata.gsrtc.dto.DepotAdminResponseDTO(
                u.userId,
                u.name,
                u.email,
                u.phoneNumber,
                d.depotName,
                d.address,
                u.accountStatus
            )
            FROM AppUser u
            JOIN u.busDepot d
            WHERE u.role = 'DEPOT_ADMIN'
            ORDER BY
                CASE WHEN u.accountStatus = 'PENDING' THEN 0 ELSE 1 END,
                u.createdAt DESC
    """)
    Page<DepotAdminResponseDTO> getDepotAdminList(Pageable pageable);

    @Query("""
        SELECT u
        FROM AppUser u
        JOIN u.busDepot d
        WHERE u.role = 'DEPOT_ADMIN'
        AND (
            LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(d.depotName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    Page<AppUser> searchDepotAdmins(String keyword, Pageable pageable);

    long count();

    @Query("""
        SELECT COUNT(u)
        FROM AppUser u
        WHERE MONTH(u.createdAt) = :month
        AND YEAR(u.createdAt) = :year
    """)
    long countUsersByMonth(@Param("month") int month, @Param("year") int year);

    @Query("""
        SELECT new com.elevata.gsrtc.dto.GraphDTO(
            MONTH(u.createdAt),
            COUNT(u)
        )
        FROM AppUser u
        WHERE YEAR(u.createdAt) = :year
        GROUP BY MONTH(u.createdAt)
        ORDER BY MONTH(u.createdAt)
    """)
    List<GraphDTO> getUserGrowth(@Param("year") int year);

//    @Query("SELECT COUNT(u) FROM User u")
//    Integer countAllUsers();
//
//    @Query("""
//        SELECT COUNT(u)
//        FROM User u
//        WHERE u.createdAt >= CURRENT_DATE - 7
//    """)
//    Integer countUsersRegisteredThisWeek();
}
