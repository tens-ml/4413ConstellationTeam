package com.constellation.bidservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sellerId", nullable = false)
    private Integer sellerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description = "No description provided";

    @Column(name = "isDutch", nullable = false)
    private Boolean isDutch = false;

    @Column(name = "daysToShip", nullable = false)
    private Integer daysToShip = 5;

    @Column(name = "shippingPrice", nullable = false, precision = 20, scale = 2)
    private BigDecimal shippingPrice = BigDecimal.valueOf(5.00);;

    @Column(name = "expeditePrice", nullable = false, precision = 20, scale = 2)
    private BigDecimal expeditePrice = BigDecimal.valueOf(2.00);

    @Column(name = "initialPrice", nullable = false, precision = 20, scale = 2)
    private BigDecimal initialPrice;

    @Column(name = "auctionEnd", nullable = false)
    private LocalDateTime auctionEnd;

    @Column(name = "available")
    private Boolean available = true;

}