
package com.constellation.backend;

import com.constellation.backend.catalogservice.CatalogItem;
import com.constellation.backend.catalogservice.CatalogService;
import com.constellation.backend.exceptions.LoginFailedException;
import com.constellation.backend.exceptions.SignupFailedException;
import com.constellation.backend.requests.LoginRequest;
import com.constellation.backend.requests.SellItemRequest;
import com.constellation.backend.requests.SignupRequest;
import com.constellation.backend.userService.User;
import com.constellation.backend.userService.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.constellation.backend.AuctionService.AuctionService;
import com.constellation.backend.AuctionService.Bid;
import com.constellation.backend.catalogservice.CatalogItem;
import com.constellation.backend.catalogservice.CatalogService;
import com.constellation.backend.exceptions.LoginFailedException;
import com.constellation.backend.exceptions.NewBidException;
import com.constellation.backend.exceptions.SignupFailedException;
import com.constellation.backend.exceptions.WrongUserException;
import com.constellation.backend.requests.BidRequest;
import com.constellation.backend.requests.LoginRequest;
import com.constellation.backend.requests.SignupRequest;
import com.constellation.backend.userService.User;
import com.constellation.backend.userService.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;

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
        newItem.setdaysToShip(sellItemRequest.getdaysToShip());
        newItem.setInitialPrice(sellItemRequest.getInitialPrice());

        Timestamp auctionEnd = convertToSqlTimestamp(sellItemRequest.getAuctionEnd());
        newItem.setAuctionEnd(auctionEnd);

        int sellerId = ((User) request.getSession(true).getAttribute("user")).getId();
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
    @Path("/user/auction_bidding/{itemID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadBiddingInfo(@PathParam("itemID") int itemID) {
    	CatalogService catalogService = new CatalogService();
    	CatalogItem item = catalogService.getItem(itemID);
    	AuctionService auctionService = new AuctionService();
    	
    	Bid test_bid = auctionService.getBidByItemId(itemID);
    	if (test_bid == null) {
    		//create bid
    		test_bid = new Bid();
    		test_bid.setItemId(itemID);
    		test_bid.setPrice(item.getInitialPrice());
    	}
    	BidRequest bidRequest = new BidRequest();
    	bidRequest.setItemID(itemID);
    	bidRequest.setItemDescription(item.getItemDescription());
    	bidRequest.setShippingPrice(14);//set to item id shipping price
    	bidRequest.setHighestPrice(test_bid.getPrice());
    	bidRequest.setHighestBidder(test_bid.getUserId());
    	


    	return Response.ok(bidRequest).build();
    	
    }
    
    @PUT
    @Path("/user/forward_auction_bidding")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBid(BidRequest newbid) throws NewBidException {
    	CatalogService catalogService = new CatalogService();
    	System.out.println("New bid itemid"+newbid.getItemID());
    	CatalogItem item = catalogService.getItem(newbid.getItemID());
    	AuctionService auctionService = new AuctionService();
    	
    	Bid test_bid = auctionService.getBidByItemId(item.getId());
    	//there is already a bid for the item
    	if (test_bid != null && newbid.getNewBid() > test_bid.getPrice() ) {
    			//update bid price
    			Bid bid = test_bid;
    			bid.setPrice(newbid.getNewBid());
    			bid.setUserId(0); // should be userId store in session
    		
    			System.out.println("right");
    			
    			auctionService.updateBid(bid);
    		}
    		else if (newbid.getNewBid() > item.getInitialPrice()) { //there is no bid for item
    			//create bid with new price
    			
    			System.out.println("In here now");
    			Bid bid = new Bid();
    			bid.setItemId(item.getId());
    			bid.setPrice(newbid.getNewBid());
    			bid.setUserId(0); // should be userId store in session 			
    			auctionService.createBid(bid);
    		}
    		else {
    			//new bid amount is less than or equal to old bid
    			System.out.println("wroonggg");
    			// throw NewBidException
    			throw new NewBidException();
    		}

    	return Response.ok().build();
    }
    

    @GET
    @Path("/user/dutch_auction_bidding/{itemID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadDutchBidding(@PathParam("itemID") int itemID) {
    	CatalogService catalogService = new CatalogService();
    	CatalogItem item = catalogService.getItem(itemID);
    	AuctionService auctionService = new AuctionService();
    	
    	Bid test_bid = auctionService.getBidByItemId(itemID);
    	if (test_bid == null) {
    		//create bid
    		test_bid = new Bid();
    		test_bid.setItemId(itemID);
    		test_bid.setPrice(item.getInitialPrice());
    	}
    	BidRequest bidRequest = new BidRequest();
    	bidRequest.setItemID(itemID);
    	bidRequest.setItemDescription(item.getItemDescription());
    	bidRequest.setShippingPrice(14);//set to item id shipping price
    	bidRequest.setHighestPrice(test_bid.getPrice());
    	bidRequest.setHighestBidder(test_bid.getUserId());
    	




    	return Response.ok(bidRequest).build();
    	
    }

    
    @PUT
    @Path("/user/auction_ended/pay")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response AuctionEndedPayNow(BidRequest newbid) throws NewBidException, WrongUserException {

    	AuctionService auctionService = new AuctionService();
    	Bid bid = auctionService.getBidByItemId(newbid.getItemID());
    	HttpSession session = request.getSession() ;
    	session.setAttribute("userId", 0);
    	if (bid.getUserId() == (Integer) session.getAttribute("userId")  ) {
    		auctionService.updateBid(bid);

    		//price with shipping
    		session.setAttribute("price", newbid.getHighestPrice());
    		session.setAttribute("itemId", bid.getItemId());
    	
    		System.out.println("price: "+session.getAttribute("price"));
    		System.out.println("itemId: "+session.getAttribute("itemId"));
    	}
    	else {
    		throw new WrongUserException();
    	}
    	
    	return Response.ok().build();
    }

}


