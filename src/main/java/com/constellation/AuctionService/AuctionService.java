package com.constellation.AuctionService;
import java.util.List;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/AuctionService")
public class AuctionService {
	private BidDAO bidDAO = new BidDAO();
	
	// create a bid
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void createBid(Bid bid) {
		bidDAO.create(bid);
	}
	
	// get all bids
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Bid> getAllBids() {
		return bidDAO.readAll();
	}
	
	// get a bid by its id
	@GET
	@Path("/{BiddingID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Bid getAllBids(@PathParam("BiddingID") int BiddingID) {
		return bidDAO.read(BiddingID);
	}
	
	// update a bid
	@PUT
	@Path("/{BiddingID}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateBid(@PathParam("BiddingID") int BiddingID, Bid bid) {
		bidDAO.update(BiddingID, bid);
	}
	
	
	//delete a bid
	@DELETE
	@Path("/{BiddingID}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deletBid(@PathParam("/{BiddingID") int BiddingID) {
		bidDAO.delete(BiddingID);
	}
	
}
