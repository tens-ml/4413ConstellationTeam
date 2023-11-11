package com.constellation.backend.userService;

import com.constellation.backend.db.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {
	// Inserting a new user
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

		String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";

		try (Connection conn = SQLiteConnection.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
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
}
