package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.entity.BusLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusLayoutRepository extends JpaRepository<BusLayout, Integer> {
}
