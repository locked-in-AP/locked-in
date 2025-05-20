package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AboutUsController handles HTTP requests for the about us page.
 * 
 * It serves the about us page content, providing information about the company,
 * its mission, and team members. The controller handles both viewing the about us
 * page and processing any related form submissions.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/aboutus" })
public class AboutUsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Initializes the AboutUsController.
     * This controller doesn't require any service dependencies.
     */
    public AboutUsController() {
        super();
    }

    /**
     * Handles GET requests to display the about us page.
     * 
     * Forwards the request to the about us JSP located in /WEB-INF/pages/aboutus.jsp.
     *
     * @param request  the HTTP request containing client request information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/aboutus.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for about us page actions.
     * 
     * Currently delegates to doGet as no specific POST functionality is implemented.
     *
     * @param request  the HTTP request containing form submission data
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
