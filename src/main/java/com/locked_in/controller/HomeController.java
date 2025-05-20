package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HomeController handles HTTP requests for the application's home page.
 * 
 * It serves as the main entry point for users, displaying the home page with
 * featured products and promotional content. The controller handles both root path (/)
 * and /home URL patterns.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/home" , "/" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Initializes the HomeController.
     * This controller doesn't require any service dependencies.
     */
    public HomeController() {
        super();
    }

    /**
     * Handles GET requests to display the home page.
     * 
     * Forwards the request to the home page JSP located in /WEB-INF/pages/home.jsp.
     *
     * @param request  the HTTP request containing client request information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to the home page.
     * 
     * Currently delegates to doGet as no POST functionality is implemented.
     *
     * @param request  the HTTP request containing client request information
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
