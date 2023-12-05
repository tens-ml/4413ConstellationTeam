package com.constellation.bidservice.model;

import com.constellation.bidservice.requests.BidRequest;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table
public class Bid {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "bidTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bidTime;

    @Column(name = "price", nullable = false, precision = 20, scale = 2)
    private BigDecimal price;

    private Integer userId;

    private Integer itemId;
}