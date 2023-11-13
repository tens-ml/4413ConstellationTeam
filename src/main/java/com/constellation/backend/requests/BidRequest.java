package com.constellation.backend.requests;

public class BidRequest {
    private int itemId;
    private double price;
    public BidRequest() {}
    public BidRequest(int itemId, double price) {
        this.itemId = itemId;
        this.price = price;
    }
    public int getItemId() {
        return itemId;
    }
    public double getPrice() {
        return price;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
