package com.constellation.AuctionService.Database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AuctionBidsDatabaseConnection {
	public static Connection connect() {
        Connection conn = null;
        try {
            // Obtain our environment naming context
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            // Look up our data source
            DataSource ds = (DataSource) envCtx.lookup("jdbc/AuctionBidsDB");
            // Allocate and use a connection from the pool
            conn = ds.getConnection();
        } catch (SQLException | NamingException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
