package com.constellation.backend.bidservice;


import java.sql.Timestamp;
import java.time.Instant;


public class Bid {

    //	CREATE TABLE bids (
//    id INTEGER PRIMARY KEY AUTOINCREMENT,
//    bidTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//    itemId INTEGER NOT NULL,
//    userId INTEGER NOT NULL,
//    price DECIMAL(20,2) NOT NULL,
//
//    FOREIGN KEY (userId) REFERENCES Users(id),
//    FOREIGN KEY (itemId) REFERENCES Catalog(id)
//);
    //the highest price, the highest bidder, and the end time of the auction
    private int  id;
    private String bidTime;
    private int itemId;
    private int userId;
    private double price;



    public Bid() {
        this.id = 0;
        this.bidTime = formatTimestamp( Timestamp.from(Instant.now()) );
        this.itemId = 0;
        this.userId = 0;
        this.price = 0;
    }

    public Bid(int id, int itemId, int userId, double price) {
        super();
        this.id = id;
        this.bidTime = formatTimestamp( Timestamp.from(Instant.now()) );
        this.itemId = itemId;
        this.userId = userId;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBidTime() {
        return bidTime;
    }

    public void setBidTime(String bidTime) {
        this.bidTime = bidTime;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private String formatTimestamp(Timestamp timestamp) {
        // Format the timestamp as a string with a specific format
        // Example format: "yyyy-MM-dd HH:mm:ss"

        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
    }


}