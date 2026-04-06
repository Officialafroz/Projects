package com.elevata.gsrtc.service;

import com.elevata.gsrtc.entity.Refund;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RefundService {
    List<Refund> findAll();

    Refund findById(int refundId);

    void save(Refund refund);

    void delete(int refundId);
}
