package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * PaymentController handles HTTP requests related to payment processing.
 * 
 * It manages the payment workflow, including displaying payment forms and
 * processing payment submissions. The controller handles both viewing payment
 * options and processing payment transactions.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/payment" })
public class PaymentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Initializes the PaymentController.
     * This controller doesn't require any service dependencies.
     */
    public PaymentController() {
        super();
    }

	/**
	 * Handles GET requests to display the payment form.
	 * 
	 * Forwards the request to the payment JSP located in /WEB-INF/pages/payment.jsp.
	 *
	 * @param request  the HTTP request containing client request information
	 * @param response the HTTP response for sending data to the client
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an I/O error occurs while forwarding
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/payment.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for payment processing.
	 * 
	 * Currently delegates to doGet as payment processing is not implemented.
	 *
	 * @param request  the HTTP request containing payment information
	 * @param response the HTTP response for sending data to the client
	 * @throws ServletException if a servlet-related error occurs
	 * @throws IOException      if an I/O error occurs while forwarding
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
