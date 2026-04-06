package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.repository.RefundRepository;
import com.elevata.gsrtc.entity.Payment;
import com.elevata.gsrtc.entity.Refund;
import com.elevata.gsrtc.service.PaymentService;
import com.elevata.gsrtc.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RefundServiceImpl implements RefundService {
    private RefundRepository refundRepository;
    private PaymentService paymentService;

    @Autowired
    public RefundServiceImpl(RefundRepository refundRepository, PaymentService paymentService) {
        this.refundRepository = refundRepository;
        this.paymentService = paymentService;
    }

    @Override
    public List<Refund> findAll() {
        return refundRepository.findAll();
    }

    @Override
    public Refund findById(int refundId) {
        Optional<Refund> refundsOptional = refundRepository.findById(refundId);

        if (refundsOptional.isEmpty()) {
            throw new RuntimeException("Route stop not found for id: " + refundId);
        }

        return refundsOptional.get();
    }

    @Transactional
    @Override
    public void save(Refund refund) {
        int paymentId = refund.getPayment().getPaymentId();

        if (refund.getPayment() != null && paymentId > 0) {
             Payment existingPayment = paymentService.findById(paymentId);

            if (existingPayment == null) {
                throw new RuntimeException(
                        String.format("Invalid payment id - %d", paymentId));
            }
            refund.setPayment(existingPayment);
        }

        refundRepository.save(refund);
        System.out.println("Refunds is successfully created.");
    }

    @Transactional
    @Override
    public void delete(int refundId) {
        refundRepository.deleteById(refundId);
        System.out.println("Refund is safely deleted.");
    }
}
