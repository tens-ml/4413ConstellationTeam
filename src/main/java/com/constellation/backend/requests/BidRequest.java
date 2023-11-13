package com.constellation.backend.requests;

public class BidRequest {
	
	int itemID;
	int newBid;
	String itemDescription;
	int highestPrice;
	int shippingPrice;
	int expeditedShippingPrice;
	int highestBidder;
	
	public BidRequest() {
		this.itemID = 0;
		this.newBid = 0;
		this.itemDescription = null;
		this.highestPrice = 0;
		this.highestBidder = 0;
		this.shippingPrice = 0;
		this.expeditedShippingPrice = 0;
	}
	
	public BidRequest(int itemID, int newBid) {
		super();
		this.itemID = itemID;
		this.newBid = newBid;
		this.itemDescription = null;
		this.highestPrice = 0;
		this.highestBidder = 0;
		this.shippingPrice = 0;
		this.expeditedShippingPrice = 0;
	}
	
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public int getNewBid() {
		return newBid;
	}
	public void setNewBid(int newBid) {
		this.newBid = newBid;
	}
	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public int getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(int HighestPrice) {
		highestPrice = HighestPrice;
	}

	public int getHighestBidder() {
		return highestBidder;
	}

	public void setHighestBidder(int HighestBidder) {
		highestBidder = HighestBidder;
	}

	public int getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(int ShippingPrice) {
		shippingPrice = ShippingPrice;
	}

	public int getExpeditedShippingPrice() {
		return expeditedShippingPrice;
	}

	public void setExpeditedShippingPrice(int ExpeditedShippingPrice) {
		expeditedShippingPrice = ExpeditedShippingPrice;
	}
}
