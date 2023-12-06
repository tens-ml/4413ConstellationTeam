package com.constellation.bidservice.controllers;

import com.constellation.bidservice.repository.BidRepository;
import com.constellation.bidservice.model.Bid;
import com.constellation.bidservice.requests.BidRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/")
    public ResponseEntity<String> placeBid(@RequestBody BidRequest bidRequest) {
        System.out.println("Placing bid");
        try {
            Bid bid = new Bid();
            bid.setBidTime(bidRequest.getBidTime());
            bid.setPrice(bidRequest.getPrice());
            bid.setItemId(bidRequest.getItemId());
            bid.setUserId(bidRequest.getUserId());
            bidRepository.save(bid);
            return ResponseEntity.ok("Bid placed");
        } catch (Exception e) {
            System.out.println("Error while placing bid");
            return ResponseEntity.badRequest().body("Error while placing bid");
        }
    }


}
