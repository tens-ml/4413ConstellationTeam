package com.constellation.gateway.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

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

    public void validate() {
        String cardNumber = this.cardNumber.toString();
        if (cardNumber.length() > 16 || cardNumber.length() < 15) {
            throw new IllegalArgumentException("Card number must be 15/16 digits");
        }

        if (this.ccv.toString().length() > 4 || this.ccv.toString().length() < 3) {
            throw new IllegalArgumentException("CCV must be 3/4 digits");
        }

        // apply regex
        if (!this.expiryDate.matches("^((0[1-9]|1[0-2])\\/\\d{2})$")) {
            throw new IllegalArgumentException("Expiry date must be in MM/YY or MM/YYYY format");
        }

        if (this.amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.getForObject("http://localhost:8081/users/" + this.userId, String.class);
        } catch (Exception e) {
            // do nothing
            throw new IllegalArgumentException("User does not exist");
        }

        try {
            restTemplate.getForObject("http://localhost:8082/items/" + this.itemId, String.class);
        } catch (Exception e) {
            // do nothing
            throw new IllegalArgumentException("Item does not exist");
        }

    }
}
