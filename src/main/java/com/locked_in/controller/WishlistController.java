package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.locked_in.util.SessionUtil;

/**
 * WishlistController handles HTTP requests for user wishlist functionality.
 * 
 * It manages the wishlist feature, allowing users to save products for later viewing.
 * Currently redirects to user profile as the wishlist view is not implemented yet.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/wishlist" })
public class WishlistController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * Initializes the WishlistController.
     * This controller doesn't require any service dependencies.
     */
    public WishlistController() {
        super();
    }
    
    /**
     * Handles GET requests to display wishlist.
     * 
     * Currently redirects to user profile with a message as wishlist view is not
     * implemented yet. Will be updated to show actual wishlist functionality.
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
        // In the future, replace with actual wishlist page
        request.setAttribute("message", "Wishlist feature coming soon!");
        response.sendRedirect(request.getContextPath() + "/userprofile");
    }
    
    /**
     * Handles POST requests for wishlist actions.
     * 
     * Currently delegates to doGet as no specific POST functionality is implemented.
     *
     * @param request  the HTTP request containing wishlist action data
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