package com.constellation.paymentservice.model;

import com.constellation.paymentservice.requests.PaymentRequest;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table
public class Payment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer userId;
    private Integer itemId;
    private BigDecimal cardNumber;
    private Integer ccv;
    private String expiryDate;
    private BigDecimal amount;

    public static Payment fromPaymentRequest(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setUserId(paymentRequest.getUserId());
        payment.setItemId(paymentRequest.getItemId());
        payment.setCardNumber(paymentRequest.getCardNumber());
        payment.setCcv(paymentRequest.getCcv());
        payment.setExpiryDate(paymentRequest.getExpiryDate());
        payment.setAmount(paymentRequest.getAmount());
        return payment;
    }
}
