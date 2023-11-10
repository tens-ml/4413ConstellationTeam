package com.constellation.backend.AuctionService;


import java.sql.Date;

public class Bid {
	
	//the highest price, the highest bidder, and the end time of the auction
	private int  BiddingID;
	private double HighestPrice;
	private int HighestBidderID;
	private Date EndTime;
	
	public Bid() {
		this.BiddingID = 0;
		this.HighestBidderID = 0;
		this.EndTime = null;
		this.HighestPrice = 0;
	}
	
	public Bid(int BiddingID, double HighestPrice, int HighestBidderID, Date EndTime) {
		this.BiddingID = BiddingID;
		this.HighestPrice = HighestPrice;
		this.HighestBidderID = HighestBidderID;
		this.EndTime = EndTime;
	}
	
	public int getBiddingID() {
		return BiddingID;
	}

	public void setBiddingID(int biddingID) {
		BiddingID = biddingID;
	}

	public double getHighestPrice() {
		return HighestPrice;
	}

	public void setHighestPrice(double highestPrice) {
		HighestPrice = highestPrice;
	}

	public int getHighestBidderID() {
		return HighestBidderID;
	}

	public void setHighestBidderID(int highestBidderID) {
		HighestBidderID = highestBidderID;
	}

	public Date getEndTime() {
		return EndTime;
	}

	public void setEndTime(Date endTime) {
		EndTime = endTime;
	}

	

}
