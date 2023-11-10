package com.constellation.backend.itemService;

import com.constellation.backend.db.SQLiteConnection;

import org.sqlite.JDBC;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {
	public void addItem(Item item) {
		String sql = "INSERT INTO items(name) VALUES(?)";

		try (Connection conn = SQLiteConnection.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, item.getName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public List<Item> getItems() {
		List<Item> items = new ArrayList<>();
		String sql = "SELECT id, name FROM items";

		try (Connection conn = SQLiteConnection.connect();
			 Statement stmt  = conn.createStatement();
			 ResultSet rs    = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Item newItem = new Item(
						rs.getInt("id"),
						rs.getString("name"));
				items.add(newItem);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return items;
	}
}
