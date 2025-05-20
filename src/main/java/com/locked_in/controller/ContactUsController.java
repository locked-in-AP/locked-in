package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ContactUsController handles HTTP requests for the contact us page.
 * 
 * It manages the contact form functionality, allowing users to send messages
 * to the company. The controller handles both displaying the contact form and
 * processing form submissions.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/contactus" })
public class ContactUsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Initializes the ContactUsController.
     * This controller doesn't require any service dependencies.
     */
    public ContactUsController() {
        super();
    }

    /**
     * Handles GET requests to display the contact form.
     * 
     * Forwards the request to the contact us JSP located in /WEB-INF/pages/contactus.jsp.
     *
     * @param request  the HTTP request containing client request information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/contactus.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for contact form submissions.
     * 
     * Currently delegates to doGet as form processing is not implemented.
     *
     * @param request  the HTTP request containing contact form data
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
