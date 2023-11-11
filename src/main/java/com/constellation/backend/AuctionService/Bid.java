package com.constellation.backend.AuctionService;


import java.sql.Timestamp;
import java.time.Instant;


public class Bid {
	
	//the highest price, the highest bidder, and the end time of the auction
	private int  BiddingID;
	private double HighestPrice;
	private int HighestBidderID;
	private String EndTime;
	
	public Bid() {
		this.BiddingID = 0;
		this.HighestBidderID = 0;
		this.EndTime = formatTimestamp( Timestamp.from(Instant.now()) );
		this.HighestPrice = 0;
	}
	
	public Bid(int BiddingID, double HighestPrice, int HighestBidderID) {
		this.BiddingID = BiddingID;
		this.HighestPrice = HighestPrice;
		this.HighestBidderID = HighestBidderID;
		this.EndTime = formatTimestamp( Timestamp.from(Instant.now()) );
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

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	private String formatTimestamp(Timestamp timestamp) {
	        // Format the timestamp as a string with a specific format
	        // Example format: "yyyy-MM-dd HH:mm:ss"
	       
	        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
	}
	

}
