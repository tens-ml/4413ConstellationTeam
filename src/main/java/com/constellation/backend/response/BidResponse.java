package com.constellation.backend.response;

public class BidResponse {
	
	int itemID;
	int newBid;
	String itemDescription;
	double highestPrice;
	double shippingPrice;
	double expeditedShippingPrice;
	int highestBidder;
	
	public BidResponse() {
		this.itemID = 0;
		this.newBid = 0;
		this.itemDescription = null;
		this.highestPrice = 0;
		this.highestBidder = 0;
		this.shippingPrice = 0;
		this.expeditedShippingPrice = 0;
	}
	
	public BidResponse(int itemID, int newBid) {
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

	public double getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(double HighestPrice) {
		highestPrice = HighestPrice;
	}

	public int getHighestBidder() {
		return highestBidder;
	}

	public void setHighestBidder(int HighestBidder) {
		highestBidder = HighestBidder;
	}

	public double getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(double ShippingPrice) {
		shippingPrice = ShippingPrice;
	}

	public double getExpeditedShippingPrice() {
		return expeditedShippingPrice;
	}

	public void setExpeditedShippingPrice(double ExpeditedShippingPrice) {
		expeditedShippingPrice = ExpeditedShippingPrice;
	}
}
