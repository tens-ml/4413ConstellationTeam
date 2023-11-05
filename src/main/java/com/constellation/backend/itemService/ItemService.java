package com.constellation.backend.itemservice;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/ItemService")
public class ItemService {
	ItemDao itemDao = new ItemDao();
	
	@GET
	@Path("/items")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> getAllItems() {
		return itemDao.getItems();
	}
	
}
