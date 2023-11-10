package com.constellation.backend.catalogservice;

import java.sql.Timestamp;
// Entity Class
public class CatalogItem {
    private int id;
    private String itemName;
    private String itemDescription;
    private boolean isDutch;
    private int daysToShip;
    private int initialPrice;
    private Timestamp auctionEnd;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public boolean isDutch() {
        return isDutch;
    }

    public void setDutch(boolean dutch) {
        isDutch = dutch;
    }

    public int getDaysToShip() {
        return daysToShip;
    }

    public void setDaysToShip(int daysToShip) {
        this.daysToShip = daysToShip;
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(int initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Timestamp getAuctionEnd() {
        return auctionEnd;
    }

    public void setAuctionEnd(Timestamp auctionEnd) {
        this.auctionEnd = auctionEnd;
    }
}