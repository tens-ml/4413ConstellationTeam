package com.constellation.paymentservice.controllers;

import com.constellation.paymentservice.model.Payment;
import com.constellation.paymentservice.requests.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.constellation.paymentservice.repository.PaymentRepository;

import java.util.Optional;

@RestController
public class PaymentServiceController {
    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping("/")
    public ResponseEntity<Integer> create(@RequestBody PaymentRequest paymentRequest) {
        try {
            Payment payment = Payment.fromPaymentRequest(paymentRequest);
            Payment saved = paymentRepository.save(payment);
            return ResponseEntity.ok(saved.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable Integer id) {
        try {
            Optional<Payment> payment = paymentRepository.findById(id);
            return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
