package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.locked_in.util.SessionUtil;
import com.locked_in.service.OrderService;
import com.locked_in.service.UserService;

/**
 * HistoryController handles HTTP requests for order history.
 * 
 * It serves the order history page, displaying past orders for authenticated
 * users with their details, status, and related information.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/history" })
public class HistoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;
    private UserService userService;
    
    /**
     * Initializes the HistoryController with instances of required services.
     * Sets up OrderService for order history operations and UserService for user validation.
     */
    public HistoryController() {
        super();
        orderService = new OrderService();
        userService = new UserService();
    }
    
    /**
     * Handles GET requests to display order history.
     * 
     * Retrieves the authenticated user's order history and forwards to the
     * history view JSP with order details and status information.
     *
     * @param request  the HTTP request containing user session information
     * @param response the HTTP response for sending data to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs while forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = (String) SessionUtil.getAttribute(request, "email");
        
        if (email == null) {
            // If not logged in, redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // For now, redirect to user profile with a message
        // In the future, replace with actual history page
        request.setAttribute("message", "Activity history feature coming soon!");
        response.sendRedirect(request.getContextPath() + "/userprofile");
    }
    
    /**
     * Handles POST requests for history actions.
     * 
     * Currently delegates to doGet as no specific POST functionality is implemented.
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