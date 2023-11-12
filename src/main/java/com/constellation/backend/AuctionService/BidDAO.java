package com.constellation.backend.AuctionService;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.constellation.backend.db.SQLiteConnection;

public class BidDAO {
	
	public List<Bid> readAll() {
		 String sql = "SELECT BiddingID, HighestBidderID, HighestPrice, EndTime FROM bids";
		 List<Bid> bids = new ArrayList<>();

		 try (Connection conn = SQLiteConnection.connect();
			  Statement stmt = conn.createStatement();
			  ResultSet rs = stmt.executeQuery(sql) ){

			 while (rs.next()) {
				 Bid bid = new Bid();
				 
				 System.out.println("Bid regular 1 Endtime:"+bid.getEndTime());
				 
				 bid.setBiddingID(rs.getInt("BiddingID"));
				 bid.setHighestBidderID(rs.getInt("HighestBidderID"));
				 bid.setHighestPrice(rs.getDouble("HighestPrice"));
				 bid.setEndTime(rs.getString("EndTime"));
				 
		 
				
		 
		 
				 bids.add(bid);
			 }
		 	} catch (SQLException e) {
		 		System.out.println(e.getMessage());
		 		}
		 
		 return bids;
	}
	public void create(Bid bid) {

		String sql = "INSERT INTO bids(HighestBidderID, HighestPrice, EndTime) VALUES(?,?,?)";

		try (Connection conn = SQLiteConnection.connect();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, bid.getHighestBidderID());
			pstmt.setDouble(2, bid.getHighestPrice());
			pstmt.setString(3, bid.getEndTime());
			System.out.println(Timestamp.valueOf(bid.getEndTime()));
			pstmt.executeUpdate();
						
		 } catch (SQLException e) {
			 System.out.println(e.getMessage());
		 	}
	}
	public Bid read(int BiddingID) {
		// Use prepared statements
		String sql = "SELECT HighestBidderID, HighestPrice, EndTime FROM bids WHERE BiddingID = ?";
		Bid bid = null;
		try (Connection conn = SQLiteConnection.connect();
		 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Set the corresponding parameter
			pstmt.setInt(1, BiddingID);
			// Execute the query and get the result set
			try (ResultSet rs = pstmt.executeQuery()) {
				// Check if a result was returned
				if (rs.next()) {
					bid = new Bid();
					// Set the properties of the bid object
					bid.setBiddingID(BiddingID);
					bid.setHighestPrice(rs.getDouble("HighestPrice"));
					bid.setHighestBidderID(rs.getInt("HighestBidderID"));
					bid.setEndTime(rs.getString("EndTime"));
					
					
					
				}
			}
		 	} catch (SQLException e) {
		 		System.out.println(e.getMessage());
		 	}
		return bid;
	}
	public void update(int BiddingID, Bid bid) {
		//use prepared statments
		String sql = "UPDATE bids SET HighestBidderID = ?, HighestPrice = ?, EndTime = ? WHERE BiddingID = ?";

		try (Connection conn = SQLiteConnection.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Set the corresponding parameters
			pstmt.setInt(1, bid.getHighestBidderID());
			pstmt.setDouble(2, bid.getHighestPrice());
			pstmt.setString(3, bid.getEndTime());
			pstmt.setInt(4, BiddingID);
			
			// Update the bid record
			pstmt.executeUpdate();
		 } catch (SQLException e) {
			 System.out.println(e.getMessage());
		 	}
	}
		 
	public void delete(int BiddingID) {
		 String sql = "DELETE FROM bids WHERE BiddingID = ?";

		 try (Connection conn = SQLiteConnection.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Set the corresponding parameter
			pstmt.setInt(1, BiddingID);
			// Delete the bid record
			pstmt.executeUpdate();
		 } catch (SQLException e) {
			 System.out.println(e.getMessage());
		 	}
	}
	
	   private String formatTimestamp(Timestamp timestamp) {
	        // Format the timestamp as a string with a specific format
	        // Example format: "yyyy-MM-dd HH:mm:ss"
	       
	        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
	    }
	
}
