
package com.constellation.backend;

import com.constellation.backend.catalogservice.CatalogItem;
import com.constellation.backend.catalogservice.CatalogService;
import com.constellation.backend.exceptions.LoginFailedException;
import com.constellation.backend.exceptions.SignupFailedException;
import com.constellation.backend.bidservice.BidService;
import com.constellation.backend.requests.BidRequest;
import com.constellation.backend.requests.LoginRequest;
import com.constellation.backend.requests.SellItemRequest;
import com.constellation.backend.requests.SignupRequest;
import com.constellation.backend.userService.User;
import com.constellation.backend.userService.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.constellation.backend.bidservice.Bid;
import com.constellation.backend.response.BidResponse;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Path("/")
public class Controller {
    @Context
    private HttpServletRequest request;
    private final UserService userService = new UserService();
    private final CatalogService catalogService = new CatalogService();
    private final BidService bidService = new BidService();
    @POST
    @Path("/user/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) throws LoginFailedException {
        User user = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            throw new LoginFailedException();
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            return Response.ok(user).build();
        }
    }

    @POST
    @Path("/user/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(SignupRequest signupRequest) throws SignupFailedException {
        User user = userService.createUser(signupRequest.getUser(), signupRequest.getPassword());
        if (user == null) {
            throw new SignupFailedException();
        } else {
            return Response.ok().build();
        }
    }

    @GET
    @Path("/catalog")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCatalogItems(@QueryParam("filter") String filter) {
        List<CatalogItem> catalogItems = catalogService.getItems(filter);
        return Response.ok(catalogItems).build();
    }

    @POST
    @Path("/catalog")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCatalogItem(SellItemRequest sellItemRequest) {
        CatalogItem newItem = new CatalogItem();
        newItem.setItemName(sellItemRequest.getItemName());
        newItem.setItemDescription(sellItemRequest.getItemDescription());
        newItem.setDutch(sellItemRequest.getAuctionType().equals("dutch"));
        newItem.setdaysToShip(sellItemRequest.getDaysToShip());
        newItem.setInitialPrice(sellItemRequest.getInitialPrice());

        Timestamp auctionEnd = convertToSqlTimestamp(sellItemRequest.getAuctionEnd());
        newItem.setAuctionEnd(auctionEnd);

        int sellerId = getUserId();
        newItem.setSellerId(sellerId);
        catalogService.createItem(newItem);

        return Response.ok().build();
    }

    private Timestamp convertToSqlTimestamp(String localDateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(localDateTimeString, formatter);

        // Assuming the local date-time is in system's default timezone
        return Timestamp.valueOf(localDateTime.atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
    

    @GET
    @Path("/bids/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBiddingInfo(@PathParam("itemId") int itemId) {
        // Get the item (for item info)
    	CatalogItem item = catalogService.getItem(itemId);
        // Need to get the highest bid; if one doesn't exist then use the initial price
    	Bid highestBid = bidService.getHighestBid(itemId);
    	if (highestBid == null) {
    		highestBid = new Bid();
    		highestBid.setItemId(itemId);
    		highestBid.setPrice(item.getInitialPrice());
    	}

    	BidResponse bidResponse = new BidResponse();
    	bidResponse.setItemID(itemId);
    	bidResponse.setItemDescription(item.getItemDescription());
    	bidResponse.setShippingPrice(item.getShippingPrice());
    	bidResponse.setHighestPrice(highestBid.getPrice());
    	bidResponse.setHighestBidder(highestBid.getUserId());

    	return Response.ok(bidResponse).build();
    }

    @POST
    @Path("/bids")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBid(BidRequest bidRequest) {

        Bid newBid = new Bid();
        newBid.setUserId(getUserId());
        newBid.setItemId(bidRequest.getItemId());
        newBid.setPrice(bidRequest.getPrice());
        bidService.createBid(newBid);

        return Response.ok().build();
    }

    private int getUserId() {
        return ((User) request.getSession().getAttribute("user")).getId();
    }
}


