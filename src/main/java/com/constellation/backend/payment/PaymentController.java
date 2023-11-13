package com.constellation.backend.payment;
import com.constellation.backend.exceptions.PaymentException;

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
	public void addCard(Payment payment) throws PaymentException {
		if (payment.getCardNo() > 9999999999999999L || payment.getCardNo() < 100000000000000L)
			throw new PaymentException("Card Number has to be 15 or 16 digits");
		if (payment.getExpMo() > 12 || payment.getExpMo() < 1)
			throw new PaymentException("Expiry Month has to be between 1 and 12");
		if (payment.getExpYe() > 2099 || payment.getExpYe() < 2023)
			throw new PaymentException("Expiry Year has to be between 2023 and 2099");
		if (payment.getCcv() > 9999 || payment.getCcv() < 100)
			throw new PaymentException("CCV has to be 3 or 4 digits");
		if (payment.getExpMo() > 12 || payment.getExpMo() < 1)
		paymentDAO.addCard(payment);
	}
	
	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Payment getPayment(@PathParam("userId") int userId) {
		return paymentDAO.read(userId);
	}
	
	@PUT
	@Path("/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updatePayment(@PathParam("userId") int userId, Payment payment) throws PaymentException {
		if (payment.getCardNo() > 9999999999999999L || payment.getCardNo() < 100000000000000L)
			throw new PaymentException("Card Number has to be 15 or 16 digits");
		if (payment.getExpMo() > 12 || payment.getExpMo() < 1)
			throw new PaymentException("Expiry Month has to be between 1 and 12");
		if (payment.getExpYe() > 2099 || payment.getExpYe() < 2023)
			throw new PaymentException("Expiry Year has to be between 2023 and 2099");
		if (payment.getCcv() > 9999 || payment.getCcv() < 100)
			throw new PaymentException("CCV has to be 3 or 4 digits");
		if (payment.getExpMo() > 12 || payment.getExpMo() < 1)
		paymentDAO.update(userId, payment);
	}
	
	@DELETE
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deletePayment(@PathParam("userId") int userId) {
		paymentDAO.delete(userId);
	}

}
