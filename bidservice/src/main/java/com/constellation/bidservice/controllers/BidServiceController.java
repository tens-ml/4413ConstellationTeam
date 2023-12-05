package com.constellation.bidservice.controllers;

import com.constellation.bidservice.repository.BidRepository;
import com.constellation.bidservice.model.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
public class BidServiceController {
    @Autowired
    private BidRepository bidRepository;

    @GetMapping("/highest/{itemId}")
    public ResponseEntity<Bid> getHighest(@PathVariable Integer itemId) {
        System.out.println("Getting highest bid");
        try {
            Optional<Bid> highestBid = bidRepository.findHighestBid(itemId);
            if (highestBid.isEmpty()) {
                throw new Exception();
            }
            return ResponseEntity.ok(highestBid.get());
        } catch (Exception e) {
            System.out.println("No highest bid for this item");
            return ResponseEntity.notFound().build();
        }
    }
}
