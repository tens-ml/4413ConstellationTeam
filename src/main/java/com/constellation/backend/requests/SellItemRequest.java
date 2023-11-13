package com.constellation.backend.requests;
public class SellItemRequest {
    private String itemName;
    private String itemDescription;
    private String auctionType;
    private int daysToShip;
    private double initialPrice;
    private String auctionEnd;

    public SellItemRequest() {
        // Default constructor
    }

    public SellItemRequest(String itemName, String itemDescription, String auctionType, int daysToShip, double initialPrice, String auctionEnd) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.auctionType = auctionType;
        this.daysToShip = daysToShip;
        this.initialPrice = initialPrice;
        this.auctionEnd = auctionEnd;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setAuctionType(String auctionType) {
        this.auctionType = auctionType;
    }

    public void setDaysToShip(int daysToShip) {
        this.daysToShip = daysToShip;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public void setAuctionEnd(String auctionEnd) {
        this.auctionEnd = auctionEnd;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getAuctionType() {
        return auctionType;
    }

    public int getDaysToShip() {
        return daysToShip;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public String getAuctionEnd() {
        return auctionEnd;
    }
}
