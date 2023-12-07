package com.constellation.paymentservice.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Integer userId;
    private Integer itemId;
    private BigDecimal cardNumber;
    private Integer ccv;
    private String expiryDate;
    private BigDecimal amount;
}
