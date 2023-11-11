DROP TABLE IF EXISTS bids;
CREATE TABLE bids (
    BiddingID INTEGER PRIMARY KEY AUTOINCREMENT,
    HighestPrice DECIMAL(20,2) NOT NULL,
    EndTime DATETIME NOT Null,
    HighestBidderID INTEGER
);

INSERT INTO bids(HighestPrice,EndTime,HighestBidderID) 
    VALUES(23.00,'2023-11-07 7:23:44',1);
INSERT INTO bids(HighestPrice,EndTime,HighestBidderID) 
    VALUES(43.00,'2023-11-06 7:23:44',2);