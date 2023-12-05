package com.constellation.gateway.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrichedItem {
    private String name;
    private String description;
    private Boolean isDutch;
    private BigDecimal currentPrice;
    private LocalDateTime auctionEnd;
}
