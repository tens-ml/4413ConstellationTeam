package com.constellation.gateway.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidRequest {
    private Integer id;
    private Date bidTime;
    private BigDecimal price;
    private Integer userId;
    private Integer itemId;
}
