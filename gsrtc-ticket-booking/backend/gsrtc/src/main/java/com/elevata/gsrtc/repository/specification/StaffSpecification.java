package com.elevata.gsrtc.repository.specification;

import com.elevata.gsrtc.entity.Staff;
import com.elevata.gsrtc.enums.EmploymentStatus;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class StaffSpecification {

    public static Specification<Staff> filterStaff(
            int depotId,
            String stf,
            String fullName,
            String role,
            String licenseNumber,
            EmploymentStatus status
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(
                    cb.equal(root.get("busDepot").get("depotId"), depotId)
            );

            if (stf != null && !stf.isBlank()) {
                predicates.add(
                        cb.equal(root.get("stf"), stf)
                );
            }

            if (fullName != null && !fullName.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("fullName")),
                                "%" + fullName.toLowerCase() + "%"
                        )
                );
            }

            if (role != null && !role.isBlank()) {
                predicates.add(
                        cb.equal(root.get("role"), role)
                );
            }

            if (licenseNumber != null && !licenseNumber.isBlank()) {
                predicates.add(
                        cb.equal(root.get("licenseNumber"), licenseNumber)
                );
            }

            if (status != null) {
                predicates.add(
                        cb.equal(root.get("status"), status)
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
