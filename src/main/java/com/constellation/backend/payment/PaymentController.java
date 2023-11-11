package com.constellation.backend.payment;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/payments")
public class PaymentController {
	private PaymentDAO paymentDAO = new PaymentDAO();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addCard(Payment payment) {
		paymentDAO.addCard(payment);
	}
	
	@GET
	@Path("/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Payment getPayment(@PathParam("userName") String userName) {
		return paymentDAO.read(userName);
	}
	
	@PUT
	@Path("/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateStudent(@PathParam("userName") String userName, Payment payment) {
		paymentDAO.update(userName, payment);
	}
	
	@DELETE
	@Path("/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteStudent(@PathParam("userName") String userName) {
		paymentDAO.delete(userName);
	}

}
