package com.constellation.backend.userService;

import com.constellation.backend.db.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
	// Inserting a new user
	public static boolean updatePassword(String username, String password) {
		try (Connection connection = SQLiteConnection.connect();
				PreparedStatement preparedStatement = connection
						.prepareStatement("UPDATE Users SET password = ? WHERE username = ?")) {
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, username);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void insertUser(User user, String password) {
		try (Connection connection = SQLiteConnection.connect();
				PreparedStatement preparedStatement = connection.prepareStatement(
						"INSERT INTO Users (username, firstName, lastName, streetAddress, streetNumber, postalCode, city, country, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, user.getStreetAddress());
			preparedStatement.setString(5, user.getStreetNumber());
			preparedStatement.setString(6, user.getPostalCode());
			preparedStatement.setString(7, user.getCity());
			preparedStatement.setString(8, user.getCountry());
			preparedStatement.setString(9, password);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Retrieving a user by username
	public static User getUser(String username, String password) {
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

		try (Connection conn = SQLiteConnection.connect();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setStreetAddress(rs.getString("streetAddress"));
				user.setStreetNumber(rs.getString("streetNumber"));
				user.setPostalCode(rs.getString("postalCode"));
				user.setCity(rs.getString("city"));
				user.setCountry(rs.getString("country"));
				System.out.println(user.getUsername());
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	// Checking if a user with the given username exists
	public static boolean isUserInDatabase(String username) {
		try (Connection connection = SQLiteConnection.connect();
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT COUNT(*) FROM Users WHERE username = ?")) {
			preparedStatement.setString(1, username);

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
	
	public static boolean isUserInDatabase(int userId) {
		try (Connection connection = SQLiteConnection.connect();
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT COUNT(*) FROM Users WHERE id = ?")) {
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
