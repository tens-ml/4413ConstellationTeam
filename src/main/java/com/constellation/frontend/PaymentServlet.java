package com.constellation.frontend;
import com.constellation.backend.payment.*;
import com.constellation.backend.userService.*;

import java.io.IOException;
import java.io.Writer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletPayment
 */
@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TYPE="type";
	

  /**
   * @see HttpServlet#HttpServlet()
   */
  public PaymentServlet() {
    super();
    // TODO Auto-generated constructor stub
  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		Writer out = response.getWriter();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		
		if (request.getParameter(TYPE)!=null) {
			if (request.getParameter(TYPE).equals("info")) {
				       								

				String fName = user.getFirstName();
			    String lName = user.getLastName();
			    String str = user.getStreetAddress();
			    String no = user.getStreetNumber();
			    String city = user.getCity();
			    String country = user.getCountry();
			    String postCode = user.getPostalCode();
			    String netPrice = "TO BE IMPLEMENTED";
	
			    out.append("{\"fName\":\" " + fName + "\",");
			    out.append("\"lName\":\" " + lName + "\",");
			    out.append("\"str\":\" " + str + "\",");
			    out.append("\"no\":\" " + no + "\",");
			    out.append("\"city\":\" " + city + "\",");
			    out.append("\"country\":\" " + country + "\",");
			    out.append("\"postCode\":\" " + postCode + "\",");
			    out.append("\"netPrice\":\" " + netPrice + "\"}");	
		    
			}else if (request.getParameter(TYPE).equals("receipt")) {
				
				String fName = user.getFirstName();
			    String lName = user.getLastName();
			    String str = user.getStreetAddress();
			    String no = user.getStreetNumber();
			    String city = user.getCity();
			    String country = user.getCountry();
			    String postCode = user.getPostalCode();
			    String netPaid = "TO BE IMPLEMENTED";
			    String itemId = "TO BE IMPLEMENTED";
			    String shipETA = "5";

			    out.append("{\"fName\":\" " + fName + "\",");
			    out.append("\"lName\":\" " + lName + "\",");
			    out.append("\"str\":\" " + str + "\",");
			    out.append("\"no\":\" " + no + "\",");
			    out.append("\"city\":\" " + city + "\",");
			    out.append("\"country\":\" " + country + "\",");
			    out.append("\"postCode\":\" " + postCode + "\",");
			    out.append("\"netPaid\":\" " + netPaid + "\",");	
			    out.append("\"itemId\":\" " + itemId + "\",");	
			    out.append("\"shipETA\":\" " + shipETA + " days.\"}");	
			}
			
		}else {
	  response.sendRedirect("receipt");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Payment p = new Payment(Long.parseLong(request.getParameter("cardno")), request.getParameter("cardna"),Integer.parseInt(request.getParameter("exmo")),
				Integer.parseInt(request.getParameter("exye")), Integer.parseInt(request.getParameter("ccv")), user.getId());
		PaymentDAO paymentDAO = new PaymentDAO();
		paymentDAO.addCard(p);
		doGet(request, response);
	}

}

