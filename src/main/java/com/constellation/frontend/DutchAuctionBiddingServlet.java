package com.constellation.frontend;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DutchAuctionBiddingServlet")
public class DutchAuctionBiddingServlet extends HttpServlet {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//	gets info about a bid
//	description, Shipping Price, Highest price, and Highest bidder 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set the content type to JSON
        response.setContentType("application/json");

        // Create a simple JSON response
        String itemDescription = "Item description of item Dutch Auction";
        double ShippingPrice = 42.00;
        double HighestPrice = 99.00;

       
        response.getWriter().write("{\"itemDescription\":\"Dutch Item description\",\"ShippingPrice\":10.99,\"HighestPrice\":50.00}");

//        // Build the JSON response
//        String jsonResponse = String.format("{\"ItemDescription\": %s, \"ShippingPrice\": %f, \"HighestPrice\": %f, \"Name\": %s}",
//                itemDescription, ShippingPrice, HighestPrice, name);


        // Write the JSON response to the output stream
 //       response.getWriter().write(jsonResponse);
    }
}

