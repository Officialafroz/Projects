package com.elevata.gsrtc.repository.specification;

import com.elevata.gsrtc.entity.Payment;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {
    public static Specification<Payment> filter(
            String pnr,
            String transactionRef,
            LocalDate date,
            String paymentMethod,
            String status,
            Double minAmount,
            Double maxAmount
    ) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (pnr != null && !pnr.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("pnr"), pnr));
            }

            if (transactionRef != null && !transactionRef.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("transactionRef"), transactionRef));
            }

            if (date != null) {
                predicates.add(criteriaBuilder.equal(root.get("transactionDate"), date));
            }

            if (paymentMethod != null && !paymentMethod.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("paymentMethod"), paymentMethod));
            }

            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if (minAmount != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), minAmount));
            }

            if (maxAmount != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), maxAmount));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }


}
