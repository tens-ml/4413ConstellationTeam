package com.constellation.gateway.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellItemRequest {
    private Integer sellerId;
    private String name;
    private String description;
    private Boolean isDutch;
    private BigDecimal initialPrice;
    private LocalDateTime auctionEnd;
}
