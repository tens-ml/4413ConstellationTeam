DROP TABLE IF EXISTS bids;
CREATE TABLE bids (
    BiddingID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    HighestPrice DECIMAL(20,2) NOT NULL,
    auctionEnd TIMESTAMP NOT NUll,
    HighestBidderID INTEGER
);

INSERT INTO bids(HighestPrice,auctionEnd,HighestBidderID)
    VALUES(23.00,'2023-11-07 7:23:44',1);
INSERT INTO bids(HighestPrice,auctionEnd,HighestBidderID)
    VALUES(43.00,'2023-11-06 7:23:44',2);
INSERT INTO bids(HighestPrice,auctionEnd)
    VALUES(63.00,'2023-11-11 12:23:44');