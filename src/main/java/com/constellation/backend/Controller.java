package com.constellation.backend;

import com.constellation.backend.AuctionService.AuctionService;
import com.constellation.backend.AuctionService.Bid;
import com.constellation.backend.catalogservice.CatalogItem;
import com.constellation.backend.catalogservice.CatalogService;
import com.constellation.backend.exceptions.LoginFailedException;
import com.constellation.backend.exceptions.NewBidException;
import com.constellation.backend.exceptions.SignupFailedException;
import com.constellation.backend.requests.BidRequest;
import com.constellation.backend.requests.LoginRequest;
import com.constellation.backend.requests.SignupRequest;
import com.constellation.backend.userService.User;
import com.constellation.backend.userService.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class Controller {
    private final UserService userService = new UserService();

    @POST
    @Path("/user/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) throws LoginFailedException {
        User user = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            throw new LoginFailedException();
        } else return Response.ok(user).build();
    }

    @POST
    @Path("/user/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(SignupRequest signupRequest) throws SignupFailedException {
        User user = userService.createUser(signupRequest.getUser(), signupRequest.getPassword());
        if (user == null) {
            throw new SignupFailedException();
        } else return Response.ok().build();
    }
    
    //Forward Auction bidding it gets a item id reads its descrption and populates the fields in html
    @GET
    @Path("/user/forward_auction_bidding/{itemID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadForwardBidding(@PathParam("itemID") int itemID) {
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
    	

    	// Log the serialized JSON
    	String json = null;
		try {
			json = new ObjectMapper().writeValueAsString(bidRequest);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("Serialized JSON: " + json);

    	return Response.ok(bidRequest).build();
    	
    }
    
    @PUT
    @Path("/user/dutch_auction_bidding/pay")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response DutchAuctionPayNow(BidRequest newbid) throws NewBidException {
//    	<@798245186567667733> when the user clicks on payment button can u 
//    	set 2 attributes in the session named price and itemId, 
//    	i need to access them for the receipt page?
    	AuctionService auctionService = new AuctionService();
    	Bid bid = auctionService.getBidByItemId(newbid.getItemID());
    	bid.setUserId(0);//set to user id in session
    	auctionService.updateBid(bid);

    	return Response.ok().build();
    }
    
}
