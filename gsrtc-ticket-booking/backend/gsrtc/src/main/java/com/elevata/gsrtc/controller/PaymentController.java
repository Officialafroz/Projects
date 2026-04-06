package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.EduPaymentDTO;
import com.elevata.gsrtc.entity.Payment;
import com.elevata.gsrtc.service.PaymentService;
import com.elevata.gsrtc.service.impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/end-user/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public String pay(@RequestParam double totalAmount, @RequestParam String pnr) {
        return paymentService.save(totalAmount, pnr);
    }

    @PostMapping
    public ResponseEntity<String> pay(@RequestBody EduPaymentDTO dto) {
        paymentService.pay(dto);
        return ResponseEntity.ok(
                "Educational booking payment created for amount " + dto.getAmount());
    }

    @GetMapping("/payments")
    public List<Payment> getPaymentList() {
        return paymentService.findAll();
    }

    @GetMapping("/{paymentId}")
    public Payment getPaymentById(@PathVariable int paymentId) {
        return paymentService.findById(paymentId);
    }

    @PostMapping("/add")
    public void add(@RequestBody Payment payment) {
        paymentService.save(payment);
    }

    @DeleteMapping("/delete/{paymentId}")
    public void delete(@PathVariable int paymentId) {
        paymentService.delete(paymentId);
    }
}
