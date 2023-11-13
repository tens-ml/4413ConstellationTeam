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
		 String sql = "SELECT id, bidTime, itemId, userId, price FROM bids";
		 List<Bid> bids = new ArrayList<>();

		 try (Connection conn = SQLiteConnection.connect();
			  Statement stmt = conn.createStatement();
			  ResultSet rs = stmt.executeQuery(sql) ){

			 while (rs.next()) {
				 Bid bid = new Bid();
				 
				 System.out.println("Bid regular 1 bidTime:"+bid.getBidTime());
				 
				 bid.setId(rs.getInt("Id"));
				 bid.setBidTime(rs.getString("bidTime"));
				 bid.setItemId(rs.getInt("itemId"));
				 bid.setUserId(rs.getInt("userId"));
				 bid.setPrice(rs.getInt("price"));
	 		 		 
				 bids.add(bid);
			 }
		 	} catch (SQLException e) {
		 		System.out.println(e.getMessage());
		 		}
		 
		 return bids;
	}
	public void create(Bid bid) {

		String sql = "INSERT INTO bids(bidTime, itemId, userID, price) VALUES(?,?,?,?)";

		try (Connection conn = SQLiteConnection.connect();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, bid.getBidTime());
			pstmt.setInt(2, bid.getItemId());
			pstmt.setInt(3, bid.getUserId());
			pstmt.setInt(4, bid.getPrice());
			
			System.out.println(Timestamp.valueOf(bid.getBidTime()));
			
			pstmt.executeUpdate();
						
		 } catch (SQLException e) {
			 System.out.println(e.getMessage());
		 	}
	}

	public Bid readById(int id) {
		// Use prepared statements
	
		String sql = "SELECT id, bidTime, itemId, userId, price FROM bids WHERE id = ?";
		Bid bid = null;
		try (Connection conn = SQLiteConnection.connect();
		 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Set the corresponding parameter
			pstmt.setInt(1, id);
			// Execute the query and get the result set
			try (ResultSet rs = pstmt.executeQuery()) {
				// Check if a result was returned
				if (rs.next()) {
					bid = new Bid();
					// Set the properties of the bid object
					bid.setId(id);
					bid.setBidTime(rs.getString("bidTime"));
					bid.setItemId(rs.getInt("itemId"));
					bid.setUserId(rs.getInt("userId"));
					bid.setPrice(rs.getInt("price"));
								
				}
			}
		 	} catch (SQLException e) {
		 		System.out.println(e.getMessage());
		 	}
		return bid;
	}

	public Bid readByItemId(int itemId) {
		
		String sql = "SELECT id, bidTime, itemId, userId, price FROM bids WHERE itemId = ?";
		Bid bid = null;
		try (Connection conn = SQLiteConnection.connect();
		 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Set the corresponding parameter
			pstmt.setInt(1, itemId);
			// Execute the query and get the result set
			try (ResultSet rs = pstmt.executeQuery()) {
				// Check if a result was returned
				if (rs.next()) {
					bid = new Bid();
					// Set the properties of the bid object
					bid.setId(rs.getInt("id"));
					bid.setBidTime(rs.getString("bidTime"));
					bid.setItemId(rs.getInt("itemId"));
					bid.setUserId(rs.getInt("userId"));
					bid.setPrice(rs.getInt("price"));
							
				}
			}
		 	} catch (SQLException e) {
		 		System.out.println(e.getMessage());
		 	}
		return bid;
	}
//	CREATE TABLE bids (
//  id INTEGER PRIMARY KEY AUTOINCREMENT,
//  bidTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//  itemId INTEGER NOT NULL,
//  userId INTEGER NOT NULL,
//  price DECIMAL(20,2) NOT NULL,
//
//  FOREIGN KEY (userId) REFERENCES Users(id),
//  FOREIGN KEY (itemId) REFERENCES Catalog(id)
//);
	public void update(int Id, Bid bid) {
		//use prepared statments
		String sql = "UPDATE bids SET bidTime = ?, itemId = ?, userId = ?, price = ? WHERE id = ?";

		try (Connection conn = SQLiteConnection.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Set the corresponding parameters
			pstmt.setString(1, bid.getBidTime());
			pstmt.setInt(2, bid.getItemId());
			pstmt.setInt(3, bid.getUserId());
			pstmt.setInt(4, bid.getPrice());
			pstmt.setInt(5, Id);
			
			// Update the bid record
			pstmt.executeUpdate();
		 } catch (SQLException e) {
			 System.out.println(e.getMessage());
		 	}
	}
		 
	public void delete(int id) {
		 String sql = "DELETE FROM bids WHERE id = ?";

		 try (Connection conn = SQLiteConnection.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Set the corresponding parameter
			pstmt.setInt(1, id);
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
