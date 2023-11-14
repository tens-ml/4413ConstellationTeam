package com.constellation.backend.catalogservice;

import com.constellation.backend.db.SQLiteConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogService implements ICatalogService {

    @Override
    public CatalogItem createItem(CatalogItem newItem) {
        String sql = "INSERT INTO catalog"
                + "(sellerId, itemName, itemDescription, isDutch, daysToShip, initialPrice, auctionEnd)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, newItem.getSellerId());
            preparedStatement.setString(2, newItem.getItemName());
            preparedStatement.setString(3, newItem.getItemDescription());
            preparedStatement.setBoolean(4, newItem.isDutch());
            preparedStatement.setInt(5, newItem.getdaysToShip());
            preparedStatement.setDouble(6, newItem.getInitialPrice());
            preparedStatement.setTimestamp(7, newItem.getAuctionEnd());
            preparedStatement.executeUpdate();
            return newItem;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public List<CatalogItem> getItems(String filter) {
        // Start with an SQL query that joins the catalog, user, and bids tables
        String sql = "SELECT c.*, u.username as sellerName, MAX(b.price) as highestBid " +
                "FROM catalog c " +
                "JOIN users u ON c.sellerId = u.id " +
                "LEFT JOIN bids b ON c.id = b.itemId ";

        // Check if a filter is provided
        boolean filterProvided = filter != null && !filter.trim().isEmpty();

        // Modify the SQL query to include a WHERE clause if a filter is provided
        if (filterProvided) {
            sql += "WHERE c.itemName LIKE ? OR c.itemDescription LIKE ? ";
        }

        // Group by catalog item to aggregate bids correctly
        sql += "GROUP BY c.id";

        List<CatalogItem> items = new ArrayList<>();

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the filter parameters
            if (filterProvided) {
                String filterPattern = "%" + filter + "%";
                pstmt.setString(1, filterPattern);
                pstmt.setString(2, filterPattern);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CatalogItem catalogItem = rsToItem(rs, true);
                items.add(catalogItem);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return items;
    }


    @Override
    public CatalogItem getItem(int itemId) {
        System.out.println("getitem");
        String sql = "SELECT * FROM catalog WHERE id = ?";

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement preparedStatement  = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, itemId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rsToItem(rs, false);
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
            pstmt.setInt(4, updatedItem.getdaysToShip());
            pstmt.setDouble(5, updatedItem.getInitialPrice());
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

    private CatalogItem rsToItem(ResultSet rs, boolean enriched) throws SQLException {
        CatalogItem item = new CatalogItem();
        item.setId(rs.getInt("id"));
        item.setItemName(rs.getString("itemName"));
        item.setItemDescription(rs.getString("itemDescription"));
        item.setDutch(rs.getBoolean("isDutch"));
        item.setdaysToShip(rs.getInt("daysToShip"));
        item.setInitialPrice(rs.getDouble("initialPrice"));
        item.setAuctionEnd(rs.getTimestamp("auctionEnd"));
        item.setAvailable(rs.getBoolean("available"));

        if (enriched) {
            item.setSellerName(rs.getString("sellerName"));
            item.setHighestBid(rs.getDouble("highestBid"));
        }
        return item;
    }
}
