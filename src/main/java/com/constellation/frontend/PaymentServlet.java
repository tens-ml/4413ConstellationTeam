package com.constellation.frontend;
import com.constellation.backend.payment.*;

import java.io.IOException;
import java.io.Writer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
		
		if (request.getParameter(TYPE)!=null) {
			if (request.getParameter(TYPE).equals("info")) {//if the client asks for info
				             								//construct a msg...
				String msg="Please add your payment information";

				out.append(msg);
			}
			
		}else {
	    response.sendRedirect("Receipt.html");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Payment p = new Payment(Long.parseLong(request.getParameter("cardno")), request.getParameter("cardna"),Integer.parseInt(request.getParameter("exmo")),
				Integer.parseInt(request.getParameter("exye")), Integer.parseInt(request.getParameter("ccv")), request.getParameter("usern"));
		PaymentDAO paymentDAO = new PaymentDAO();
		paymentDAO.addCard(p);
		doGet(request, response);
	}

}

