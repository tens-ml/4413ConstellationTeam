package com.constellation.backend.payment;
import com.constellation.backend.exceptions.PaymentException;
import com.constellation.backend.userService.*;

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
	public String addCard(Payment payment){
		try {
			if (payment.getCardNo() > 9999999999999999L || payment.getCardNo() < 100000000000000L)
				throw new PaymentException("Card Number has to be 15 or 16 digits");
			if (payment.getExpMo() > 12 || payment.getExpMo() < 1)
				throw new PaymentException("Expiry Month has to be between 1 and 12");
			if (payment.getExpYe() > 2099 || payment.getExpYe() < 2023)
				throw new PaymentException("Expiry Year has to be between 2023 and 2099");
			if (payment.getCcv() > 9999 || payment.getCcv() < 100)
				throw new PaymentException("CCV has to be 3 or 4 digits");
			if (payment.getName().isEmpty() || payment.getName().trim().length() == 0)
				throw new PaymentException("Enter a valid cardholder name");
			if (!UserDao.isUserInDatabase(payment.getUserId()))
				throw new PaymentException("User does not exist");
			paymentDAO.addCard(payment);
			return "Success";
		}catch(PaymentException e){
			return e.getMessage();
		}
	}
	
	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPayment(@PathParam("userId") int userId){
		try {
		if (!UserDao.isUserInDatabase(userId))
			throw new PaymentException("User does not exist");
		if (!PaymentDAO.isPaymentInDatabase(userId))
			throw new PaymentException("No payment information for this user");
		return paymentDAO.read(userId).toString();
		}catch(PaymentException e){
			return e.getMessage();
		}
	}
	
	@PUT
	@Path("/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updatePayment(@PathParam("userId") int userId, Payment payment){
		try {
			if (payment.getCardNo() > 9999999999999999L || payment.getCardNo() < 100000000000000L)
				throw new PaymentException("Card Number has to be 15 or 16 digits");
			if (payment.getExpMo() > 12 || payment.getExpMo() < 1)
				throw new PaymentException("Expiry Month has to be between 1 and 12");
			if (payment.getExpYe() > 2099 || payment.getExpYe() < 2023)
				throw new PaymentException("Expiry Year has to be between 2023 and 2099");
			if (payment.getCcv() > 9999 || payment.getCcv() < 100)
				throw new PaymentException("CCV has to be 3 or 4 digits");
			if (payment.getName().isEmpty() || payment.getName().trim().length() == 0)
				throw new PaymentException("Enter a valid cardholder name");
			if (!UserDao.isUserInDatabase(payment.getUserId()))
				throw new PaymentException("User does not exist");
			paymentDAO.update(userId, payment);
			return "Success";
		}catch(PaymentException e){
			return e.getMessage();
		}
	}
	
	@DELETE
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deletePayment(@PathParam("userId") int userId){
		try {
		if (!UserDao.isUserInDatabase(userId))
			throw new PaymentException("User does not exist");
		if (!PaymentDAO.isPaymentInDatabase(userId))
			throw new PaymentException("No payment information for this user");
		paymentDAO.delete(userId);
		return "Success";
		}catch(PaymentException e){
			return e.getMessage();
		}
	}

}
