package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.dto.TransactionManagementDTO;
import com.elevata.gsrtc.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{
    @Query("""
    SELECT new com.elevata.gsrtc.dto.TransactionManagementDTO(
        b.pnr,
        p.transactionRef,
        p.amount,
        p.paymentMethod,
        p.paymentDate,
        p.status
    )
    FROM Payment p
    JOIN p.booking b
    JOIN b.trip t
    JOIN t.busDepot d
    WHERE d.depotId = :depotId
        AND (:pnr IS NULL OR b.pnr = :pnr)
        AND (:transactionRef IS NULL OR p.transactionRef = :transactionRef)
        AND (:date IS NULL OR p.paymentDate = :date)
        AND (:paymentMethod IS NULL OR p.paymentMethod = :paymentMethod)
        AND (:status IS NULL OR p.status = :status)
    """)
    List<TransactionManagementDTO> filterDepotTransactions(
            @Param("depotId") int depotId,
            @Param("pnr") String pnr,
            @Param("transactionRef") String transactionRef,
            @Param("date") LocalDateTime date,
            @Param("paymentMethod") String paymentMethod,
            @Param("status") String status
    );


//    @Query("SELECT SUM(p.amount) FROM EduPayment p WHERE p.status = 'PAID'")
//    BigDecimal getTotalRevenue();

//    @Query("""
//        SELECT new com.elevata.gsrtc.dto.MonthlyRevenueRow(
//            FUNCTION('MONTHNAME_CUSTOM', p.createdAt),
//            SUM(p.amount)
//        )
//        FROM Payment p
//        WHERE p.createdAt >= :startDate
//        GROUP BY FUNCTION('MONTH', p.createdAt)
//        ORDER BY FUNCTION('MONTH', p.createdAt)
//    """)
//    List<MonthlyRevenueRow> getMonthlyRevenue(LocalDate startDate);
}
