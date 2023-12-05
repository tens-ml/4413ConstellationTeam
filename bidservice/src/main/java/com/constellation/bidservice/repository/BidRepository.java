package com.constellation.bidservice.repository;

import com.constellation.bidservice.model.Bid;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends CrudRepository<Bid, Integer> {
    @Query("SELECT b FROM Bid b WHERE b.item.id = :itemId ORDER BY b.price DESC")
    List<Bid> findBidsByItemIdOrderByPriceDesc(@Param("itemId") Integer itemId);

    default Optional<Bid> findHighestBid(Integer itemId) {
        return findBidsByItemIdOrderByPriceDesc(itemId).stream().findFirst();
    }
}
