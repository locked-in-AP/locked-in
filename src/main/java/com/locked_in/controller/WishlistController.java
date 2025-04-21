package com.locked_in.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.locked_in.util.SessionUtil;

/**
 * Controller for handling user wishlist functionality
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/wishlist" })
public class WishlistController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * Default constructor
     */
    public WishlistController() {
        super();
    }
    
    /**
     * Handles GET requests to display wishlist
     * For now, redirects to user profile as wishlist view is not implemented yet
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
     * Handles POST requests for wishlist actions
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
} 