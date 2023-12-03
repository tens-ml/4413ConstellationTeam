package com.constellation.paymentservice;

import com.constellation.db.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDAO {
	public void addCard(Payment payment) {
		//we use prepared statements, Q: why?
		String sql = "INSERT INTO payments(cardNumber, name, expMonth, expYear, ccv, userId) VALUES(?,?,?,?,?,?)";
		try (Connection conn = SQLiteConnection.connect();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, payment.getCardNo());
			pstmt.setString(2, payment.getName());
			pstmt.setInt(3, payment.getExpMo());
			pstmt.setInt(4, payment.getExpYe());
			pstmt.setInt(5, payment.getCcv());
			pstmt.setInt(6, payment.getUserId());
		pstmt.executeUpdate();
		} catch (SQLException e) {
		System.out.println(e.getMessage());
		}
		}
	
	public void delete(int userId) {
		String sql = "DELETE FROM payments WHERE userId = ?";
		try (Connection conn = SQLiteConnection.connect();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {
			//Set the corresponding parameter
			pstmt.setInt(1, userId);
			//Delete the payment record
			pstmt.executeUpdate();
		} catch (SQLException e) {
		System.out.println(e.getMessage());
		}
		}
	
	public void update(int userId, Payment payment) {
		//use prepared statments
		String sql = "UPDATE payments SET cardNumber = ?, name = ?, expMonth = ?, expYear = ?, ccv = ? WHERE userId =?";
		try (Connection conn = SQLiteConnection.connect();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {
			//Set the corresponding parameters
			pstmt.setLong(1, payment.getCardNo());
			pstmt.setString(2, payment.getName());
			pstmt.setInt(3, payment.getExpMo());
			pstmt.setInt(4, payment.getExpYe());
			pstmt.setInt(5, payment.getCcv());
			pstmt.setInt(6, userId);
			//Update the payment record
			pstmt.executeUpdate();
		} catch (SQLException e) {
		System.out.println(e.getMessage());
		}
		}
	
	public Payment read(int userId) {
		// Use prepared statements
		String sql = "SELECT cardNumber, name, expMonth, expYear, ccv FROM payments WHERE userId = ?";
		Payment payment = null;
		try (Connection conn = SQLiteConnection.connect();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Set the corresponding parameter
			pstmt.setInt(1, userId);
			//Execute the query and get the result set
			try (ResultSet rs = pstmt.executeQuery()) {
				//Check if a result was returned
				if (rs.next()) {
					payment = new Payment();
				//Set the properties of the payment object
					payment.setUserId(userId);
					payment.setCardNo(rs.getLong("cardNumber"));
					payment.setName(rs.getString("name"));
					payment.setExpMo(rs.getInt("expMonth"));
					payment.setExpYe(rs.getInt("expYear"));
					payment.setCcv(rs.getInt("ccv"));
				}
			}
		} catch (SQLException e) {
		System.out.println(e.getMessage());
		}
		return payment;
		}
	
	public static boolean isPaymentInDatabase(int userId) {
		try (Connection connection = SQLiteConnection.connect();
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT COUNT(*) FROM payments WHERE userId = ?")) {
			preparedStatement.setInt(1, userId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					return count > 0; // If count is greater than 0, the user exists
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
