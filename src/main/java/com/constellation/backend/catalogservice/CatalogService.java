package com.constellation.backend.catalogservice;

import com.constellation.backend.db.SQLiteConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogService implements ICatalogService {

    @Override
    public CatalogItem createItem(CatalogItem newItem) {
        String sql = "INSERT INTO catalog"
                + "(itemName, itemDescription, isDutch, daysToShip, initialPrice, auctionEnd)"
                + " VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, newItem.getItemName());
            preparedStatement.setString(2, newItem.getItemDescription());
            preparedStatement.setBoolean(3, newItem.isDutch());
            preparedStatement.setInt(4, newItem.getDaysToShip());
            preparedStatement.setInt(5, newItem.getInitialPrice());
            preparedStatement.setTimestamp(6, newItem.getAuctionEnd());
            preparedStatement.executeUpdate();
            return newItem;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public List<CatalogItem> getItems() {
        String sql = "SELECT * FROM catalog";
        List<CatalogItem> items = new ArrayList<>();

        try (Connection conn = SQLiteConnection.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CatalogItem catalogItem = rsToItem(rs);
                items.add(catalogItem);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return items;
    }

    @Override
    public CatalogItem getItem(int itemId) {
        String sql = "SELECT * FROM catalog WHERE id = ?";

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement preparedStatement  = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, itemId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rsToItem(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public CatalogItem updateItem(CatalogItem updatedItem) {
        String sql = "UPDATE catalog SET itemName = ?, itemDescription = ?, isDutch = ?, " +
                "daysToShip = ?, initialPrice = ?, auctionEnd = ? WHERE id = ?";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, updatedItem.getItemName());
            pstmt.setString(2, updatedItem.getItemDescription());
            pstmt.setBoolean(3, updatedItem.isDutch());
            pstmt.setInt(4, updatedItem.getDaysToShip());
            pstmt.setInt(5, updatedItem.getInitialPrice());
            pstmt.setTimestamp(6, updatedItem.getAuctionEnd());
            pstmt.setInt(7, updatedItem.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return updatedItem;
    }

    @Override
    public boolean deleteItem(int itemId) {
        return false;
    }

    private CatalogItem rsToItem(ResultSet rs) throws SQLException {
        CatalogItem item = new CatalogItem();
        item.setId(rs.getInt("id"));
        item.setItemName(rs.getString("itemName"));
        item.setItemDescription(rs.getString("itemDescription"));
        item.setDutch(rs.getBoolean("isDutch"));
        item.setDaysToShip(rs.getInt("daysToShip"));
        item.setInitialPrice(rs.getInt("initialPrice"));
        item.setAuctionEnd(rs.getTimestamp("auctionEnd"));
        return item;
    }
}
