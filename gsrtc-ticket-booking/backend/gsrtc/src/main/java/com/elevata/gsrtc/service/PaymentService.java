package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.EduPaymentDTO;
import com.elevata.gsrtc.entity.Payment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {

    void pay(EduPaymentDTO dto);

    String save(double totalAmount, String pnr);

    List<Payment> findAll();

    Payment findById(int paymentId);

    void save(Payment payment);

    void delete(int paymentId);
}
