package com.constellation.backend.AuctionService;
import java.util.List;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

//remove servlet calls later

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
	public Bid getBidById(@PathParam("BiddingID") int id) {
		return bidDAO.readById(id);
	}
	
	public Bid getBidByItemId(int itemId) {
		return bidDAO.readByItemId(itemId);
	}
	
	// update a bid
	public void updateBid(Bid bid) {
		bidDAO.update(bid.getId(), bid);
	}
	
	
	//delete a bid
	@DELETE
	@Path("/{BiddingID}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deletBid(@PathParam("BiddingID") int BiddingID) {
		bidDAO.delete(BiddingID);
	}
	
}
