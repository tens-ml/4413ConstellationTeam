package com.constellation.gateway.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidRequest {
    private Integer id;
    private Date bidTime;
    private BigDecimal price;
    private Integer userId;
    private Integer itemId;

    public void validate() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.getForObject(System.getenv("USERSERVICE_URL") + "/" + this.userId, String.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("User does not exist");
        }

        try {
            restTemplate.getForObject(System.getenv("CATALOGSERVICE_URL") + "/" + this.itemId, String.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Item does not exist");
        }

        // bid must be higher than current price
        try {
            BidRequest currentBid = restTemplate.getForObject(System.getenv("BIDSERVICE_URL") + "/highest/" + this.itemId, BidRequest.class);
            BigDecimal currentPrice = Objects.requireNonNull(currentBid).getPrice();

            if (this.price.compareTo(currentPrice) <= 0) {
                throw new IllegalArgumentException("Bid must be higher than current price");
            }
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }
}
